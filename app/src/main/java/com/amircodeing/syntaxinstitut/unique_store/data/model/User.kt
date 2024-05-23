package com.amircodeing.syntaxinstitut.unique_store.data.model


import androidx.room.Embedded


data class User(
    val id: String = "",
    val fullName: String = "",
    val email: String = "",
    val tel: String = "",
    val password: String = "",
    val image: String? = null,
    @Embedded
    val cart: Cart? = null,
    @Embedded
    val address: Address ? = null
)



