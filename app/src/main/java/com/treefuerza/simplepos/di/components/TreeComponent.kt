package com.treefuerza.simplepos.di.components

import android.content.Context
import com.treefuerza.simplepos.data.AppDatabase
import com.treefuerza.simplepos.di.ApplicationContext
import com.treefuerza.simplepos.di.modules.TreeModule
import com.treefuerza.simplepos.models.UserRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [TreeModule::class])
interface TreeComponent {
    fun userRepository(): UserRepository

    @ApplicationContext
    fun getContext(): Context

    fun getDatabaseManager(): AppDatabase
}