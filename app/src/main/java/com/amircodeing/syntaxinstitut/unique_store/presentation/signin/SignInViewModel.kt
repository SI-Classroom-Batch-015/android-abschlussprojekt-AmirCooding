package com.amircodeing.syntaxinstitut.unique_store.presentation.signin

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amircodeing.syntaxinstitut.unique_store.R
import com.amircodeing.syntaxinstitut.unique_store.data.model.User
import com.amircodeing.syntaxinstitut.unique_store.utils.Constants
import com.amircodeing.syntaxinstitut.unique_store.utils.CustomInputField
import com.google.firebase.database.*

class SignInViewModel : ViewModel() {

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

}

sealed class SignInResult {
    data class Success(val userId: String?) : SignInResult()
    object Failure : SignInResult()
    data class Error(val errorMessage: String) : SignInResult()
}
