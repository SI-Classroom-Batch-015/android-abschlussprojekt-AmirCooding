package com.amircodeing.syntaxinstitut.unique_store.presentation.signin

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amircodeing.syntaxinstitut.unique_store.R
import com.amircodeing.syntaxinstitut.unique_store.data.Repository
import com.amircodeing.syntaxinstitut.unique_store.data.local.database.AppDatabase
import com.amircodeing.syntaxinstitut.unique_store.data.model.User
import com.amircodeing.syntaxinstitut.unique_store.data.remote.apiservice.ApiService
import com.amircodeing.syntaxinstitut.unique_store.utils.Constants
import com.amircodeing.syntaxinstitut.unique_store.utils.CustomInputField
import com.google.firebase.database.*
import org.mindrot.jbcrypt.BCrypt

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

     fun setupUserNameInputField(signInView: View) {
        signInView.findViewById<CustomInputField>(R.id.signIn_userName).apply {
            setLabelText("Username")
            setInputHint("Username")
        }
    }


    fun userNameAndPasswordValidation(userName: String, password: String, databaseReference: DatabaseReference) {
        databaseReference.orderByChild("userName").equalTo(userName)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (userSnapshot in dataSnapshot.children) {
                            val userdata = userSnapshot.getValue(User::class.java)
                            if (userdata != null) {
                                // Validate hashed password
                                if (BCrypt.checkpw(password, userdata.password)) {
                                    // Password matches
                                    callUserToRoomDB(userdata)
                                    Constants.currentUser = userdata
                                    val userId = userSnapshot.key
                                    _signInResult.value = SignInResult.Success(userId)
                                    return
                                } else {
                                    // Password does not match
                                    _signInResult.value = SignInResult.WrongPassword
                                    return
                                }
                            }
                        }
                    } else {
                        // User not found
                        _signInResult.value = SignInResult.UserNotFound
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
/**
 * this class represent the different results of a sign-in attempt
 * @see UserNotFound Represents a failed sign-in due to the username not being found in the database
 * @see WrongPassword Represents a failed sign-in due to the password being incorrect
 .در زبان برنامه‌نویسی Kotlin، از object برای تعریف یک شیء تک‌نمونه‌ای (singleton) استفاده می‌شود. وقتی از object استفاده می‌کنید،
 * در واقع می‌گویید که تنها یک نمونه از آن نوع خاص وجود دارد. این ویژگی زمانی مفید است
 * که نوع داده نیازی به نگهداری وضعیت (داده‌ها) ندارد و دلیلی برای داشتن نمونه‌های متعدد از آن وجود ندارد.
 *
 *
 */
sealed class SignInResult {
    data class Success(val userId: String?) : SignInResult()
    data object UserNotFound : SignInResult()
    data object WrongPassword : SignInResult()
    data class Error(val errorMessage: String) : SignInResult()
}
