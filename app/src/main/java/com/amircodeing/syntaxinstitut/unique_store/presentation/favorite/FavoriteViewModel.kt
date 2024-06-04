package com.amircodeing.syntaxinstitut.unique_store.presentation.favorite

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.amircodeing.syntaxinstitut.unique_store.data.Repository
import com.amircodeing.syntaxinstitut.unique_store.data.local.database.AppDatabase
import com.amircodeing.syntaxinstitut.unique_store.data.model.Product
import com.amircodeing.syntaxinstitut.unique_store.data.remote.apiservice.ApiService
import com.amircodeing.syntaxinstitut.unique_store.data.remote.firebaseService.FirebaseService
import kotlinx.coroutines.launch

class FavoriteViewModel (application: Application) : AndroidViewModel(application) {
    private val repository = Repository(ApiService, AppDatabase.getAppDatabase(application) , FirebaseService())
    private val _favoriteResult = MutableLiveData<Boolean>()

    val favorites = repository.showFavorites

init {
    getAllFavorite()
}
    fun getAllFavorite() {
        viewModelScope.launch {
            repository.getAllFromFavorite()
        }
    }

    fun removeProductFromFavoriteList(product: Product){
        viewModelScope.launch {
           repository.removeFromFavorite(product)
        }
    }




}


