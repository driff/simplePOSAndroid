package com.treefuerza.simplepos.di.components

import com.treefuerza.simplepos.di.PerChildFragment
import com.treefuerza.simplepos.di.modules.CreateOrderModule
import com.treefuerza.simplepos.di.modules.OpenOrdersModule
import com.treefuerza.simplepos.ui.main.orders.create.CreateOrderFragment
import com.treefuerza.simplepos.ui.main.orders.open.OpenOrdersFragment
import dagger.Component

@PerChildFragment
@Component(modules = arrayOf(CreateOrderModule::class), dependencies = arrayOf(AppComponent::class))
interface CreateOrdersComponent {
    fun inject(fragment: CreateOrderFragment)
}