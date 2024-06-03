package com.amircodeing.syntaxinstitut.unique_store.presentation.profile

import android.app.Application
import android.content.Context
import android.net.Uri
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amircodeing.syntaxinstitut.unique_store.R
import com.amircodeing.syntaxinstitut.unique_store.data.Repository
import com.amircodeing.syntaxinstitut.unique_store.data.local.database.AppDatabase
import com.amircodeing.syntaxinstitut.unique_store.data.model.User
import com.amircodeing.syntaxinstitut.unique_store.data.remote.apiservice.ApiService
import com.amircodeing.syntaxinstitut.unique_store.data.remote.firebaseService.FirebaseService
import com.amircodeing.syntaxinstitut.unique_store.data.remote.firebaseService.SessionState
import com.amircodeing.syntaxinstitut.unique_store.utils.CustomInputField
import kotlinx.coroutines.launch
const val  TAG = "ProfileViewModel"
class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository(ApiService, AppDatabase.getAppDatabase(application) , FirebaseService())
    private val _sessionState = MutableLiveData<SessionState>()
    val sessionState : LiveData<SessionState> = _sessionState


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

    private fun setupInputField(signInView: View, fieldId: Int, label: String, hint: String) {
        signInView.findViewById<CustomInputField>(fieldId).apply {
            setLabelText(label)
            setInputHint(hint)
        }
    }

    fun setViewOnProfileInput(signInView: View) {
        setupInputField(signInView, R.id.signUp_fullName, "Full Name", "Allisson Becker")
        setupInputField(signInView, R.id.signUpTel, "Mobile", "00491157576789")
        setupInputField(signInView, R.id.signUp_street_ET, "Street", "Eduard-Bernstein-Straße")
        setupInputField(signInView, R.id.signUp_number_ET, "Nr", "10")
        setupInputField(signInView, R.id.signUp_zip_ET, "Zip", "28299")
        setupInputField(signInView, R.id.signUp_city_ET, "City", "Bremen")
        setupInputField(signInView, R.id.signUp_country_ET, "Country", "Deutschland")
    }
    fun setProfileWithImage(user: User, imageUri: Uri?) {
        viewModelScope.launch {
            val result = repository.setProfileWithImage(user, imageUri)
            result.onSuccess {
                _sessionState.value = SessionState.IS_PROFILE_SAVED
            }.onFailure { e ->
                _sessionState.value = SessionState.FAILED
                Log.e(TAG ,"Error------->$e")
            }
        }
    }

/*
    fun setProfile(user: User) {
        viewModelScope.launch {
            val isSuccess = repository.setProfile(user)
            _sessionState.value = if (isSuccess) SessionState.IS_PROFILE_SAVED else SessionState.FAILED
        }
    }
*/

    fun checkEmptyFieldInProfile(user: User, context: Context): Boolean {
        val emptyFields = mutableListOf<String>()
        with(user) {
            if (fullName.isEmpty()) emptyFields.add("Full Name")
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


