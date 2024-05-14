package com.amircodeing.syntaxinstitut.unique_store.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "cart_table")
data class Cart(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0 ,
    val items : MutableList<Product>,
    val subTotal: Double,
    val shippingPrice : Double,
    val totalCost: Double,
)
