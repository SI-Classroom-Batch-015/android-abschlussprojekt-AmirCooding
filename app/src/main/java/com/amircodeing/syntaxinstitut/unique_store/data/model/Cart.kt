package com.amircodeing.syntaxinstitut.unique_store.data.model

import java.security.MessageDigest

data class Cart(
    val items: List<Product> = emptyList(),
    val subTotal: Double? = 0.0,
    val totalCost: Double = 0.0,
    val shippingPrice: Double = 0.0,
    val countProduct : Int = 1
)



