package com.amircodeing.syntaxinstitut.unique_store.presentation.home

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
import com.amircodeing.syntaxinstitut.unique_store.presentation.favorite.FavoriteViewModel
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository(ApiService, AppDatabase.getAppDatabase(application) , FirebaseService())

    val products = repository.products
    val category = repository.category

    private val _favoriteBadge = MutableLiveData<Int>()
    val showCountFavorites: LiveData<Int> get() = _favoriteBadge

    private val _cartBadge = MutableLiveData<Int>()
    val cartBadge: LiveData<Int> get() = _cartBadge

    fun updateCartProductCount() {
        viewModelScope.launch {
            try {
                val count = repository.getAllCountProductCart()
                _cartBadge.postValue(count)
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error fetching favorite product count", e)
                _cartBadge.postValue(0)
            }
        }
    }

    fun updateFavoriteProductCount() {
        viewModelScope.launch {
            try {
                val count = repository.getFavoriteProductCount()
                _favoriteBadge.postValue(count)
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error fetching favorite product count", e)

              //  _favoriteBadge.postValue(0)
            }
        }
    }

     fun addFavorite(product: Product) {
        viewModelScope.launch {
            repository.addToFavorite(product)
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




}











