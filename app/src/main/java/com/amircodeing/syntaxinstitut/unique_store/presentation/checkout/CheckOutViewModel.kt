package com.amircodeing.syntaxinstitut.unique_store.presentation.checkout

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.amircodeing.syntaxinstitut.unique_store.data.Repository
import com.amircodeing.syntaxinstitut.unique_store.data.local.database.AppDatabase
import com.amircodeing.syntaxinstitut.unique_store.data.model.User
import com.amircodeing.syntaxinstitut.unique_store.data.remote.apiservice.ApiService
import com.amircodeing.syntaxinstitut.unique_store.data.remote.firebaseService.FirebaseService
import kotlinx.coroutines.launch

class CheckOutViewModel (application: Application) : AndroidViewModel(application) {
    private val repository = Repository(ApiService, AppDatabase.getAppDatabase(application) , FirebaseService())

     val user = repository.userProfile
    val items = repository.showCart

    init {
        getUserInfo()
        getCartItems()
    }
    fun getUserInfo(){
        viewModelScope.launch {
            repository.getUserProfile()
        }
    }
    fun deleteAllCart(){
        viewModelScope.launch {
            repository.deleteAllItemsFromCart()
        }
    }

    fun getCartItems(){
        viewModelScope.launch {
            repository.getAllFromCart()
        }
    }
}