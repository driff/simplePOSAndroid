package com.treefuerza.simplepos.ui.main.orders.open

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.PersistState
import com.treefuerza.simplepos.models.Orders
import com.treefuerza.simplepos.ui.base.MvRxViewModel
import io.reactivex.Observable

data class OpenOrdersState(val orders: Async<List<Orders>>, @PersistState val total: Double,
                           @PersistState val size: Int): MvRxState
data class totals(val total: Double = 0.0, val size: Int = 0)
class OpenOrdersViewModel(initialState: OpenOrdersState): MvRxViewModel<OpenOrdersState>(initialState) {
    init {
        asyncSubscribe(OpenOrdersState::orders, onSuccess = {
            if(!it.isNullOrEmpty()) {
                Observable.fromIterable(it).reduce(totals(), this::calculate)
                    .execute { total -> copy(total = total.invoke()?.total?:0.0, size = total.invoke()?.size?: 0) }
            }
        })
    }

    private fun calculate(count: totals, order:Orders): totals = totals(count.total + order.total, count.size + 1)
}