package com.amircodeing.syntaxinstitut.unique_store.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amircodeing.syntaxinstitut.unique_store.data.local.database.AppDatabase
import com.amircodeing.syntaxinstitut.unique_store.data.local.datasource.DataSource
import com.amircodeing.syntaxinstitut.unique_store.data.local.datasource.DataSourceImpl
import com.amircodeing.syntaxinstitut.unique_store.data.model.Cart
import com.amircodeing.syntaxinstitut.unique_store.data.model.Category
import com.amircodeing.syntaxinstitut.unique_store.data.model.Product
import com.amircodeing.syntaxinstitut.unique_store.data.model.User
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


    private val _showFavorites: LiveData<List<Product>> = database.appDao.getAllLiked()
    val showFavorites: LiveData<List<Product>> get() = _showFavorites

    private val _showProductsToCart: LiveData<List<User>> = database.appDao.getAllUser()
    val showProductsToCart: LiveData<List<User>> get() = _showProductsToCart


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

    suspend fun deleteAll() {
        try {
            database.appDao.deleteAll()
            Log.i(TAG, "success loading category")
        } catch (e: Exception) {
            Log.e(TAG, "Error loading category $e")
        }
    }


    fun getAllFavorite() {
        try {
            database.appDao.getAllLiked()
            Log.i(TAG, "success loading List of Favorite")
        } catch (e: Exception) {
            Log.e(TAG, "Error loading List of Favorite $e")
        }
    }

    fun addProductToFavorite(product: Product) {
        try {
            database.appDao.addProductToFavorites(product)
            Log.i(TAG, "success add Product of Favorite")
        } catch (e: Exception) {
            Log.e(TAG, "Error remove Product of Favorite $e")
        }
    }

    fun addUserTORoomDB(user: User) {
        try {
            database.appDao.addUserToDB(user)
            Log.i(TAG, "success add user to DB")
        } catch (e: Exception) {
            Log.e(TAG, "Error add user to DB $e")
        }
    }

    fun removeProductFromFavorite(product: Product) {
        try {
            database.appDao.removeProductFromFavorites(product)
            Log.i(TAG, "success remove List of Favorite")
        } catch (e: Exception) {
            Log.e(TAG, "Error remove List of Favorite $e")
        }
    }

    fun updateProduct(id: Int, isLiked: Boolean) {
        database.appDao.productUpdate(id, isLiked)
    }

    suspend fun updateCartForUser(userId: String, updatedProduct: Product) {
        val user = database.appDao.getUserById(userId)
        user?.let {
            val updatedCartItems = it.cart?.items?.toMutableList() ?: mutableListOf()
            val existingProductIndex =
                updatedCartItems.indexOfFirst { product -> product.id == updatedProduct.id }
            if (existingProductIndex != -1) {
                updatedCartItems[existingProductIndex] = updatedProduct
            } else {
                updatedCartItems.add(updatedProduct)
            }
            updateCartPrices(userId, updatedCartItems)
        }
    }

    suspend fun removeFromCart(userId: String, product: Product) {
        val user = database.appDao.getUserById(userId)
        user?.let {
            val updatedCartItems = it.cart?.items?.filter { it.id != product.id } ?: listOf()
            updateCartPrices(userId, updatedCartItems)
        }
    }

    private fun updateCartPrices(userId: String, updatedCartItems: List<Product>) {
        val subTotal =
            updatedCartItems.sumOf { product -> (product.price ?: 0.00) * product.quantity }
        val countProduct = updatedCartItems.sumOf { product -> product.quantity }
        val shippingPrice = 5.99
        val totalCost = if (countProduct == 0) 0.0 else subTotal + shippingPrice
        val formattedSubTotal = String.format("%.2f", subTotal).toDouble()
        val formattedTotalCost = String.format("%.2f", totalCost).toDouble()
        val updatedCart = Cart(
            items = updatedCartItems,
            subTotal = formattedSubTotal,
            shippingPrice = shippingPrice,
            totalCost = formattedTotalCost,
            countProduct = countProduct
        )
        val user = database.appDao.getUserById(userId)
        user?.let {
            val updatedUser = it.copy(cart = updatedCart)
            database.appDao.updateUser(updatedUser)
        }
    }
}

/*

    fun updateCartForUser(userId: String, newProduct: Product) {
        try {
            val user = database.appDao.getUserById(userId)
            user?.let {
                val updatedCartItems = it.cart?.items?.toMutableList() ?: mutableListOf()
                updatedCartItems.add(newProduct)
                val subTotal = updatedCartItems.sumOf { product -> product.price ?: 0.0 }
                val countProduct = (user.cart?.countProduct ?: 0) +1
              //  val formattedSubTotal = String.format("%.2f", subTotal)
                val shippingPrice = 5.99
                val totalCost = subTotal + shippingPrice
                val updatedCart = Cart(
                    items = updatedCartItems,
                    subTotal = subTotal,
                    shippingPrice = shippingPrice,
                    totalCost = totalCost,
                    countProduct = countProduct
                )
                val updatedUser = it.copy(cart = updatedCart)
                database.appDao.updateUser(updatedUser)
            }
            Log.i(TAG, "success add Product to  Cart")
        } catch (e: Exception) {
            Log.e(TAG, "Error add Product to Cart $e")

        }
    }
*/




