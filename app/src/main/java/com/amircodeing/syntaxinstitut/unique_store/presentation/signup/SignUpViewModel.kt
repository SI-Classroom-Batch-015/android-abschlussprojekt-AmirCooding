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
    val userProfile = repository.userProfile
    /**
     *
     * Set up Custom FIeld for Inputs in Sign up screen
     * @param signInView represents the basic building block for user interface components
     * @see setupFullNameInputField
     * @see setupEmailSignUpField
     * @see setupUserNameInputField
     * @see setupPasswordInputField
     * @see setupNumberInputField
     * @see setupStreetInputField
     * @see setupNumberInputField
     * @see setupZipInputField
     * @see setupCityInputField
     * @see setupCountryInputField
     */

    private fun setupFullNameInputField(signInView: View) {
        signInView.findViewById<CustomInputField>(R.id.signUp_fullName).apply {
            setLabelText("Full Name")
            setInputHint("Allisson Becker")
        }
    }

    private fun setupUserNameInputField(signInView: View) {
        signInView.findViewById<CustomInputField>(R.id.signUp_userName).apply {
            setLabelText("Username")
            setInputHint("Username")
        }
    }


    private fun setupTelNumber(signInView: View) {
        signInView.findViewById<CustomInputField>(R.id.signUpTel).apply {
            setLabelText("Mobile")
            setInputHint("00491157576789")
        }

    }

    private fun setupEmailSignUpField(signInView: View) {
        signInView.findViewById<CustomInputField>(R.id.signUpEmail).apply {
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

    private fun setupStreetInputField(signInView: View) {
        signInView.findViewById<CustomInputField>(R.id.signUp_street_ET).apply {
            setLabelText("Street")
            setInputHint("Eduard-Bernstein-Straße")
        }
    }
    private fun setupNumberInputField(signInView: View) {
        signInView.findViewById<CustomInputField>(R.id.signUp_number_ET).apply {
            setLabelText("Nr")
            setInputHint("10")
        }
    }
    private fun setupZipInputField(signInView: View) {
        signInView.findViewById<CustomInputField>(R.id.signUp_zip_ET).apply {
            setLabelText("Zip")
            setInputHint("28299")
        }
    }
    private fun setupCityInputField(signInView: View) {
        signInView.findViewById<CustomInputField>(R.id.signUp_city_ET).apply {
            setLabelText("City")
            setInputHint("Bremen")
        }
    }
    private fun setupCountryInputField(signInView: View) {
        signInView.findViewById<CustomInputField>(R.id.signUp_country_ET).apply {
            setLabelText("Country")
            setInputHint("Deutschland")
        }
    }



    fun signUp(auth: Auth) {
        viewModelScope.launch {
            val isSuccess = repository.createUser(auth)
            _sessionState.value = if (isSuccess) SessionState.REGISTERED else SessionState.FAILED
        }
    }

    /**
     * Set labels and hints for each input
     */
    fun setViewOnProfileInput(signInView: View) {
        setupFullNameInputField(signInView)
        setupEmailSignUpField(signInView)
        setupTelNumber(signInView)
        setupCityInputField(signInView)
        setupZipInputField(signInView)
        setupStreetInputField(signInView)
        setupCountryInputField(signInView)
        setupNumberInputField(signInView)
    }

    fun setViewOnAuthInput(signInView: View) {
           setupPasswordInputField(signInView)
            setupUserNameInputField(signInView)
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

     fun checkEmptyFieldInProfile(user: User, context: Context): Boolean {
        val emptyFields = mutableListOf<String>()
        with(user) {
            if (fullName.isEmpty()) emptyFields.add("Full Name")
            if (email.isEmpty()) emptyFields.add("email")
            if (tel.isEmpty()) emptyFields.add("telephone")
            with(address) {
                if (this?.street?.isEmpty() == true) emptyFields.add("street")
                if (this?.number?.isEmpty() == true) emptyFields.add("number")
                if (this?.zip?.isEmpty() == true) emptyFields.add("zip")
                if (this?.city?.isEmpty() == true) emptyFields.add("city")
                if (this?.country?.isEmpty() == true) emptyFields.add("country")
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






