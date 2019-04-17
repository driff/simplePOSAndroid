package com.treefuerza.simplepos.di.modules

import com.treefuerza.simplepos.di.PerChildFragment
import com.treefuerza.simplepos.ui.main.orders.create.OrderDetailsAdapter
import dagger.Module
import dagger.Provides

@Module
class CreateOrderModule {

    @Provides
    @PerChildFragment
    fun provideOrdersAdapter(): OrderDetailsAdapter = OrderDetailsAdapter(mutableListOf())
}