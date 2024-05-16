package com.amircodeing.syntaxinstitut.unique_store.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amircodeing.syntaxinstitut.unique_store.data.Repository
import com.amircodeing.syntaxinstitut.unique_store.data.model.Product
import com.amircodeing.syntaxinstitut.unique_store.data.remote.apiservice.ApiService
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val repository = Repository(ApiService)
    val products = repository.bestSellerList
    val category = repository.category

    fun loadData() {
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

}







