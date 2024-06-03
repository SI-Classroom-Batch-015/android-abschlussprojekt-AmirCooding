package com.amircodeing.syntaxinstitut.unique_store.presentation.signup

import android.app.Application
import android.content.Context
import android.net.Uri
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
import com.amircodeing.syntaxinstitut.unique_store.utils.CustomInputField
import kotlinx.coroutines.launch

class SignUpViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository(ApiService, AppDatabase.getAppDatabase(application) , FirebaseService())

    private val _sessionState = MutableLiveData<SessionState>()
    val sessionState: LiveData<SessionState> get() = _sessionState


    private fun setupEmailSignUpField(signInView: View) {
        signInView.findViewById<CustomInputField>(R.id.signUp_userName).apply {
            setLabelText("Email Address")
            setInputHint("example@gmail.com")
        }
    }


    private fun setupPasswordInputField(signInView: View) {
        signInView.findViewById<CustomInputField>(R.id.signUpPassword).apply {
            setLabelText("Password")
            setInputHint("*********")
            setPasswordMode(true)
        }
    }

    fun signUp(auth: Auth) {
        viewModelScope.launch {
            val isSuccess = repository.createUser(auth)
            _sessionState.value = if (isSuccess) SessionState.REGISTERED else SessionState.FAILED
        }
    }



    fun setViewOnAuthInput(signInView: View) {
           setupPasswordInputField(signInView)
           setupEmailSignUpField(signInView)
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






