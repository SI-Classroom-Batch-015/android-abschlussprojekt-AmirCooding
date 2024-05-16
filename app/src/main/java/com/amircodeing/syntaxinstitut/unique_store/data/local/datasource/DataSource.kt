package com.amircodeing.syntaxinstitut.unique_store.data.local.datasource

import com.amircodeing.syntaxinstitut.unique_store.data.model.Category
import com.amircodeing.syntaxinstitut.unique_store.data.model.Product



interface DataSource {
     fun getCategories() : List<Category>
}