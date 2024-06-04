package com.amircodeing.syntaxinstitut.unique_store.presentation.cart

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.amircodeing.syntaxinstitut.unique_store.data.Repository
import com.amircodeing.syntaxinstitut.unique_store.data.local.database.AppDatabase
import com.amircodeing.syntaxinstitut.unique_store.data.model.Cart
import com.amircodeing.syntaxinstitut.unique_store.data.model.Product
import com.amircodeing.syntaxinstitut.unique_store.data.remote.apiservice.ApiService
import com.amircodeing.syntaxinstitut.unique_store.data.remote.firebaseService.FirebaseService
import kotlinx.coroutines.launch


const val TAG = "CartViewModel"



class CartViewModel(application: Application) : AndroidViewModel(application) {
    private val repository =
        Repository(ApiService, AppDatabase.getAppDatabase(application), FirebaseService())
    private val uid = FirebaseService().userId
    val showCart = repository.showCart


    init {
        getAllProductFromFirebase()
        repository.listenToCartChanges()
    }
    fun increaseItemQuantity(product: Product) {
        viewModelScope.launch {
            val updatedProduct = product.copy(quantity = product.quantity + 1)
            try {
                repository.addToCart(updatedProduct)
            } catch (e: Exception) {
                Log.e(TAG, " Error increaseItem Quantity ")
            }
        }
    }

    fun decreaseItemQuantity(product: Product) {
        viewModelScope.launch {
            repository.removeFromCart(product)
        }
    }

    fun removeFromCart(productId: Product) {
        viewModelScope.launch {
            repository.removeFromCart(productId)

        }
    }

    private fun getAllProductFromFirebase() {
        viewModelScope.launch {
            repository.getAllFromCart()

        }
    }


}
