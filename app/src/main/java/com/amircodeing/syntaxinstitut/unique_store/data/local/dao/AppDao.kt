package com.amircodeing.syntaxinstitut.unique_store.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amircodeing.syntaxinstitut.unique_store.data.model.Product

@Dao
interface AppDao {

    @Query("SELECT * FROM product_table ")

    fun getAllProducts(): LiveData<List<Product>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProducts(products: List<Product>)

    @Query("SELECT * FROM product_table WHERE rate IS NOT NULL ORDER BY rate DESC LIMIT 7 ")
    fun getListBestSeller(): LiveData<List<Product>>
    @Query("SELECT * FROM product_table WHERE category ='men''s clothing'")
    fun getListMenClothing(): LiveData<List<Product>>

    @Query("SELECT * FROM product_table WHERE category ='women''s clothing'")
    fun getListWomenClothing(): LiveData<List<Product>>

    @Query("SELECT * FROM product_table WHERE category ='electronics'")
    fun getListElectronics(): LiveData<List<Product>>

    @Query("SELECT * FROM product_table WHERE category ='jewelery'")
    fun getListJewelery(): LiveData<List<Product>>

    @Query("DELETE FROM product_table")
    suspend fun deleteAll()
}