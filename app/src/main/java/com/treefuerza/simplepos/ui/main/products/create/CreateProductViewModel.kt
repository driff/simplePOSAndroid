package com.treefuerza.simplepos.ui.main.products.create

import com.airbnb.mvrx.*
import com.treefuerza.simplepos.TreeApplication
import com.treefuerza.simplepos.data.DataRepository
import com.treefuerza.simplepos.models.Item
import com.treefuerza.simplepos.ui.base.MvRxViewModel
import io.reactivex.Observable
import org.threeten.bp.ZonedDateTime

data class CreateProductState(@PersistState val name: String = "",
                              @PersistState val price: Double = 0.0,
                              @PersistState val code: String = "",
                              @PersistState val tax: Double = 0.0,
                              @PersistState val imageUrl: String = "",
                              val errorMsg: Async<String> = Uninitialized,
                              val done: Async<Boolean> = Uninitialized
): MvRxState

class CreateProductViewModel(initialState: CreateProductState, val repo: DataRepository) : MvRxViewModel<CreateProductState>(initialState) {

    companion object: MvRxViewModelFactory<CreateProductViewModel, CreateProductState> {
        override fun create(viewModelContext: ViewModelContext, state: CreateProductState): CreateProductViewModel? {
            val repo = viewModelContext.app<TreeApplication>().component.dataRepository()
            return CreateProductViewModel(state, repo)
        }
    }

    fun setName(obs: Observable<String>) {
        obs.execute {
            copy(name = it.invoke()?:"")
        }
    }

    fun setCode(obs: Observable<String>) {
        obs.execute {
            copy(code = it.invoke()?:"")
        }
    }

    fun setPrice(obs: Observable<Double>) {
        obs.execute {
            copy(price = it.invoke()?:0.0)
        }
    }

    fun setTax(obs: Observable<Double>) {
        obs.execute {
            copy(tax = it.invoke()?:0.0)
        }
    }

    fun createItem(name: String, code: String, price: Double, tax: Double) {
        withState {
            if(code.isNotEmpty() && name.isNotEmpty() && price > 0 && tax >= 0){
                repo.addItem(Item(code = code, price = price, taxValue = tax, name = name, createdAt = ZonedDateTime.now().toString(), userId = ""))
                Observable.just(true).execute { done -> copy(done = done) }
            }
        }
    }


}
