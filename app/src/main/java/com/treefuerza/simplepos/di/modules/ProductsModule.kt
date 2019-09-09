package com.treefuerza.simplepos.di.modules

import com.treefuerza.simplepos.di.PerChildFragment
import com.treefuerza.simplepos.models.Item
import com.treefuerza.simplepos.ui.main.orders.open.OrdersAdapter
import com.treefuerza.simplepos.ui.main.products.ProductsAdapter
import com.treefuerza.simplepos.utils.OnItemClickListener
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ProductsModule(private val listener: OnItemClickListener<Item>) {

    @Provides
    @PerChildFragment
    fun provideProductsAdapter(): ProductsAdapter = ProductsAdapter(mutableListOf(), listener)
}