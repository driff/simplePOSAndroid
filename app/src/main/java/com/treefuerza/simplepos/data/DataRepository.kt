package com.treefuerza.simplepos.data

import com.treefuerza.simplepos.models.*
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class DataRepository (val db: AppDatabase) {
    fun getUser(userId: String) = Observable.just(User(userId, "Johan Garcia", email = "mail@mail.com", password = "1234")).delay(2, TimeUnit.SECONDS)
    fun addUser(user: User) = db.userDao().insertAll(user)
    fun findUserByEmail(email:String) = db.userDao().findByEmail(email)
    fun getOpenOrders() = db.orderDao().getAllOpenOrders()
    fun getItem(code: String) = db.itemDao().findByCode(code)
    fun getItemsByName(name: String) = db.itemDao().findByName(name)
    fun getClientByName(name: String) = db.clientDao().findByName(name)
    fun addItem(item:Item) = db.itemDao().insert(item)
    fun getAllItems() = db.itemDao().getAll()
    fun addOrder(order: Orders) = db.orderDao().insert(order)
    fun addOrderDetails(orderDetails: OrderDetail) = db.orderDetailDao().insertAll(orderDetails)
    fun addOrderTransaction(orders: Orders, details: List<OrderDetail>) = db.orderDao().insertOrderTransaction(orders, details)
}