package com.amircodeing.syntaxinstitut.unique_store.presentation.favorite

import android.app.Application
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
import kotlinx.coroutines.launch

class FavoriteViewModel (application: Application) : AndroidViewModel(application) {
    private val repository = Repository(ApiService, AppDatabase.getAppDatabase(application))
    private val _favoriteResult = MutableLiveData<Boolean>()
    val favoriteResult: LiveData<Boolean> get() = _favoriteResult

    fun setProduct(product: Product) {
        viewModelScope.launch {
            repository.setProduct(product)
        }
    }
    fun getProduct(): LiveData<Product> {
        return repository.getProduct()
    }
    fun addProductToFavorites(product: Product) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val database = FirebaseDatabase.getInstance().reference
            database.child("users").child(it.uid).child("favorites").child(product.id.toString()).setValue(product)
                .addOnCompleteListener { task ->
                    _favoriteResult.value = task.isSuccessful
                }
        }
    }

    fun removeProductFromFavorites(product: Product) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val database = FirebaseDatabase.getInstance().reference
            database.child("users").child(it.uid).child("favorites").child(product.id.toString()).removeValue()
                .addOnCompleteListener { task ->
                    _favoriteResult.value = task.isSuccessful
                }
        }
    }
}
