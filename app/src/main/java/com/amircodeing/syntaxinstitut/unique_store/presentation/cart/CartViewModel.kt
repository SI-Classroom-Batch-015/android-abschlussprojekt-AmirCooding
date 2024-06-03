package com.amircodeing.syntaxinstitut.unique_store.presentation.cart

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.amircodeing.syntaxinstitut.unique_store.data.Repository
import com.amircodeing.syntaxinstitut.unique_store.data.local.database.AppDatabase
import com.amircodeing.syntaxinstitut.unique_store.data.model.Cart
import com.amircodeing.syntaxinstitut.unique_store.data.model.Product
import com.amircodeing.syntaxinstitut.unique_store.data.model.User
import com.amircodeing.syntaxinstitut.unique_store.data.remote.apiservice.ApiService
import com.amircodeing.syntaxinstitut.unique_store.data.remote.firebaseService.FirebaseService
import kotlinx.coroutines.launch





class CartViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository(ApiService, AppDatabase.getAppDatabase(application) , FirebaseService())
    private val userId = FirebaseService()
    val showCart = repository.showCart

    private val _totalCart = MutableLiveData<Cart>()
    val totalCart: LiveData<Cart> = _totalCart


    fun calculateTotals(carts: List<Cart>) {
        val subTotal = carts.sumOf { it.subTotal }
        val countProduct = carts.sumOf { it.countProduct }
        val shippingPrice = 5.99
        val totalCost = subTotal + shippingPrice

        _totalCart.value = Cart(emptyList(), subTotal, totalCost, shippingPrice, countProduct)
    }

    fun increaseItemQuantity( product: Product) {
        viewModelScope.launch {
            userId.userId?.let { repository.updateCartForUser(it, product.copy(quantity = product.quantity + 1)) }
        }
    }

    fun decreaseItemQuantity(product: Product) {
        viewModelScope.launch {
            if (product.quantity > 1) {
                userId.userId?.let { repository.updateCartForUser(it, product.copy(quantity = product.quantity - 1)) }
            } else {
              /*   userId.userId?.let { repository.removeFromCart(product.id.toString()) } */
            }
        }
    }

    fun getAllProductFromFirebase() {
        viewModelScope.launch {
           repository.getAllFromCart()
        }
    }

    // TODO remove from favorite

/*     fun removeItemFromCart( product: Product) {
        viewModelScope.launch {
            userId.userId?.let { repository.removeFromCart(product.id) }
        }
    } */

}
