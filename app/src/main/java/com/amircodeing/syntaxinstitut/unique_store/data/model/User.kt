package com.amircodeing.syntaxinstitut.unique_store.data.model


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey
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



