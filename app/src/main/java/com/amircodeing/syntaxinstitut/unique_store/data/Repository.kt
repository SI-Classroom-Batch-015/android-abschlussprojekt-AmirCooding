package com.amircodeing.syntaxinstitut.unique_store.data

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amircodeing.syntaxinstitut.unique_store.data.local.database.AppDatabase
import com.amircodeing.syntaxinstitut.unique_store.data.local.datasource.DataSource
import com.amircodeing.syntaxinstitut.unique_store.data.local.datasource.DataSourceImpl
import com.amircodeing.syntaxinstitut.unique_store.data.model.Auth
import com.amircodeing.syntaxinstitut.unique_store.data.model.Cart
import com.amircodeing.syntaxinstitut.unique_store.data.model.Category
import com.amircodeing.syntaxinstitut.unique_store.data.model.Product
import com.amircodeing.syntaxinstitut.unique_store.data.model.Setting
import com.amircodeing.syntaxinstitut.unique_store.data.model.User
import com.amircodeing.syntaxinstitut.unique_store.data.remote.apiservice.ApiService
import com.amircodeing.syntaxinstitut.unique_store.data.remote.firebaseService.FireStorageService
import com.amircodeing.syntaxinstitut.unique_store.data.remote.firebaseService.FirebaseService
import com.amircodeing.syntaxinstitut.unique_store.data.remote.firebaseService.FirestoreService
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


const val TAG = "Repository"

class Repository(
    private val api: ApiService,
    private val database: AppDatabase,
    private val firebaseService: FirebaseService
) {
    private val dataSource: DataSource = DataSourceImpl()
    private val fireStorageService = FireStorageService()
    private val userId = FirebaseAuth.getInstance().currentUser?.uid
    private val firestoreService = userId?.let { FirestoreService(it) }

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

    private val _favoriteProductsId = MutableLiveData<List<Int>>()
    val favoriteProductsId: LiveData<List<Int>> get() = _favoriteProductsId
    private val _categoryMens: LiveData<List<Product>> = database.appDao.getListMenClothing()
    val categoryMens: LiveData<List<Product>> get() = _categoryMens

    private val _categoryWomen: LiveData<List<Product>> = database.appDao.getListWomenClothing()
    val categoryWomen: LiveData<List<Product>> get() = _categoryWomen

    private val _categoryJeweler: LiveData<List<Product>> = database.appDao.getListJewelery()
    val categoryJeweler: LiveData<List<Product>> get() = _categoryJeweler

    private val _showCart = MutableLiveData<Cart>()
    val showCart: LiveData<Cart> get() = _showCart


    private val _userProfile = MutableLiveData<User>()
    val userProfile: LiveData<User> get() = _userProfile
    val isLoggedIn = firebaseService.isLoggedIn



    /**
     *
     * @_category Declare a MutableLiveData to hold a list of Category objects
     * @category Publicly expose a LiveData object
     */
    private val _category = MutableLiveData<List<Category>>()
    val category: LiveData<List<Category>> get() = _category

    private val _settingElements = MutableLiveData<List<Setting>>()
    val settingElements: LiveData<List<Setting>> get() = _settingElements

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
            Log.i(TAG, "success loading Products")
        } catch (e: Exception) {
            Log.e(TAG, "Error loading Products $e")
        }
    }

   fun loadCategory() {
        try {
            _category.postValue(dataSource.getCategories())
            Log.i(TAG, "success loading category")
        } catch (e: Exception) {
            Log.e(TAG, "Error loading category $e")
        }
    }
     fun loadStting() {
        try {
            _settingElements.postValue(dataSource.getSettingElements())
            Log.i(TAG, "success loading category")
        } catch (e: Exception) {
            Log.e(TAG, "Error loading category $e")
        }
    }

    //TODO improve  this method what should be deleted ? and where should it be deleted ?
    suspend fun deleteAll() {
        try {
            database.appDao.deleteAll()
            Log.i(TAG, "success Delete All Product from ....")
        } catch (e: Exception) {
            Log.e(TAG, "Error loading category $e")
        }
    }

    suspend fun createUser(auth: Auth): Boolean {
        return try {
            signOut()
            firebaseService.createUserWithEmailAndPassword(auth)
        } catch (e: Exception) {
            Log.e(Repository::class.simpleName, "Could not create a user")
            false
        }
    }

    suspend fun signInUser(auth: Auth): Boolean {
        return try {
            val result = firebaseService.signInWithEmailAndPassword(auth)
            result
        } catch (e: Exception) {
            Log.e(Repository::class.simpleName, "Could not log-in the user")
            false
        }
    }


    fun signOut() {
        try {
            firebaseService.signOut()
        } catch (e: Exception) {
            Log.e(Repository::class.simpleName, "Could not log-out the user")
        }
    }


    suspend fun setProfileWithImage(user: User, imageUri: Uri?): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                if (imageUri != null) {
                    val imageUrl = fireStorageService.uploadProfileImage(imageUri)
                    user.image = imageUrl
                }
                setProfile(user)
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }


    private suspend fun setProfile(user: User): Boolean {
        try {
            // val uid = firebaseService.userId ?: return false
            firestoreService?.setProfile(user)
            /*      if (userId != null) {
                getUserProfile()
            } */
            return true
        } catch (e: Exception) {
            Log.e(Repository::class.simpleName, "Could not create a profile")
            return false
        }
    }

    suspend fun getUserProfile() {
        try {
          _userProfile.postValue(firestoreService?.getProfile(firebaseService.userId.toString()))
        } catch (e: Exception) {
            Log.e(Repository::class.simpleName, "Could not get profile")
        }
    }

       suspend fun addToFavorite(product: Product): List<Product> {
        return try {
            val uid = firebaseService.userId
            if (uid != null) {
                firestoreService?.addToFavorite(uid, product) ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            Log.e(Repository::class.simpleName, "Could not save Favorite: $e")
            emptyList()
        }
    }

    suspend fun removeFromFavorite(product: Product): List<Product> {
        return try {
            val uid = firebaseService.userId
            if (uid != null) {
                firestoreService?.removeFromFavorite(uid, product) ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            Log.e(Repository::class.simpleName, "Could not remove Favorite: $e")
            emptyList()
        }
    }

    suspend fun getFavoriteProductsId() {
        try {
            _favoriteProductsId.postValue(firestoreService?.getFavoriteProductIds(firebaseService.userId.toString()))
        } catch (e: Exception) {
            Log.e(Repository::class.simpleName, "Could not get favorite product IDs", e)
        }
    }


    suspend fun getAllFromFavorite(): List<Product> {
        try {
            val products: MutableList<Product> = mutableListOf()
            for (item in firestoreService?.getAllFavorite(firebaseService.userId.toString())!!) {
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
            return products
        } catch (e: Exception) {
            Log.e(TAG, "Error loading Products From Firebase: $e")
            return emptyList()
        }
    }


    private suspend fun getFavoriteProductCount(): Int {
        return try {
            firestoreService?.getFavoriteProductCount(firebaseService.userId.toString()) ?: 0
        } catch (e: Exception) {
            Log.e(Repository::class.simpleName, "Could not get count Favorites", e)
            0
        }
    }

    val cartProductCountFlow: Flow<Int> = flow {
        while (true) {
            emit(countProductCart())
        }
    }

    val favoriteProductCountFlow: Flow<Int> = flow {
        while (true) {
            emit(getFavoriteProductCount())
        }
    }



    suspend fun addToCart(product: Product) {
        try {
            firebaseService.userId?.let { firestoreService?.addProductToCart(it, product) }
        } catch (e: Exception) {
            Log.e(Repository::class.simpleName, "Could not save Favorite")
        }
    }
    suspend fun countProductCart(): Int {
        return try {
            val uid = firebaseService.userId
            if (uid != null) {
                firestoreService?.getAllProductsFromCart(uid)?.countProduct ?: 0
            } else {
                0
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error loading Products From Firebase to cart", e)
            0
        }
    }
    suspend fun getAllFromCart() {
        try {
            val uid = firebaseService.userId
            _showCart.postValue(uid?.let { firestoreService?.getAllProductsFromCart(it) })
            Log.i(TAG, "success loading Products From Firebase to cart")
        } catch (e: Exception) {
            Log.e(TAG, "Error loading Products From Firebase to cart $e")
        }
    }

    fun listenToCartChanges() {
        try {
            firestoreService?.listenToCartChanges(_showCart)
            Log.i(TAG, "success listen to change cart")
        } catch (e: Exception) {
            Log.e(TAG, "Error loading Products From Firebase to cart $e")
        }
    }

    suspend fun removeFromCart( productId: Product) {
        try {
            firebaseService.userId?.let { firestoreService?.removeFromCart(it, productId) }
            Log.i(TAG, "success loading Products From Firebase to cart")
        } catch (e: Exception) {
            Log.e(TAG, "Error loading Products From Firebase to cart $e")
        }
    }

    fun updateProduct(id: Int, isLiked: Boolean) {
        database.appDao.productUpdate(id, isLiked)
    }

    suspend fun deleteAllItemsFromCart() {
        try {
            firestoreService?.deleteAllProductsFromCart(firebaseService.userId.toString())
            Log.i(TAG, "success delete Products From Firebase to cart")
        } catch (e: Exception) {
            Log.e(TAG, "Error delete  Products From Firebase to cart $e")
        }
    }
    }












