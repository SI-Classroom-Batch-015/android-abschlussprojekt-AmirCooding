package com.amircodeing.syntaxinstitut.unique_store.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amircodeing.syntaxinstitut.unique_store.data.local.database.AppDatabase
import com.amircodeing.syntaxinstitut.unique_store.data.local.datasource.DataSource
import com.amircodeing.syntaxinstitut.unique_store.data.local.datasource.DataSourceImpl
import com.amircodeing.syntaxinstitut.unique_store.data.model.Category
import com.amircodeing.syntaxinstitut.unique_store.data.model.Product
import com.amircodeing.syntaxinstitut.unique_store.data.remote.apiservice.ApiService


const val TAG = "Repository"

class Repository(private val api: ApiService, private val database: AppDatabase) {
    private val dataSource: DataSource = DataSourceImpl()

    /**
     * @_products Declare a MutableLiveData to hold a list of Product objects
     * @products Publicly expose a LiveData object
     */

    //private val _products = MutableLiveData<List<Product>>()
   private val _products: LiveData<List<Product>> = database.appDao.getAllProducts()
    val products: LiveData<List<Product>> get() = _products

    /**
     * sort categories a LiveData List
     *
     */

    private val _categoryElectronic: LiveData<List<Product>> = database.appDao.getListElectronics()
    val categoryElectronic: LiveData<List<Product>> get() = _categoryElectronic


    private val _categoryMens: LiveData<List<Product>> = database.appDao.getListMenClothing()
    val categoryMens: LiveData<List<Product>> get() = _categoryMens

    private val _categoryWomen: LiveData<List<Product>> = database.appDao.getListWomenClothing()
    val categoryWomen: LiveData<List<Product>> get() = _categoryWomen

    private val _categoryJeweler: LiveData<List<Product>> = database.appDao.getListJewelery()
    val categoryJeweler: LiveData<List<Product>> get() = _categoryJeweler

    /**
     *
     * @_category Declare a MutableLiveData to hold a list of Category objects
     * @category Publicly expose a LiveData object
     */
    private val _category = MutableLiveData<List<Category>>()
    val category: LiveData<List<Category>> get() = _category

    /**
     *  @see transfer Data from Home-Screen to Details-Screen
     *
     */
    val product = MutableLiveData<Product>()


    fun setProduct(setProduct: Product) {
        product.postValue(setProduct)
    }

    fun getProduct(): LiveData<Product> {
        return product
    }


    /**
     * fetch Products from api and set it on _product variable
     */
    suspend fun loadProduct() {
        try {
           //   _products.postValue(api.retrofitService.getProducts())
            val products: MutableList<Product> = mutableListOf()
            for (item in api.retrofitService.getProducts()) {
                products.add(
                    Product(
                        title = item.title,
                        price = item.price,
                        description = item.description,
                        category = item.category,
                        image = item.image,
                        rating = item.rating
                    )
                )
            }
            database.appDao.insertProducts(products)
        // _products.postValue(products)
            Log.i(TAG, "success loading Products")
        } catch (e: Exception) {
            Log.e(TAG, "Error loading Products $e")
        }
    }

    suspend fun loadCategory() {
        try {
            _category.postValue(dataSource.getCategories())
            Log.i(TAG, "success loading category")
        } catch (e: Exception) {
            Log.e(TAG, "Error loading category $e")
        }
    }


}

