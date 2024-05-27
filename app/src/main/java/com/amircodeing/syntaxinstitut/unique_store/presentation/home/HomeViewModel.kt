package com.amircodeing.syntaxinstitut.unique_store.presentation.home

import android.app.Application
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.load
import com.amircodeing.syntaxinstitut.unique_store.data.Repository
import com.amircodeing.syntaxinstitut.unique_store.data.local.database.AppDatabase
import com.amircodeing.syntaxinstitut.unique_store.data.model.Cart
import com.amircodeing.syntaxinstitut.unique_store.data.model.Product
import com.amircodeing.syntaxinstitut.unique_store.data.model.User
import com.amircodeing.syntaxinstitut.unique_store.data.remote.CrudFDataBase
import com.amircodeing.syntaxinstitut.unique_store.data.remote.apiservice.ApiService
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository(ApiService, AppDatabase.getAppDatabase(application))
    val products = repository.products
    val category = repository.category

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

    fun addProductToFavoriteList(product: Product) {
        viewModelScope.launch {
            repository.addProductToFavorite(product)
        }
    }
    fun updateProduct(id: Int, isLiked: Boolean) {
        viewModelScope.launch {
            repository.updateProduct(id, isLiked)
        }
    }

    fun updateCartForUser(userId: String, newCart : Product){
        viewModelScope.launch {
            repository.updateCartForUser(userId,newCart)
        }
    }


    //////////////////////////////////////TODO save product id To DB
    fun addFavoriteToFBDB(userId: String, productId: String, context: Context){
        CrudFDataBase.addFavoriteProductToDbFirebase(userId, productId, context)
    }



}











