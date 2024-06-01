package com.amircodeing.syntaxinstitut.unique_store.data.remote.firebaseService


import com.amircodeing.syntaxinstitut.unique_store.data.model.Auth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FirebaseService {

    private var user: FirebaseUser? = null

    val isLoggedIn: Boolean
        get() = user != null

    val userId: String?
        get() = user?.uid

    val email: String?
        get() = user?.email

    suspend fun createUserWithUserNameAndPassword(auth: Auth): Boolean {
        val result = Firebase.auth.createUserWithEmailAndPassword(auth.username, auth.password).await()
        user = result.user
        return user != null

    }

    suspend fun signInWithEmailAndPassword(auth: Auth): Boolean {
        val result = Firebase.auth.signInWithEmailAndPassword(auth.username, auth.password).await()
        user = result.user
        return user != null
    }

    fun signOut() {
        Firebase.auth.signOut()
    }
}