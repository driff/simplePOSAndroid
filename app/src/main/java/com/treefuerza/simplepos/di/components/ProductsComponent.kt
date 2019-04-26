package com.treefuerza.simplepos.di.components

import com.treefuerza.simplepos.di.PerChildFragment
import com.treefuerza.simplepos.di.modules.OpenOrdersModule
import com.treefuerza.simplepos.di.modules.ProductsModule
import com.treefuerza.simplepos.ui.main.orders.open.OpenOrdersFragment
import com.treefuerza.simplepos.ui.main.products.ProductsFragment
import dagger.Component

@PerChildFragment
@Component(modules = arrayOf(ProductsModule::class), dependencies = arrayOf(AppComponent::class))
interface ProductsComponent {
    fun inject(fragment: ProductsFragment)
}