package com.amircodeing.syntaxinstitut.unique_store.presentation.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amircodeing.syntaxinstitut.unique_store.data.model.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class FavoriteViewModel : ViewModel() {

    private val _favoriteResult = MutableLiveData<Boolean>()
    val favoriteResult: LiveData<Boolean> get() = _favoriteResult

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
