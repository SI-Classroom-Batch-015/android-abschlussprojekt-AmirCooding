package com.amircodeing.syntaxinstitut.unique_store.presentation.signin

import android.app.Application
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.amircodeing.syntaxinstitut.unique_store.R
import com.amircodeing.syntaxinstitut.unique_store.data.Repository
import com.amircodeing.syntaxinstitut.unique_store.data.local.database.AppDatabase
import com.amircodeing.syntaxinstitut.unique_store.data.model.Auth
import com.amircodeing.syntaxinstitut.unique_store.data.model.User
import com.amircodeing.syntaxinstitut.unique_store.data.remote.apiservice.ApiService
import com.amircodeing.syntaxinstitut.unique_store.data.remote.firebaseService.FirebaseService
import com.amircodeing.syntaxinstitut.unique_store.data.remote.firebaseService.SessionState
import com.amircodeing.syntaxinstitut.unique_store.utils.Constants
import com.amircodeing.syntaxinstitut.unique_store.utils.CustomInputField
import com.google.firebase.database.*
import kotlinx.coroutines.launch
import org.mindrot.jbcrypt.BCrypt

class SignInViewModel (application: Application) : AndroidViewModel(application) {
    private val repository = Repository(ApiService, AppDatabase.getAppDatabase(application) , FirebaseService())
  val isLoggedIn = repository.isLoggedIn

    private val _sessionState = MutableLiveData<SessionState>()
    val sessionState: LiveData<SessionState> get() = _sessionState

    fun signIn(auth: Auth) {
        viewModelScope.launch {
            val isSuccess = repository.signInUser(auth)
            _sessionState.value = if (isSuccess) SessionState.REGISTERED else SessionState.FAILED
        }
    }
     fun setupPasswordInputField(signInView: View) {
        signInView.findViewById<CustomInputField>(R.id.signInPassword).apply {
            setLabelText("Password")
            setInputHint("*********")
            setPasswordMode(true)
        }
    }

     fun setupUserNameInputField(signInView: View) {
        signInView.findViewById<CustomInputField>(R.id.signIn_userName).apply {
            setLabelText("Username")
            setInputHint("Username")
        }
    }

    fun checkEmptyFieldInAuth(auth : Auth, context: Context): Boolean {
        val emptyFields = mutableListOf<String>()
        with(auth) {
            if (email.isEmpty()) emptyFields.add("Username")
            val password = password
            if (password.isEmpty() || password.length < 8) {
                emptyFields.add("password (must be at least 8 characters long and contain at least one special character)")
            }
        }
        if (emptyFields.isNotEmpty()) {
            val message = "The following fields are empty: ${emptyFields.joinToString(", ")}"
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

}


/*     fun callUserToRoomDB(user: User){
        repository.addUserTORoomDB(user)
    } */


