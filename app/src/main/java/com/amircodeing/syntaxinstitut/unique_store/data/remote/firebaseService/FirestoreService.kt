package com.amircodeing.syntaxinstitut.unique_store.data.remote.firebaseService
import com.amircodeing.syntaxinstitut.unique_store.data.model.Cart
import com.amircodeing.syntaxinstitut.unique_store.data.model.Product
import com.amircodeing.syntaxinstitut.unique_store.data.model.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FirestoreService(private val uid: String) {
    private val database = Firebase.firestore

    suspend fun setProfile(user: User) {
        database.collection("Profiles").document(uid).set(user).await()
    }
    suspend fun getProfile(uid: String): User? {
        val result = database.collection("Profiles").document(uid).get().await()
        return result.toObject(User::class.java)
    }

    suspend fun addToFavorite(uid: String , product: Product) {
     database.collection("Profiles").document(uid).collection("Favorites").
      add(product).await()

    }
    suspend fun getAllFavorite(uid: String)  : List<Product>{
        val result = database.collection("Profiles").
        document(uid).collection("Favorites").get().await()
       return  result.toObjects(Product::class.java)
    }


    suspend fun addToCart(uid: String , cart: Cart) {
        database.collection("Profiles").document(uid).collection("Carts").
        add(cart).await()
    }

    suspend fun removeFromCart(uid: String, productId: Product) {
        val cartCollection = database.collection("Profiles").document(uid).collection("Carts")
        val querySnapshot = cartCollection.whereEqualTo("id", productId).get().await()

        for (document in querySnapshot.documents) {
            cartCollection.document(document.id).delete().await()
        }
    }
    suspend fun getAllProductsFromCart(uid: String)  : List<Cart>{
        val result = database.collection("Profiles").
        document(uid).collection("Carts").get().await()
        return  result.toObjects(Cart::class.java)
    }
}