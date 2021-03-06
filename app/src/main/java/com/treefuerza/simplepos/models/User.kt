package com.treefuerza.simplepos.models

import androidx.room.*
import io.reactivex.Observable
import io.reactivex.Single
import java.util.concurrent.TimeUnit

@Entity(indices = arrayOf(Index(value = ["email"], unique = true)))
data class User(@PrimaryKey @ColumnInfo(name = "user_id") var userId: String,
                @ColumnInfo var name: String,
                @ColumnInfo var password: String,
                @ColumnInfo var email: String)

@Dao
interface UserDao {
        @Query("SELECT * FROM user")
        fun getAll(): Single<List<User>>

        @Query("SELECT * FROM user WHERE user_id = (:userId) LIMIT 1")
        fun loadAllByIds(userId: String): Single<User>

        @Query("SELECT * FROM user WHERE email LIKE :email LIMIT 1")
        fun findByEmail(email: String): Single<User>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insertAll(user: User)

        @Update
        fun update(user: User)

        @Delete
        fun delete(user: User)
}