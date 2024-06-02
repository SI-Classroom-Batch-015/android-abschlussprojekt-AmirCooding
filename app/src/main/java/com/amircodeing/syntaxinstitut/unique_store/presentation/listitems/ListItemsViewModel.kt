package com.amircodeing.syntaxinstitut.unique_store.presentation.listitems

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
import com.amircodeing.syntaxinstitut.unique_store.data.remote.firebaseService.FirebaseService

import kotlinx.coroutines.launch
class ListItemsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository(ApiService, AppDatabase.getAppDatabase(application) , FirebaseService())
    val categoryE = repository.categoryElectronic
    val categoryMen = repository.categoryMens
    val categoryWomen = repository.categoryWomen
    val categoryJewelery = repository.categoryJeweler


    private val _categoryWomen = MutableLiveData<List<Product>>()
    val categoryW: LiveData<List<Product>> get() = _categoryWomen

    private val _categoryJewelery = MutableLiveData<List<Product>>()
    val categoryJ: LiveData<List<Product>> get() = _categoryJewelery

    private val _categoryEl = MutableLiveData<List<Product>>()
    val categoryEl: LiveData<List<Product>> get() = _categoryEl

    private val _categoryMen = MutableLiveData<List<Product>>()
    val categoryM: LiveData<List<Product>> get() = _categoryMen

    private val _selectedCategory = MutableLiveData<String>()
    val selectedCategory: LiveData<String> get() = _selectedCategory


        // Set default category
    init {
        _selectedCategory.value = "men"
        loadMenCategory()
    }

    fun setSelectedCategory(category: String) {
        _selectedCategory.value = category
        when (category) {
            "women" -> loadWomenCategory()
            "jewelery" -> loadJeweleryCategory()
            "electronics" -> loadElectronicsCategory()
            "men" -> loadMenCategory()
        }
    }

    private fun loadWomenCategory() {
        viewModelScope.launch {
            _categoryWomen.postValue(repository.categoryWomen.value)
        }
    }
    private fun loadJeweleryCategory() {
        viewModelScope.launch {
            _categoryWomen.postValue(repository.categoryJeweler.value)
        }
    }
    private fun loadElectronicsCategory() {
        viewModelScope.launch {
            _categoryWomen.postValue(repository.categoryElectronic.value)
        }
    }

    private fun loadMenCategory() {
        viewModelScope.launch {
            _categoryWomen.postValue(repository.categoryMens.value)
        }
    }


}