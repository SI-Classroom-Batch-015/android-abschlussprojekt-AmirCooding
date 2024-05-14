package com.amircodeing.syntaxinstitut.unique_store.data.local.database


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.amircodeing.syntaxinstitut.unique_store.data.local.dao.AppDao
import com.amircodeing.syntaxinstitut.unique_store.data.model.Address
import com.amircodeing.syntaxinstitut.unique_store.data.model.Cart
import com.amircodeing.syntaxinstitut.unique_store.data.model.Product
import com.amircodeing.syntaxinstitut.unique_store.data.model.Rating
import com.amircodeing.syntaxinstitut.unique_store.data.model.User


/**
 * @author Amir Lotfi
 * @Database : to declare the database schema; lists all entities and sets the database version.
 */
/*

@Database(entities = [Product::class, Address::class , Cart::class, User::class, Rating::class] , version = 1)
abstract class AppDataBase() : RoomDatabase(){

    */
/**
     *   Abstract property to provide DAO
     *//*


    abstract  val appDao : AppDao
    companion object{
        private lateinit var INSTANCE: AppDataBase
        fun get(context: Context) : AppDataBase {
            synchronized(AppDataBase::class.java) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDataBase::class.java,
                        "uniqueStore_database"
                    ).build()
                }
                return INSTANCE
            }
        }

    }

}
*/
