package com.amircodeing.syntaxinstitut.unique_store.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.amircodeing.syntaxinstitut.unique_store.data.local.dao.AppDao
import com.amircodeing.syntaxinstitut.unique_store.data.model.Product

@Database(entities = [Product::class], version = 1)
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