package com.amircodeing.syntaxinstitut.unique_store.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "product_table")
data class Product(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0 ,
    var title: String? = null,
    var price: Double? = null,
    var description: String? = null,
    var category: String? = null,
    var image: String? = null,
    var isLiked:Boolean? = false,
    var quantity: Int = 1,
    @Embedded
   var rating: Rating? = Rating()

)
