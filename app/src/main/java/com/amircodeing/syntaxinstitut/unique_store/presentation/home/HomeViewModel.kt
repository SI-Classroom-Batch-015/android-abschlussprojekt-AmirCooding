package com.amircodeing.syntaxinstitut.unique_store.presentation.home

import AppDataBase
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.amircodeing.syntaxinstitut.unique_store.data.Repository
import com.amircodeing.syntaxinstitut.unique_store.data.model.Product
import com.amircodeing.syntaxinstitut.unique_store.data.remote.apiservice.ApiStatus
import com.amircodeing.syntaxinstitut.unique_store.data.remote.apiservice.ApiService
import kotlinx.coroutines.launch
const val  TAG = "Repository"
class HomeViewModel(application: Application) : AndroidViewModel(application){
    private val repository = Repository(ApiService , AppDataBase.get(application))
    val products = repository.bestSellerList
    val category = repository.category

    private val _loading = MutableLiveData<ApiStatus>()

    val productOne = repository.products

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            _loading.value = ApiStatus.LOADING
            try {
                repository.loadProducts()
                _loading.value = ApiStatus.DONE
            }catch (e: Exception){
                Log.e(TAG, "Error load Data $e")
                if(productOne.value.isNullOrEmpty()){
                    _loading.value = ApiStatus.ERROR
                }else{
                    _loading.value = ApiStatus.DONE
                }
            }

        }

    }

    fun loadCategory() {
        viewModelScope.launch {
         /*    repository.loadCategory() */
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







