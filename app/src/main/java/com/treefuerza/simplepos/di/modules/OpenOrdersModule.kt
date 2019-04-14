package com.treefuerza.simplepos.di.modules

import com.treefuerza.simplepos.di.PerChildFragment
import com.treefuerza.simplepos.ui.main.orders.open.OrdersAdapter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class OpenOrdersModule {

    @Provides
    @PerChildFragment
    fun provideOrdersAdapter(): OrdersAdapter = OrdersAdapter(mutableListOf())
}