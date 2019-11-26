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
import io.reactivex.schedulers.Schedulers
import java.util.*

data class CreateOrderState(
    @PersistState val client: String = "",
    @PersistState val code: String = "",
    @PersistState val quantity: Double = 0.0,
    @PersistState val total: Double = 0.0,
    @PersistState val subtotal: Double = 0.0,
    @PersistState val tax: Double = 0.0,
    val order: Async<Orders> = Uninitialized,
    val item: Async<Item> = Uninitialized,
    val orderSaved: Async<Boolean> = Uninitialized,
    val details: Async<List<OrderDetail>> = Uninitialized
) : MvRxState

class CreateOrderViewModel(initialState: CreateOrderState, private val repo: DataRepository) :
    MvRxViewModel<CreateOrderState>(initialState) {

    val TAG = this.javaClass.canonicalName

    companion object : MvRxViewModelFactory<CreateOrderViewModel, CreateOrderState> {
        override fun create(
            viewModelContext: ViewModelContext,
            state: CreateOrderState
        ): CreateOrderViewModel? {
            val repo = viewModelContext.app<TreeApplication>().component.dataRepository()
            return CreateOrderViewModel(state, repo)
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

    fun loadOrder(id: String?) {
        if (id != null && id.trim() != "") {
            this.repo.db.orderDao().findById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .execute { copy(order = it) }
            this.repo.db.orderDetailDao().getAllFromOrder(id)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .execute {
                Log.d(TAG, this.toString())
                copy(details = it)
            }
        }
    }

    fun setProduct(item: Item) {
        Observable.just(item).execute {
            copy(item = it)
        }
    }

    fun addDetail(detail: OrderDetail) {
        withState {
            val order = it.order.invoke()?.copy() ?: Orders(
                id = UUID.randomUUID().toString(),
                creator = "",
                sequence = 0
            )
            detail.apply {
                order.total += total
                order.tax += tax
                order.subtotal += subtotal
                orderId = order.id
            }
            Log.i(TAG, detail.orderId)
            val dets = it.details.invoke()?.toMutableList() ?: mutableListOf()
            dets.add(detail)
            repo.addOrderTransaction(order, dets)
            Observable.just(dets).execute { details -> copy(details = details) }
            Observable.just(order).execute { o -> copy(order = o) }
        }
    }

    fun saveOrder(client: String) {
        withState {
            val orderDetails = it.details.invoke() ?: return@withState
            val order = it.order.invoke()?.copy(
                client = client, itemsQuantity = orderDetails.size,
                status = 1
            ) ?: return@withState
            this.repo.addOrderTransaction(order, orderDetails)
        }
    }

}