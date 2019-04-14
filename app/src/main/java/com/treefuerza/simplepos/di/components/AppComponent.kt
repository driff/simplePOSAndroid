package com.treefuerza.simplepos.di.components

import android.content.Context
import com.treefuerza.simplepos.data.AppDatabase
import com.treefuerza.simplepos.data.DataRepository
import com.treefuerza.simplepos.di.ApplicationContext
import com.treefuerza.simplepos.di.modules.AppModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun userRepository(): DataRepository

    @ApplicationContext
    fun getContext(): Context

    fun getDatabaseManager(): AppDatabase
}