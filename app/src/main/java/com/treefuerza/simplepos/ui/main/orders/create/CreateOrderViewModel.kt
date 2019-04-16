package com.treefuerza.simplepos.ui.main.orders.create

import com.airbnb.mvrx.*
import com.treefuerza.simplepos.TreeApplication
import com.treefuerza.simplepos.data.DataRepository
import com.treefuerza.simplepos.models.Item
import com.treefuerza.simplepos.models.OrderDetail
import com.treefuerza.simplepos.models.Orders
import com.treefuerza.simplepos.ui.base.MvRxViewModel

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
        repo.getItem(code).execute {
            copy(item = it)
        }
    }

}