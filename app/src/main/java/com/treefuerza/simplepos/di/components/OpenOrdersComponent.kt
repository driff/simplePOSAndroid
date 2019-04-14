package com.treefuerza.simplepos.di.components

import com.treefuerza.simplepos.di.PerChildFragment
import com.treefuerza.simplepos.di.modules.OpenOrdersModule
import com.treefuerza.simplepos.ui.main.orders.open.OpenOrdersFragment
import dagger.Component

@PerChildFragment
@Component(modules = arrayOf(OpenOrdersModule::class), dependencies = arrayOf(AppComponent::class))
interface OpenOrdersComponent {
    fun inject(fragment: OpenOrdersFragment)
}