package com.treefuerza.simplepos.models

import androidx.room.*
import com.treefuerza.simplepos.utils.STATUS_CREATED
import com.treefuerza.simplepos.utils.STATUS_DELETED
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*
@Entity
data class Orders(
    @PrimaryKey var id: String = UUID.randomUUID().toString(),
    @ColumnInfo var creator: String,
    @ColumnInfo var client: String = "Client",
    @ColumnInfo var total: Double = 0.0,
    @ColumnInfo var subtotal: Double = 0.0,
    @ColumnInfo var tax: Double = 0.0,
    @ColumnInfo var tip: Double = 0.0,
    @ColumnInfo var itemsQuantity: Int = 0,
    @ColumnInfo(name = "created_at") var createdAt: String = "",
    @ColumnInfo val size: Int = 0, //People on table
    @ColumnInfo var status: Int = 0
    //@Relation(parentColumn = "id", entityColumn = "order_id", entity = OrderDetail::class)
   /* @Ignore var details: List<OrderDetail> = listOf()*/) {
}

@Dao
interface OrderDao {
    @Query("SELECT * FROM orders ORDER BY created_at ASC")
    fun getAllFromOrder(): Single<List<Orders>>

    @Query("SELECT * FROM orders WHERE status = $STATUS_CREATED ORDER BY created_at ASC")
    fun getAllOpenOrders(): Flowable<List<Orders>>

    @Query("SELECT * FROM orders WHERE status <> $STATUS_DELETED")
    fun loadAllNotDeleted(): Single<List<Orders>>

    @Query("SELECT * FROM orders WHERE id = :id LIMIT 1")
    fun findById(id: String): Single<Orders>

    @Transaction
    fun insertOrderTransaction(orders: Orders, details: List<OrderDetail>) {
        insert(orders)
        insertDetails(details)
    }

    @Insert
    fun insert(orders: Orders)

    @Insert
    fun insertDetails(orderDetails: List<OrderDetail>)

    @Delete
    fun delete(order: Orders)
}