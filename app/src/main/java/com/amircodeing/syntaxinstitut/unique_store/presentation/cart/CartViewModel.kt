package com.amircodeing.syntaxinstitut.unique_store.presentation.cart

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.amircodeing.syntaxinstitut.unique_store.data.Repository
import com.amircodeing.syntaxinstitut.unique_store.data.local.database.AppDatabase
import com.amircodeing.syntaxinstitut.unique_store.data.model.Product
import com.amircodeing.syntaxinstitut.unique_store.data.model.User
import com.amircodeing.syntaxinstitut.unique_store.data.remote.apiservice.ApiService
import kotlinx.coroutines.launch

class CartViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository(ApiService, AppDatabase.getAppDatabase(application))
    val users: LiveData<List<User>> = repository.userInformation
    private val _cartItems = MutableLiveData<MutableList<Product>>()
    val cartItems: LiveData<MutableList<Product>> = _cartItems
    fun increaseItemQuantity(userId: String, product: Product) {
        viewModelScope.launch {
            repository.updateCartForUser(userId, product.copy(quantity = product.quantity + 1))
        }
    }

    fun decreaseItemQuantity(userId: String, product: Product) {
        viewModelScope.launch {
            if (product.quantity > 1) {
                repository.updateCartForUser(userId, product.copy(quantity = product.quantity - 1))
            } else {
                repository.removeFromCart(userId, product)
            }
        }
    }

    fun removeItemFromCart(userId: String, product: Product) {
        viewModelScope.launch {
            repository.removeFromCart(userId, product)
        }
    }

}
