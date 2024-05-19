package com.amircodeing.syntaxinstitut.unique_store.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Category(
    val id: Int = 0,
    val category: String,
    val image: String)