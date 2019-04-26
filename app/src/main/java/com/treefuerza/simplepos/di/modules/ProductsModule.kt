package com.treefuerza.simplepos.di.modules

import com.treefuerza.simplepos.di.PerChildFragment
import com.treefuerza.simplepos.ui.main.orders.open.OrdersAdapter
import com.treefuerza.simplepos.ui.main.products.ProductsAdapter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ProductsModule {

    @Provides
    @PerChildFragment
    fun provideProductsAdapter(): ProductsAdapter = ProductsAdapter(mutableListOf())
}