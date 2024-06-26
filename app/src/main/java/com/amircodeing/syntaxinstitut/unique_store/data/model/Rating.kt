package com.amircodeing.syntaxinstitut.unique_store.data.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

data class Rating(
    @SerializedName("rate") var rate: Double? = null,
    @SerializedName("count") var count: Int? = null

)