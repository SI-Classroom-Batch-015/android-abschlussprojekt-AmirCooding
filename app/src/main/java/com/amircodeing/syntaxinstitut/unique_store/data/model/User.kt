package com.amircodeing.syntaxinstitut.unique_store.data.model


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0 ,
    val fullName : String,
    val email : String,
    val tel : String,
    val password: String,
    val cart : Cart,
    val address : Address
)
