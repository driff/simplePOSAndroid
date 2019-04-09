package com.treefuerza.simplepos.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.treefuerza.simplepos.models.*

@Database(entities = arrayOf(User::class, Orders::class, Item::class, OrderDetail::class, Client::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun itemDao(): ItemDao
    abstract fun clientDao(): ClientDao
    abstract fun orderDao(): OrderDao
    abstract fun orderDetailDao(): OrderDetailDao

}