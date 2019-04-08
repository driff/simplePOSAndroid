package com.treefuerza.simplepos.models

import androidx.room.*
import com.treefuerza.simplepos.utils.STATUS_DELETED
import io.reactivex.Single
import java.util.*

@Entity( indices = arrayOf(Index(value = ["id", "order_id"])),
    tableName = "order_detail", foreignKeys = arrayOf(ForeignKey(entity = Orders::class, parentColumns = arrayOf("id"),
        childColumns = arrayOf("order_id"), onDelete = ForeignKey.CASCADE),
    ForeignKey(entity = Item::class, parentColumns = arrayOf("id"), childColumns = arrayOf("item_id"), onDelete = ForeignKey.NO_ACTION)))
data class OrderDetail(@PrimaryKey var id: String = UUID.randomUUID().toString(),
                       @ColumnInfo(name = "order_id") var orderId: String,
                       @ColumnInfo(name = "item_id") var itemId: String = "",
                       @ColumnInfo var total: Double = 0.0,
                       @ColumnInfo var subtotal: Double = 0.0,
                       @ColumnInfo var tax: Double = 0.0,
                       @ColumnInfo var description: String = "",
                       @ColumnInfo(name = "additional_info") var additionalInfo: String = "",
                       @ColumnInfo(name = "item_price") var itemPrice: Double = 0.0,
                       @ColumnInfo var quantity: Double = 0.0,
                       @ColumnInfo(name = "created_at") var createdAt: String = "",
                       @ColumnInfo var status: Int = 0 )
@Dao
interface OrderDetailDao {
    @Query("SELECT * FROM order_detail WHERE order_id = :orderId ORDER BY created_at ASC")
    fun getAllFromOrder(orderId: String): Single<List<OrderDetail>>

    @Query("SELECT * FROM order_detail WHERE order_id = :orderId AND status <> $STATUS_DELETED LIMIT 1")
    fun loadAllByOrderAndNotDeleted(orderId: String): Single<List<OrderDetail>>

    @Query("SELECT * FROM order_detail WHERE id = :id LIMIT 1")
    fun findById(id: String): Single<OrderDetail>

    @Insert
    fun insertAll(vararg orderDetails: OrderDetail)

    @Delete
    fun delete(orderDetail: OrderDetail)
}