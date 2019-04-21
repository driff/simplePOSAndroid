package com.treefuerza.simplepos.ui.main.orders.create

import android.util.Log
import com.airbnb.mvrx.*
import com.treefuerza.simplepos.TreeApplication
import com.treefuerza.simplepos.data.DataRepository
import com.treefuerza.simplepos.models.Item
import com.treefuerza.simplepos.models.OrderDetail
import com.treefuerza.simplepos.models.Orders
import com.treefuerza.simplepos.ui.base.MvRxViewModel
import io.reactivex.Observable

data class CreateOrderState(
    @PersistState val client: String = "",
    @PersistState val code: String = "",
    @PersistState val quantity: Double = 0.0,
    val order: Async<Orders> = Uninitialized,
    val item: Async<Item> = Uninitialized,
    val details: Async<List<OrderDetail>> = Uninitialized): MvRxState

class CreateOrderViewModel(initialState: CreateOrderState, private val repo: DataRepository): MvRxViewModel<CreateOrderState>(initialState){

    companion object: MvRxViewModelFactory<CreateOrderViewModel, CreateOrderState>{
        override fun create(viewModelContext: ViewModelContext, state: CreateOrderState): CreateOrderViewModel? {
            val repo = viewModelContext.app<TreeApplication>().component.dataRepository()
            return CreateOrderViewModel(state, repo)
        }
    }

    init {

    }

    fun fetchProduct(code: String) {
//        repo.getItem(code).execute {
//            copy(item = it)
//        }
        Observable.just(Item(name = "Test item", userId = "testuser", price = 2.5)).execute {
            copy(item = it)
        }
    }

    fun addDetail() {
        withState {
            val total = it.item.invoke()?.price?: 0 * it.quantity
            Observable.create<List<OrderDetail>> { emitter ->
                val det = OrderDetail(orderId = "test", total = total, description = "TEST DESCRIPTION", quantity = it.quantity)
                Log.w("Viewmodel", det.toString())
                val details: MutableList<OrderDetail>
                when(it.details){
                    is Success -> {
                        details = it.details.invoke().toMutableList()
                        details.add(det)
                    }
                    else -> details = mutableListOf(det)
                }
                emitter.onNext(details.toList())
                emitter.onComplete()
            }.execute { dets -> copy(details = dets) }
        }
    }

    fun setQuantity(quantity: Double) {
        setState { copy(quantity = quantity) }
    }

}