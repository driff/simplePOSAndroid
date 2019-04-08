package com.treefuerza.simplepos.models

import androidx.room.*
import io.reactivex.Single
import java.util.*

@Entity( primaryKeys = arrayOf("id", "user_id"),
    indices = arrayOf(Index(value = ["alias"], unique = true), Index(value = ["code"], unique = true), Index(value = ["id"], unique = true)))
data class Item(var id: String = UUID.randomUUID().toString(),
                @ColumnInfo(name = "user_id") var userId: String,
                @ColumnInfo var name: String = "",
                @ColumnInfo var alias: String = "",
                @ColumnInfo var price: Double = 0.0,
                @ColumnInfo val code: String = "",
                @ColumnInfo(name = "tax_value") var taxValue: Double = 0.0,
                @ColumnInfo(name = "created_at") var createdAt: String = Calendar.getInstance().time.toString()
)

@Dao
interface ItemDao {
    @Query("SELECT * FROM item")
    fun getAll(): Single<List<Item>>

    @Query("SELECT * FROM item WHERE id IN (:itemId)")
    fun loadAllByIds(itemId: String): Single<List<Item>>

    @Query("SELECT * FROM item WHERE name LIKE :first")
    fun findByName(first: String): Single<List<Item>>

    @Query("SELECT * FROM item WHERE code LIKE :first")
    fun findByCode(first: String): Single<Item>


    @Insert
    fun insertAll(vararg items: Item)

    @Delete
    fun delete(item: Item)
}