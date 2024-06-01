package com.amircodeing.syntaxinstitut.unique_store.presentation.recoverypassword

import android.app.Application
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.Navigation
import com.amircodeing.syntaxinstitut.unique_store.R
import com.amircodeing.syntaxinstitut.unique_store.data.Repository
import com.amircodeing.syntaxinstitut.unique_store.data.local.database.AppDatabase
import com.amircodeing.syntaxinstitut.unique_store.data.remote.apiservice.ApiService
import com.amircodeing.syntaxinstitut.unique_store.data.remote.firebaseService.FirebaseService
import com.amircodeing.syntaxinstitut.unique_store.utils.CustomInputField
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
class RecoveryPasswordViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository(ApiService, AppDatabase.getAppDatabase(application) , FirebaseService())
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    fun setupEmailInputField(signInView: View) {
        signInView.findViewById<CustomInputField>(R.id.recoveryPassword_email_TV).apply {
            setLabelText("Please Enter Your Email")
            setInputHint("example@gmail.com")
        }
    }



}

