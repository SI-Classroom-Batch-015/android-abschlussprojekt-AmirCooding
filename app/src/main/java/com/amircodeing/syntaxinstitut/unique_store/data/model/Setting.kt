package com.amircodeing.syntaxinstitut.unique_store.data.model

data class Setting(
     val id: Int = 0,
     val title: String? = null,
     val description: String? = null,
     val icon : Int,
     val fixIcon: Int,
     var isExpandable: Boolean = false
 )

