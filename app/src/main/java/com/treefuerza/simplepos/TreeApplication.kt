package com.treefuerza.simplepos

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.treefuerza.simplepos.di.components.DaggerTreeComponent
import com.treefuerza.simplepos.di.components.TreeComponent
import com.treefuerza.simplepos.di.modules.TreeModule

class TreeApplication : Application() {

    val component: TreeComponent by lazy { createComponent() }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }

    protected open fun createComponent(): TreeComponent {
        return DaggerTreeComponent.builder()
            .treeModule(TreeModule(this))
            .build()
    }
}