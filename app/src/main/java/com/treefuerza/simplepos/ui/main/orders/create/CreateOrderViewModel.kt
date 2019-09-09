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
import org.threeten.bp.ZonedDateTime
import java.util.*

data class CreateOrderState(
    @PersistState val client: String = "",
    @PersistState val code: String = "",
    @PersistState val quantity: Double = 0.0,
    val order: Async<Orders> = Uninitialized,
    val item: Async<Item> = Uninitialized,
    val details: Async<MutableList<OrderDetail>> = Uninitialized): MvRxState

class CreateOrderViewModel(initialState: CreateOrderState, private val repo: DataRepository): MvRxViewModel<CreateOrderState>(initialState){

    companion object: MvRxViewModelFactory<CreateOrderViewModel, CreateOrderState>{
        override fun create(viewModelContext: ViewModelContext, state: CreateOrderState): CreateOrderViewModel? {
            val repo = viewModelContext.app<TreeApplication>().component.dataRepository()
            return CreateOrderViewModel(state, repo)
        }
    }

    fun loadOrder(order: Orders? = null) {
        var _order = order
        if(_order == null) {
            _order = Orders(creator = "", createdAt = ZonedDateTime.now().toLocalDateTime().toString(), client = "")
        }
        Observable.just(_order).execute {
            copy(order = it)
        }
    }

    fun fetchProduct(code: String) {
        repo.getItem(code).execute {
            copy(item = it)
        }
//        Observable.just(Item(name = "Test item", userId = "testuser", price = 2.5)).execute {
//            copy(item = it)
//        }
    }

    fun setProduct(item: Item) {
        Observable.just(item).execute {
            copy(item = it)
        }
    }

    fun addDetail(quantity: Double) {
        withState {
            if(it.item.invoke() == null || it.order.invoke() == null) {
                Log.d("covm", "Order or item is null")
                return@withState
            }
            val item = it.item.invoke()!!
            val total = item.price * quantity * (item.taxValue / 100)
            val det = OrderDetail(orderId = it.order.invoke()!!.id, total = total, description = "TEST DESCRIPTION", quantity = it.quantity, itemId = item.id)
            val dets = it.details.invoke()?: mutableListOf(det)
            Observable.just(dets).execute { details -> copy(details = details) }
        }
    }

    fun saveOrder(client: String){
        withState {
            val order = it.order.invoke()?: return@withState
            val orderDetails = it.details.invoke()?: return@withState
            order.client = client
            order.itemsQuantity = orderDetails.size
            order.status = 1
            order.subtotal = 0.0 // TODO: calculate this
            order.tax = 0.0
            order.total = 0.0
            this.repo.addOrderTransaction(order, orderDetails)
        }
    }

    fun setQuantity(quantity: Double) {
        setState { copy(quantity = quantity) }
    }

}