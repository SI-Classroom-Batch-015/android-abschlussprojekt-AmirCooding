package com.amircodeing.syntaxinstitut.unique_store.data.model


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.security.MessageDigest

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey
    val id: String = "",
    val fullName: String = "",
    val userName: String = "",
    val email: String = "",
    val tel: String = "",
    var password: String = "",
    var image: String? = null,
    @Embedded
    val cart: Cart? = null,
    @Embedded
    val address: Address ? = null

)


fun hashPassword(password: String): String {
    val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
    return bytes.joinToString("") { "%02x".format(it) }
}


