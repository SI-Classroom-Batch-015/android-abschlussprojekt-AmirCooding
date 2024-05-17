package com.amircodeing.syntaxinstitut.unique_store.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.amircodeing.syntaxinstitut.unique_store.data.local.datasource.DataSource
import com.amircodeing.syntaxinstitut.unique_store.data.local.datasource.DataSourceImpl
import com.amircodeing.syntaxinstitut.unique_store.data.model.Category
import com.amircodeing.syntaxinstitut.unique_store.data.model.Product
import com.amircodeing.syntaxinstitut.unique_store.data.remote.apiservice.ApiService


const val TAG = "Repository"
class Repository(private val api: ApiService ) {
    private val  dataSource: DataSource = DataSourceImpl()
    /**
     * @_products Declare a MutableLiveData to hold a list of Product objects
     * @products Publicly expose a LiveData object
     */
    private val _products= MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    /**
     * filter best Seller in 7 Items as a LiveData List
     */
    val bestSellerList: LiveData<List<Product>> get() = _products.map {
            productList ->
        productList.sortedByDescending { it.rating?.rate ?: 0.0 }.take(7)
    }

    /**
     * @_category Declare a MutableLiveData to hold a list of Category objects
     * @category Publicly expose a LiveData object
     */
    private val _category= MutableLiveData<List<Category>>()
    val category: LiveData<List<Category>> get() = _category

    /**
     *  @see transfer Data from Home-Screen to Details-Screen
     *
     */
    val product = MutableLiveData<Product>()


    fun setProduct(setProduct : Product) {
        product.postValue(setProduct)
    }

    fun getProduct() : LiveData<Product> {
        return product
    }


    /**
     * fetch Products from api and set it on _product variable
     */
    suspend fun loadProduct() {
        try {
            _products.postValue(api.retrofitService.getProducts() )
            Log.i(TAG, "success loading Products")
        } catch (e: Exception) {
            Log.e(TAG, "Error loading Products $e")
        }
    }

    suspend fun loadCategory() {
        try {
            _category.postValue(dataSource.getCategories() )
            Log.i(TAG, "success loading Products")
        } catch (e: Exception) {
            Log.e(TAG, "Error loading Products $e")
        }
    }

}