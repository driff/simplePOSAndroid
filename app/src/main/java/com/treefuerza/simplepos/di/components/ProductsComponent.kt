package com.treefuerza.simplepos.di.components

import com.treefuerza.simplepos.di.PerChildFragment
import com.treefuerza.simplepos.di.modules.ProductsModule
import com.treefuerza.simplepos.ui.main.products.ProductsFragment
import dagger.Component

@PerChildFragment
@Component(modules = [ProductsModule::class], dependencies = [AppComponent::class])
interface ProductsComponent {
    fun inject(fragment: ProductsFragment)
}