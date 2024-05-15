package com.amircodeing.syntaxinstitut.unique_store.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amircodeing.syntaxinstitut.unique_store.data.Repository
import com.amircodeing.syntaxinstitut.unique_store.data.remote.apiservice.ApiService
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val repository = Repository(ApiService)
    val products = repository.products
    fun loadData() {
        viewModelScope.launch {
            repository.loadProduct()
        }
    }
}