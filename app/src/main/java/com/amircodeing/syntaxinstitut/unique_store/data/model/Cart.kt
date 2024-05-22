package com.amircodeing.syntaxinstitut.unique_store.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Cart(
    val items: List<Product> = emptyList(),
    val subTotal: Double = 0.0,
    val shippingPrice: Double = 0.0,
    val totalCost: Double = 0.0
)