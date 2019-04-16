package com.treefuerza.simplepos.models

import androidx.room.*
import io.reactivex.Flowable
import io.reactivex.Single

@Entity
data class Client(@ColumnInfo(name = "full_name")var fullName: String = "",
                  @PrimaryKey var id: String = "",
                  @ColumnInfo var email: String = "",
                  @ColumnInfo var phone: String = "")

@Dao
interface ClientDao {
    @Query("SELECT * FROM client")
    fun getAll(): Single<List<Client>>

    @Query("SELECT * FROM client WHERE id = (:clientId) LIMIT 1")
    fun loadAllByIds(clientId: String): Single<Client>

    @Query("SELECT * FROM client WHERE full_name LIKE :name LIMIT 1")
    fun findByName(name: String): Single<Client>

    @Insert
    fun insertAll(vararg client: Client)

    @Delete
    fun delete(client: Client)
}