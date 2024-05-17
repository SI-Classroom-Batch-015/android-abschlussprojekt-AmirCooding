package com.amircodeing.syntaxinstitut.unique_store.data.model


import androidx.room.Entity
import androidx.room.PrimaryKey


data class User(
    val id : Long = 0 ,
    val fullName : String,
    val email : String,
    val tel : String,
    val password: String,
    val cart : Cart,
    val address : Address
)
