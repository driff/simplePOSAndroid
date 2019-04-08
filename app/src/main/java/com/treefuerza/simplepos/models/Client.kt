package com.treefuerza.simplepos.models

import androidx.room.*
import io.reactivex.Flowable
import io.reactivex.Single

@Entity
data class Client(@ColumnInfo(name = "first_name")var firstName: String = "",
                  @ColumnInfo(name= "last_name")var lastName: String = "",
                  @PrimaryKey var id: String = "",
                  @ColumnInfo var email: String = "",
                  @ColumnInfo var phone: String = "")

@Dao
interface ClientDao {
    @Query("SELECT * FROM client")
    fun getAll(): Single<List<Client>>

    @Query("SELECT * FROM client WHERE id = (:clientId) LIMIT 1")
    fun loadAllByIds(clientId: String): Single<Client>

    @Query("SELECT * FROM client WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): Single<Client>

    @Insert
    fun insertAll(vararg client: Client)

    @Delete
    fun delete(client: Client)
}