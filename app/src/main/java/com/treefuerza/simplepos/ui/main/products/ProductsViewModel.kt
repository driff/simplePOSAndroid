package com.treefuerza.simplepos.ui.main.products

import com.airbnb.mvrx.*
import com.treefuerza.simplepos.TreeApplication
import com.treefuerza.simplepos.data.DataRepository
import com.treefuerza.simplepos.models.Item
import com.treefuerza.simplepos.ui.base.MvRxViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

data class ProductsState(val products: Async<List<Item>> = Uninitialized): MvRxState

class ProductsViewModel(initialState: ProductsState, private val repo: DataRepository) : MvRxViewModel<ProductsState>(initialState) {

    companion object: MvRxViewModelFactory<ProductsViewModel, ProductsState> {
        override fun create(viewModelContext: ViewModelContext, state: ProductsState): ProductsViewModel? {
            val repo = viewModelContext.app<TreeApplication>().component.dataRepository()
            return ProductsViewModel(state, repo)
        }
    }

    init {
        fetchProducts()
    }

    fun fetchProducts(){
        this.repo.getAllItems().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .toObservable()
            .execute { copy(products = it) }
    }

}
