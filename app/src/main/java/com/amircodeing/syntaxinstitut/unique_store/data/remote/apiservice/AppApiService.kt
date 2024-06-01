package com.amircodeing.syntaxinstitut.unique_store.data.remote.apiservice

import com.amircodeing.syntaxinstitut.unique_store.data.model.Product
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface AppApiService {
    @GET("/products")
    suspend fun getProducts(): List<Product>
}


object ApiService {
    val retrofitService: AppApiService by lazy { retrofit.create(AppApiService::class.java) }
}

