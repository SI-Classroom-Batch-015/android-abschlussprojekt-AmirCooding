package com.amircodeing.syntaxinstitut.unique_store.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.amircodeing.syntaxinstitut.unique_store.data.local.dao.AppDao
import com.amircodeing.syntaxinstitut.unique_store.data.model.Product
import com.amircodeing.syntaxinstitut.unique_store.data.model.User
import com.amircodeing.syntaxinstitut.unique_store.presentation.cart.Converters

@Database(entities = [Product::class, User::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase() : RoomDatabase() {
    abstract val appDao: AppDao
    companion object {
        private lateinit var INSTANCE: AppDatabase
        fun getAppDatabase(context: Context): AppDatabase {
            synchronized(AppDatabase::class.java) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_database_db"
                    ).allowMainThreadQueries().build()
                }
                return INSTANCE
            }
        }
    }
}