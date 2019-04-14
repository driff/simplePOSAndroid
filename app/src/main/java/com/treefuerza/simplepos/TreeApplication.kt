package com.treefuerza.simplepos

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.treefuerza.simplepos.di.components.AppComponent
import com.treefuerza.simplepos.di.components.DaggerAppComponent
import com.treefuerza.simplepos.di.modules.AppModule

class TreeApplication : Application() {

    lateinit var component: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        component = createComponent()
        AndroidThreeTen.init(this)
    }

    private fun createComponent(): AppComponent {
        return DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    companion object {
        private var INSTANCE: TreeApplication? = null

        @JvmStatic
        fun get(): TreeApplication = INSTANCE!!
    }

}