package com.amircodeing.syntaxinstitut.unique_store.data.model

import androidx.room.PrimaryKey

data class Address(
    val street: String = "",
    val number: String = "",
    val zip: String = "",
    val city: String = "",
    val country: String = ""
)