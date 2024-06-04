package com.amircodeing.syntaxinstitut.unique_store.data.remote.firebaseService
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.amircodeing.syntaxinstitut.unique_store.data.model.Cart
import com.amircodeing.syntaxinstitut.unique_store.data.model.Product
import com.amircodeing.syntaxinstitut.unique_store.data.model.User
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.math.RoundingMode

const val TAG = "FirestoreService"
class FirestoreService(private val uid: String) {
    private val database = Firebase.firestore

    suspend fun setProfile(user: User) {
        database.collection("Profiles").document(uid).set(user).await()
    }
    suspend fun getProfile(uid: String): User? {
        val result = database.collection("Profiles").document(uid).get().await()
        return result.toObject(User::class.java)
    }

    suspend fun addToFavorite(uid: String, product: Product): List<Product> {
        val favoritesRef = database.collection("Profiles").document(uid).collection("Favorites")
        favoritesRef.add(product).await()
        return favoritesRef.get().await().toObjects(Product::class.java)
    }

    suspend fun removeFromFavorite(uid: String, product: Product): List<Product> {
        val favoritesRef = database.collection("Profiles").document(uid).collection("Favorites")
        val query = favoritesRef.whereEqualTo("id", product.id)
        val snapshot = query.get().await()
        if (snapshot.documents.isNotEmpty()) {
            val documentId = snapshot.documents.first().id
            favoritesRef.document(documentId).delete().await()
        }

        return favoritesRef.get().await().toObjects(Product::class.java)
    }


    suspend fun getAllFavorite(uid: String)  : List<Product>{
        val result = database.collection("Profiles").
        document(uid).collection("Favorites").get().await()
        return  result.toObjects(Product::class.java)
    }

    suspend fun addProductToCart(uid: String, product: Product) {
        val cartDocument = database.collection("Profiles").document(uid).collection("Carts").document("cart")
        val cartSnapshot = cartDocument.get().await()
        val cart = if (cartSnapshot.exists()) {
            cartSnapshot.toObject(Cart::class.java)!!
        } else {
            Cart()
        }
        updateCart(cartDocument, cart, product)
    }

    suspend fun updateCart(cartDocument: DocumentReference, cart: Cart, product: Product) {
        val updatedItems = cart.items.toMutableList()
        val existingIndex = updatedItems.indexOfFirst { it.id == product.id }

        if (existingIndex != -1) {
            // Product already exists in the cart, increment the quantity
            updatedItems[existingIndex] = updatedItems[existingIndex].copy(quantity = (updatedItems[existingIndex].quantity ?: 1) + 1)
        } else {
            // Product does not exist in the cart, add it with a quantity of 1
            updatedItems.add(product.copy(quantity = 1))
        }

        val updatedSubTotal = updatedItems.sumOf { (it.price ?: 0.0) * (it.quantity ?: 1) }.toBigDecimal().setScale(2, RoundingMode.HALF_UP).toDouble()
        val updatedCount = updatedItems.sumOf { it.quantity ?: 1 }
        val shippingPrice = if (updatedCount == 0) 0.0 else 5.99
        val updatedTotalCost = (updatedSubTotal + shippingPrice).toBigDecimal().setScale(2, RoundingMode.HALF_UP).toDouble()

        val updatedCart = Cart(
            items = updatedItems,
            subTotal = updatedSubTotal,
            totalCost = updatedTotalCost,
            countProduct = updatedCount
        )

        cartDocument.set(updatedCart).await()
    }


    suspend fun removeFromCart(uid: String, product: Product) {
        val cartDocument = database.collection("Profiles").document(uid).collection("Carts").document("cart")
        val cartSnapshot = cartDocument.get().await()
        val cart = if (cartSnapshot.exists()) {
            cartSnapshot.toObject(Cart::class.java)!!
        } else {
            return
        }

        val updatedItems = cart.items.toMutableList()
        val existingIndex = updatedItems.indexOfFirst { it.id == product.id }

        if (existingIndex != -1) {
            // Product exists in the cart
            if (updatedItems[existingIndex].quantity!! > 1) {
                // Decrease the quantity by 1
                updatedItems[existingIndex] = updatedItems[existingIndex].copy(quantity = (updatedItems[existingIndex].quantity ?: 1) - 1)
            } else {
                // Remove the product from the cart
                updatedItems.removeAt(existingIndex)
            }

            val updatedSubTotal = updatedItems.sumOf { (it.price ?: 0.0) * (it.quantity ?: 1) }
            val updatedCount = updatedItems.sumOf { it.quantity ?: 1 }
            val shippingPrice = if (updatedCount == 0) 0.0 else 5.99
            val updatedTotalCost = updatedSubTotal + shippingPrice

            val updatedCart = Cart(
                items = updatedItems,
                subTotal = updatedSubTotal,
                totalCost = updatedTotalCost,
                countProduct = updatedCount
            )

            cartDocument.set(updatedCart).await()
        }
    }



    /*     private suspend fun updateCartAfterRemoval(cartDocument: DocumentReference, updatedItems: MutableList<Product>) {
            val updatedSubTotal = updatedItems.sumOf { it.price ?: 0.0 }
            val updatedCount = updatedItems.size
            val shippingPrice = if (updatedCount == 0) 0.0 else 5.99
            val updatedTotalCost = updatedSubTotal + shippingPrice

            val updatedCart = Cart(
                items = updatedItems,
                subTotal = updatedSubTotal,
                totalCost = updatedTotalCost,
                countProduct = updatedCount
            )

            cartDocument.set(updatedCart).await()
        } */

    suspend fun getAllProductsFromCart(uid: String): Cart? {
        val result = database.collection("Profiles")
            .document(uid).collection("Carts").document("cart").get().await()
        return result.toObject(Cart::class.java)
    }

    fun listenToCartChanges(cartLiveData: MutableLiveData<Cart>) {
        val cartDocument = database.collection("Profiles").document(uid).collection("Carts").document("cart")
        cartDocument.addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.e(TAG, "Listen failed.", error)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                val cart = snapshot.toObject(Cart::class.java)
                cartLiveData.postValue(cart)
            } else {
                Log.d(TAG, "Current data: null")
            }
        }
    }


}

