package com.amircodeing.syntaxinstitut.unique_store.data.local.datasource

import com.amircodeing.syntaxinstitut.unique_store.data.model.Category

class DataSourceImpl : DataSource {
    override fun getCategories(): List<Category> {
       return listOfCategory
    }
    private val listOfCategory = listOf(
        Category(1, "MEN'S" , "https://b2292265.smushcdn.com/2292265/wp-content/uploads/2022/06/seo-ppc-fashion-retailers-blog-header.jpg?lossy=1&strip=1&webp=1"),
        Category(2, "WOMEN'S" ,"https://live.staticflickr.com/5299/5480740607_0d3e7a8c40_z.jpg"),
        Category(3, "JEWELERY" ,"https://i.pinimg.com/736x/96/24/c6/9624c6b0b7ba7ea35932081fc25f7ac2.jpg"),
        Category(4, "ELECTRONIC" ,"https://images.unsplash.com/photo-1468495244123-6c6c332eeece?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8ZWxlY3Ryb25pYyUyMGRldmljZXN8ZW58MHx8MHx8fDA%3D")
    )
}