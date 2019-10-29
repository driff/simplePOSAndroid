package com.treefuerza.simplepos.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.treefuerza.simplepos.models.*

@Database(
    entities = [User::class, Orders::class, Item::class, OrderDetail::class, Client::class],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        @JvmField
        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Rename orders table
                database.execSQL("ALTER TABLE Orders RENAME TO _Orders_old_1190923220635")
                // Create new Orders table
                database.execSQL(
                    "CREATE TABLE Orders('id' TEXT PRIMARY KEY NOT NULL, 'sequence' INTEGER NOT NULL, 'creator' " +
                            "TEXT NOT NULL, 'client' TEXT NOT NULL, 'total' REAL NOT NULL, 'subtotal' " +
                            "REAL NOT NULL, 'tax' REAL NOT NULL, 'tip' REAL NOT NULL, 'itemsQuantity' INTEGER " +
                            "NOT NULL, 'created_at' TEXT NOT NULL, 'size' INTEGER NOT NULL, 'status' INTEGER " +
                            "NOT NULL)"
                )
//                // copy content to new table // Check why this doesn't work
//                database.execSQL(
//                    "INSERT INTO 'Orders' ('id', 'sequence', 'creator', 'client', 'total', 'subtotal', 'tax', 'tip'," +
//                            "'itemsQuantity', 'created_at', 'size', 'status') SELECT 'id', 0, 'creator', 'client', " +
//                            "'total', 'subtotal', 'tax', 'tip', 'itemsQuantity', 'created_at', 'size', 'status' " +
//                            "FROM '_Orders_old_1190923220635'"
//                )
            }
        }
    }

    abstract fun userDao(): UserDao
    abstract fun itemDao(): ItemDao
    abstract fun clientDao(): ClientDao
    abstract fun orderDao(): OrderDao
    abstract fun orderDetailDao(): OrderDetailDao

}

