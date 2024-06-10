package com.amircodeing.syntaxinstitut.unique_store.presentation.home

import android.app.Application
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import coil.load
import com.amircodeing.syntaxinstitut.unique_store.R
import com.amircodeing.syntaxinstitut.unique_store.data.Repository
import com.amircodeing.syntaxinstitut.unique_store.data.local.database.AppDatabase
import com.amircodeing.syntaxinstitut.unique_store.data.model.Product
import com.amircodeing.syntaxinstitut.unique_store.data.remote.apiservice.ApiService
import com.amircodeing.syntaxinstitut.unique_store.data.remote.firebaseService.FirebaseService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository(ApiService, AppDatabase.getAppDatabase(application) , FirebaseService())

    val products = repository.products
    val category = repository.category
    val carts = repository.showCart
    val user = repository.userProfile

    private val _cartBadge = MutableLiveData<Int>()
    val cartBadge: LiveData<Int> get() = _cartBadge
    val favoriteProductsId: LiveData<List<Int>> get() = repository.favoriteProductsId

    val showCountFavoritesLiveData: LiveData<Int> = repository.favoriteProductCountFlow
        .distinctUntilChanged()
        .asLiveData()

    val showCountCartLiveData: LiveData<Int> = repository.cartProductCountFlow
        .distinctUntilChanged()
        .asLiveData()
    fun updateCartProductCount() {
        viewModelScope.launch {
            try {
                val count = repository.countProductCart()
                _cartBadge.postValue(count)
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error fetching favorite product count", e)
                _cartBadge.postValue(0)
            }
        }
    }

    init {
        getProfile()
    }

    fun getFavoriteProductsId() {
        viewModelScope.launch {
            repository.getFavoriteProductsId()
        }
    }

    fun addFavorite(product: Product) {
        viewModelScope.launch {
            repository.addToFavorite(product)
            getFavoriteProductsId()
        }
    }
    fun addProductToCart(product: Product) {
        viewModelScope.launch {
            repository.addToCart(product)
        }
    }
    init {
       // updateFavoriteProductCount()
        deleteAllProducts()
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            repository.loadProduct()
        }
    }


    fun loadCategory() {
        viewModelScope.launch {
            repository.loadCategory()
        }
    }

    fun setProduct(product: Product) {
        viewModelScope.launch {
            repository.setProduct(product)
        }
    }

    fun getProduct(): LiveData<Product> {
        return repository.getProduct()
    }

    fun deleteAllProducts() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }

    fun updateProduct(id: Int, isLiked: Boolean) {
        viewModelScope.launch {
            repository.updateProduct(id, isLiked)
        }
    }

    fun getIdCurrentsFavorite() : List<Int> {
        var favorites = mutableListOf<Int>()
        viewModelScope.launch {
         for(item in repository.getAllFromFavorite()){
           favorites.add(item.id)
         }
        }
        return  favorites
    }
    fun getProfile() {
        viewModelScope.launch {
            repository.getUserProfile()
        }
    }


}











