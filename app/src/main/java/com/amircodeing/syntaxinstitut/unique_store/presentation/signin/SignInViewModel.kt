package com.amircodeing.syntaxinstitut.unique_store.presentation.signin

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amircodeing.syntaxinstitut.unique_store.R
import com.amircodeing.syntaxinstitut.unique_store.data.Repository
import com.amircodeing.syntaxinstitut.unique_store.data.local.database.AppDatabase
import com.amircodeing.syntaxinstitut.unique_store.data.model.User
import com.amircodeing.syntaxinstitut.unique_store.data.remote.apiservice.ApiService
import com.amircodeing.syntaxinstitut.unique_store.utils.Constants
import com.amircodeing.syntaxinstitut.unique_store.utils.CustomInputField
import com.google.firebase.database.*

class SignInViewModel (application: Application) : AndroidViewModel(application) {
    private val repository = Repository(ApiService, AppDatabase.getAppDatabase(application))
    private val _signInResult = MutableLiveData<SignInResult>()
    val signInResult: LiveData<SignInResult> get() = _signInResult


     fun setupPasswordInputField(signInView: View) {
        signInView.findViewById<CustomInputField>(R.id.signInPassword).apply {
            setLabelText("Password")
            setInputHint("*********")
            setPasswordMode(true)
        }
    }

     fun setupEmailInputField(signInView: View) {
        signInView.findViewById<CustomInputField>(R.id.signInemail).apply {
            setLabelText("Email Address")
            setInputHint("example@gmail.com")
        }
    }

    fun userNameAndPasswordValidation(email: String, password: String, databaseReference: DatabaseReference) {
        databaseReference.orderByChild("email").equalTo(email)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (userSnapshot in dataSnapshot.children) {
                            val userdata = userSnapshot.getValue(User::class.java)
                            if (userdata != null) {
                                callUserToRoomDB(userdata)
                            }
                                Constants.currentUser = userdata
                            if (userdata != null && userdata.password == password) {
                                val userId = userSnapshot.key
                                _signInResult.value = SignInResult.Success(userId)
                                return
                            }
                        }
                        _signInResult.value = SignInResult.Failure
                    } else {
                        _signInResult.value = SignInResult.Failure
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    _signInResult.value = SignInResult.Error(databaseError.message)
                }
            })
    }


    fun callUserToRoomDB(user: User){
        repository.addUserTORoomDB(user)
    }

}

sealed class SignInResult {
    data class Success(val userId: String?) : SignInResult()
    object Failure : SignInResult()
    data class Error(val errorMessage: String) : SignInResult()
}
