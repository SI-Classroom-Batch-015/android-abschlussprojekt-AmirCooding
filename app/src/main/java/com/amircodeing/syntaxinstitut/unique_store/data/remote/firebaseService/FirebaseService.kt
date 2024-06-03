package com.amircodeing.syntaxinstitut.unique_store.data.remote.firebaseService
import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.Navigation
import com.amircodeing.syntaxinstitut.unique_store.R
import com.amircodeing.syntaxinstitut.unique_store.data.model.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await


class FirebaseService {
    private var user: FirebaseUser? = Firebase.auth.currentUser
    val isLoggedIn: Boolean
        get() = user != null

    val userId: String?
        get() = user?.uid

    val email: String?
        get() = user?.email

    suspend fun createUserWithEmailAndPassword(auth: Auth): Boolean {
        val result = Firebase.auth.createUserWithEmailAndPassword(auth.email, auth.password).await()
        user = result.user
        return user != null
    }


    suspend fun signInWithEmailAndPassword(auth: Auth): Boolean {
        val result = Firebase.auth.signInWithEmailAndPassword(auth.email, auth.password).await()
        user = result.user
        return user != null
    }

    fun signOut() {
        Firebase.auth.signOut()
    }

}