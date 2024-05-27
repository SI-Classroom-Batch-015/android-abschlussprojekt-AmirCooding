package com.amircodeing.syntaxinstitut.unique_store.presentation.favorite

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amircodeing.syntaxinstitut.unique_store.data.Repository
import com.amircodeing.syntaxinstitut.unique_store.data.local.database.AppDatabase
import com.amircodeing.syntaxinstitut.unique_store.data.model.Product
import com.amircodeing.syntaxinstitut.unique_store.data.remote.apiservice.ApiService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class FavoriteViewModel (application: Application) : AndroidViewModel(application) {
    private val repository = Repository(ApiService, AppDatabase.getAppDatabase(application))
    private val _favoriteResult = MutableLiveData<Boolean>()
    val favoriteResult: LiveData<Boolean> get() = _favoriteResult

    val favorites = repository.showFavorites

    init {
        loadProducts()
    }
    private fun loadProducts() {
        viewModelScope.launch {
            repository.getAllFavorite()
        }
    }

    fun removeProductFromFavoriteList(product: Product){
        viewModelScope.launch {
            repository.removeProductFromFavorite(product)
            repository.updateProduct(product.id , isLiked = false)
        }
    }




}


