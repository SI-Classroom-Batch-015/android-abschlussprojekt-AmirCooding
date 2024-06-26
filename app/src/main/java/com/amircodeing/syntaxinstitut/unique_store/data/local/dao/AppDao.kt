package com.amircodeing.syntaxinstitut.unique_store.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.amircodeing.syntaxinstitut.unique_store.data.model.Cart
import com.amircodeing.syntaxinstitut.unique_store.data.model.Product
import com.amircodeing.syntaxinstitut.unique_store.data.model.User

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

    @Query("UPDATE product_table SET isLiked =:isLiked WHERE id=:id")
    fun  productUpdate(id : Int , isLiked : Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addProductToFavorites(product: Product)
    @Delete
    fun removeProductFromFavorites(product: Product)
    @Query("SELECT * FROM product_table WHERE isLiked=1")
    fun getAllLiked() : LiveData<List<Product>>



    @Query("SELECT * FROM user_table WHERE id = :userId")
    fun getUserById(userId: String): User?

    @Update
    fun updateUser(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUserToDB(user: User)

    @Query("SELECT * FROM user_table")
    fun getAllUser() : LiveData<List<User>>





}