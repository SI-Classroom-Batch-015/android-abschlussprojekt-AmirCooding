package com.amircodeing.syntaxinstitut.unique_store.presentation.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
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
    val userProfile = repository.userProfile


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











