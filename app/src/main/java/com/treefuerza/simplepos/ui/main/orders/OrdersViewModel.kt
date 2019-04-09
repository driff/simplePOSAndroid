package com.treefuerza.simplepos.ui.main.orders

import com.airbnb.mvrx.MvRxViewModelFactory
import com.treefuerza.simplepos.data.DataRepository
import com.treefuerza.simplepos.ui.base.MvRxViewModel

class OrdersViewModel(initialState: OrdersState, private val userId: Int, private val repo: DataRepository) : MvRxViewModel<OrdersState>(initialState) {

    fun fetchUser(){
        repo.getUser(userId.toString()).execute {
            copy( user = it)
        }
    }

    companion object: MvRxViewModelFactory<OrdersViewModel, OrdersState> {
//        override fun initialState(viewModelContext: ViewModelContext): OrdersState? {
//            val userId = (viewModelContext as FragmentViewModelContext).fragment<OrdersFragment>().getUserId()
//            return OrdersState(userId)
//        }

//        override fun create(viewModelContext: ViewModelContext, state: OrdersState): OrdersViewModel? {
//            val userId = (viewModelContext as FragmentViewModelContext).fragment<OrdersFragment>().getNavArgs().userId
//            val userRepository = viewModelContext.app<TreeApplication>().component.userRepository()
//            return OrdersViewModel(state, userId, userRepository)
//        }
    }

}
