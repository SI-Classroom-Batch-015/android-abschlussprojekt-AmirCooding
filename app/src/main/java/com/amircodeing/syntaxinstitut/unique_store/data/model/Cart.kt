package com.amircodeing.syntaxinstitut.unique_store.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Cart(
    val id : Long = 0 ,
    val items : MutableList<Product>,
    val subTotal: Double,
    val shippingPrice : Double,
    val totalCost: Double,
)
