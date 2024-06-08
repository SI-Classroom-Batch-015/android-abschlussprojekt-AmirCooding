package com.amircodeing.syntaxinstitut.unique_store.data.local.datasource

import com.amircodeing.syntaxinstitut.unique_store.data.model.Category
import com.amircodeing.syntaxinstitut.unique_store.data.model.Product
import com.amircodeing.syntaxinstitut.unique_store.data.model.Setting


interface DataSource {
     fun getCategories() : List<Category>
     fun getSettingElements(): List<Setting>
}