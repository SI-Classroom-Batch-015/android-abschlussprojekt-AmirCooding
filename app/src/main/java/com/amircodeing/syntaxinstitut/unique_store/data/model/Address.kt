package com.amircodeing.syntaxinstitut.unique_store.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


 data class Address (
     val id : Long = 0 ,
     val street: String,
     val number : Int,
     val  plz : String,
     val city : String,
     val country : String
 )
