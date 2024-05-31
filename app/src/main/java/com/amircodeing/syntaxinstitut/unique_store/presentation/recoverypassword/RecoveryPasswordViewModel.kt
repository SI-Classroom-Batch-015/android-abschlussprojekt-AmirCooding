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
import com.amircodeing.syntaxinstitut.unique_store.data.model.User
import com.amircodeing.syntaxinstitut.unique_store.data.remote.CrudFDataBase
import com.amircodeing.syntaxinstitut.unique_store.data.remote.apiservice.ApiService
import com.amircodeing.syntaxinstitut.unique_store.presentation.signin.SignInResult
import com.amircodeing.syntaxinstitut.unique_store.utils.Constants
import com.amircodeing.syntaxinstitut.unique_store.utils.CustomInputField
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import org.mindrot.jbcrypt.BCrypt

class RecoveryPasswordViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository(ApiService, AppDatabase.getAppDatabase(application))
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    fun setupEmailInputField(signInView: View) {
        signInView.findViewById<CustomInputField>(R.id.recoveryPassword_email_TV).apply {
            setLabelText("Please Enter Your Email")
            setInputHint("example@gmail.com")
        }
    }


    fun setupNewPasswordInputField(signInView: View) {
        signInView.findViewById<CustomInputField>(R.id.recoveryPassword_newPassword_TV).apply {
            setLabelText("Enter New Password")
            setInputHint("*********")
            setPasswordMode(true)
        }
    }

    fun setupRepeatNewPasswordInputField(signInView: View) {
        signInView.findViewById<CustomInputField>(R.id.recoveryPassword_repeat_TV).apply {
            setLabelText(" Repeat New Password ")
            setInputHint("*********")
            setPasswordMode(true)
        }
    }

    fun updatePassword(
        email: String,
        newPassword: String,
        repeatPassword: String,
        databaseReference: DatabaseReference,
        context: Context,
        view: View
    ) {
        if (newPassword.isEmpty() || repeatPassword.isEmpty()) {
            Toast.makeText(context, "Password or repeat password is Empty", Toast.LENGTH_LONG)
                .show()
            return
        }

        if (newPassword != repeatPassword) {
            Toast.makeText(context, "Passwords do not match", Toast.LENGTH_LONG).show()
            return
        }

        databaseReference.orderByChild("email").equalTo(email)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (userSnapshot in snapshot.children) {
                            val userData = userSnapshot.getValue(User::class.java)
                            if (userData != null) {
                                val hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt())
                                val userKey = userSnapshot.key ?: return
                                databaseReference.child(userKey).child("password")
                                    .setValue(hashedPassword)
                                val navController = Navigation.findNavController(view)
                                navController.navigate(R.id.signInFragment)
                                Toast.makeText(
                                    context,
                                    "Successfully changed password",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    } else {
                        Toast.makeText(context, "User not found", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context, "Error: ${error.message}", Toast.LENGTH_LONG).show()
                }
            })
    }
}

