package com.amircodeing.syntaxinstitut.unique_store.data.remote.firebaseService
import com.amircodeing.syntaxinstitut.unique_store.data.model.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FirestormService(private val uid: String) {
    private val database = Firebase.firestore

    suspend fun setProfile(user: User) {
        database.collection("Profiles").document(uid).set(user).await()
    }
    suspend fun getProfile(uid: String): User? {
        val result = database.collection("Profiles").document(uid).get().await()
        return result.toObject(User::class.java)
    }
}