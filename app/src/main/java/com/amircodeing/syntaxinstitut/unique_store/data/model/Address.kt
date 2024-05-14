package com.amircodeing.syntaxinstitut.unique_store.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "address_table")
 data class Address (
    @PrimaryKey(autoGenerate = true)
     val id : Long = 0 ,
     val street: String,
     val number : Int,
     val  plz : String,
     val city : String,
     val country : String
 )
