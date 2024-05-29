package com.amircodeing.syntaxinstitut.unique_store.presentation.signup

import android.content.Context
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.amircodeing.syntaxinstitut.unique_store.R
import com.amircodeing.syntaxinstitut.unique_store.data.model.Address
import com.amircodeing.syntaxinstitut.unique_store.data.model.User
import com.amircodeing.syntaxinstitut.unique_store.databinding.FragmentSignUpBinding
import com.amircodeing.syntaxinstitut.unique_store.utils.CustomInputField
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference

class SignUpViewModel : ViewModel() {

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
    /**
     * Set labels and hints for each input
     */
    fun callInputComponents(signInView: View) {
      setupFullNameInputField(signInView)
        setupUserNameInputField(signInView)
        setupPasswordInputField(signInView)
     setupEmailSignUpField(signInView)
        setupTelNumber(signInView)
       setupCityInputField(signInView)
      setupZipInputField(signInView)
      setupStreetInputField(signInView)
       setupCountryInputField(signInView)
      setupNumberInputField(signInView)
    }





    fun getProfile(
        binding: FragmentSignUpBinding,
        callback: (User) -> Unit,
        provideParameters: ProvideParameters
    ) {
        val contactId = provideParameters.databaseReference.push().key!!
        val fullName = binding.signUpFullName.getText()
        val userName = binding.signUpUserName.getText().trim()
        val email = binding.signUpEmail.getText().trim()
        val password = binding.signUpPassword.getText().trim()
        val street = binding.signUpStreetET.getText().trim()
        val number = binding.signUpNumberET.getText().trim()
        val zip = binding.signUpZipET.getText().trim()
        val city = binding.signUpCityET.getText().trim()
        val tel = binding.signUpTel.getText()
        val country = binding.signUpCountryET.getText().trim()
        val address = Address(street = street, number = number, zip = zip, city = city, country = country)
        /**
         *  Stored image Profile in Firebase Storage
         *  @see provideParameters  Define a data class named ProvideParameters
         *  that encapsulates parameters needed for certain operations.
         */
        provideParameters.uri.let {
            provideParameters.storageRef.child(contactId).putFile(it).addOnSuccessListener { task ->
                task.metadata?.reference?.downloadUrl?.addOnSuccessListener { url ->
                    Toast.makeText(provideParameters.context, "Image stored successfully", Toast.LENGTH_LONG).show()
                    val user = User(
                        id = contactId,
                        fullName = fullName,
                        userName = userName,
                        email = email,
                        tel = tel,
                        password = password,
                        address = address,
                        image = url.toString()
                    )
                    callback(user)
                }?.addOnFailureListener {
                    Toast.makeText(provideParameters.context, "Failed to get download URL", Toast.LENGTH_LONG).show()
                }
            }.addOnFailureListener {
                Toast.makeText(provideParameters.context, "Failed to upload file", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun checkEmptyField(user: User, context: Context): Boolean {
        val emptyFields = mutableListOf<String>()
        if (user.fullName.isEmpty()) emptyFields.add("Full Name")
        if (user.userName.isEmpty()) emptyFields.add("Username")
        if (user.email.isEmpty()) emptyFields.add("email")
        val email = user.email
        if (email.isEmpty()) {
            emptyFields.add("email (must be contain @)")
        }
        if (user.tel.isEmpty()) emptyFields.add("telephone")
        val password = user.password
        if (password.isEmpty() || password.length < 8) {
            emptyFields.add("password (must be at least 8 characters long and contain at least one special character")
        }
        if (user.address!!.street.isEmpty()) emptyFields.add("street")
        if (user.address.number.isEmpty()) emptyFields.add("number")
        if (user.address.zip.isEmpty()) emptyFields.add("zip")
        if (user.address.city.isEmpty()) emptyFields.add("city")
        if (user.address.country.isEmpty()) emptyFields.add("country")
        if (emptyFields.isNotEmpty()) {
            val message = "The following fields are empty: ${emptyFields.joinToString(", ")}"
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    fun clearSignUpInputs(signInView: View) {
        signInView.findViewById<CustomInputField>(R.id.signUp_fullName).clearInputs()
        // Clear the image view
        val signUpImageIV = signInView.findViewById<ImageView>(R.id.add_image_profile_IV)
        signUpImageIV.setImageDrawable(null) // Clear the image
        signUpImageIV.setBackgroundResource(0) // Clear background if necessary

        signInView.findViewById<CustomInputField>(R.id.signUpTel).clearInputs()
        signInView.findViewById<CustomInputField>(R.id.signUpEmail).clearInputs()
        signInView.findViewById<CustomInputField>(R.id.signUp_street_ET).clearInputs()
        signInView.findViewById<CustomInputField>(R.id.signUp_number_ET).clearInputs()
        signInView.findViewById<CustomInputField>(R.id.signUp_zip_ET).clearInputs()
        signInView.findViewById<CustomInputField>(R.id.signUp_city_ET).clearInputs()
        signInView.findViewById<CustomInputField>(R.id.signUp_country_ET).clearInputs()
    }
}
data class ProvideParameters(
    val databaseReference: DatabaseReference,
    val storageRef: StorageReference,
    val context: Context,
    val uri: Uri
)
