package com.amircodeing.syntaxinstitut.unique_store.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amircodeing.syntaxinstitut.unique_store.data.model.Product
import com.amircodeing.syntaxinstitut.unique_store.data.remote.apiservice.ApiService
import com.amircodeing.syntaxinstitut.unique_store.data.remote.apiservice.AppApiService


const val TAG = "Repository"
class Repository(private val api: ApiService) {

    private val _products= MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    suspend fun loadProduct() {
        try {
          _products.postValue(api.retrofitService.getProducts() )
            Log.i(TAG, "success loading Products")
        } catch (e: Exception) {
            Log.e(TAG, "Error loading Products $e")
        }
    }

}