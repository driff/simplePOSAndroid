package com.treefuerza.simplepos.ui.main.orders.open

import com.airbnb.mvrx.*
import com.treefuerza.simplepos.TreeApplication
import com.treefuerza.simplepos.data.DataRepository
import com.treefuerza.simplepos.models.Orders
import com.treefuerza.simplepos.ui.base.MvRxViewModel
import io.reactivex.Observable
import java.util.*

data class OpenOrdersState(val orders: Async<List<Orders>> = Uninitialized, @PersistState val total: Double = 0.0,
                           @PersistState val size: Int = 0): MvRxState
data class totals(val total: Double = 0.0, val size: Int = 0)
class OpenOrdersViewModel(initialState: OpenOrdersState, private val repo: DataRepository): MvRxViewModel<OpenOrdersState>(initialState) {

    companion object: MvRxViewModelFactory<OpenOrdersViewModel, OpenOrdersState> {
        override fun create(viewModelContext: ViewModelContext, state: OpenOrdersState): OpenOrdersViewModel? {
            val repo = viewModelContext.app<TreeApplication>().component.dataRepository()
            return OpenOrdersViewModel(state, repo)
        }
    }

    init {
        asyncSubscribe(OpenOrdersState::orders, onSuccess = {
            if(!it.isNullOrEmpty()) {
                Observable.fromIterable(it).reduce(totals(), this::calculate)
                    .execute { total -> copy(total = total.invoke()?.total?:0.0, size = total.invoke()?.size?: 0) }
            }
        })
        fetchOpenOrders()
    }

    private fun calculate(count: totals, order:Orders): totals = totals(count.total + order.total, count.size + 1)
    fun fetchOpenOrders() {
//        this.repo.getOpenOrders()
//            .toObservable()
//            .execute { copy(orders = it) }
        Observable.just(listOf(Orders(UUID.randomUUID().toString(), "TEST", total = 37.50), Orders(UUID.randomUUID().toString(), "TEST2", total = 20.12)))
            .execute { copy(orders = it) }
    }
}