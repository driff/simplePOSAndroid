package com.treefuerza.simplepos.di.modules

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.treefuerza.simplepos.data.AppDatabase
import com.treefuerza.simplepos.data.DataRepository
import com.treefuerza.simplepos.di.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TreeModule(private val application: Application) {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "treefuerza-pos"
        ).build()
    
    @Singleton
    @Provides
    fun provideDataRepository(db: AppDatabase) = DataRepository(db)

    @Provides
    @ApplicationContext
    fun provideApplicationContext():Context{
        return application
    }


}