package com.amircodeing.syntaxinstitut.unique_store.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amircodeing.syntaxinstitut.unique_store.data.model.Product

@Dao
interface AppDao {

    @Query("SELECT * FROM product_table")
    fun getAllProducts(): LiveData<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(products: List<Product>)

}