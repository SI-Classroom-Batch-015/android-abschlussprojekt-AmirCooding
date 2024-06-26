package com.amircodeing.syntaxinstitut.unique_store.data.model


data class Cart(
    val items: List<Product> = emptyList(),
    val subTotal: Double = 0.0,
    val totalCost: Double = 0.0,
    val shippingPrice: Double = 5.99,
    val countProduct: Int = 0
)
