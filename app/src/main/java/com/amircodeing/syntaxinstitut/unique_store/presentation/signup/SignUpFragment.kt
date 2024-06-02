package com.amircodeing.syntaxinstitut.unique_store.presentation.signup

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.amircodeing.syntaxinstitut.unique_store.R
import com.amircodeing.syntaxinstitut.unique_store.data.model.Address
import com.amircodeing.syntaxinstitut.unique_store.data.model.User
import com.amircodeing.syntaxinstitut.unique_store.data.remote.firebaseService.FirebaseService
import com.amircodeing.syntaxinstitut.unique_store.data.remote.firebaseService.SessionState
import com.amircodeing.syntaxinstitut.unique_store.databinding.FragmentSignUpBinding
import com.amircodeing.syntaxinstitut.unique_store.utils.ChangeButtonNavVisibility
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
const val TAG =" SignUpFragment"
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {
    private lateinit var binding: FragmentSignUpBinding
    private val f = FirebaseService()
    private var imageUri: Uri? = null
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        selectImageProfile()
        activity?.let { ChangeButtonNavVisibility.inVisibilityNavButton(it) }
        viewModel.setViewOnProfileInput(binding.root)
        return binding.root
    }

    private fun selectImageProfile() {
        /**
         * Register an activity result to pick an image from the device's storage.
         * ActivityResultContracts.GetContent() allows us to get content (in this case, an image).
         */
        val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            // Set the selected image URI to the ImageView.
            binding.addImageProfileIV.setImageURI(uri)
            // If the URI is not null, assign it to the variable 'uri'.
            if (uri != null) this.imageUri = uri
        }
        /**
         * Set an OnClickListener on the ImageView (addImageProfileIV) to launch the image picker.
         * Launch the image picker with a MIME type filter of "image", allowing the user to select any type of image.
         */
        binding.addImageProfileIV.setOnClickListener { pickImage.launch("image/*") }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.customButtonSignup.setOnClickListener {
            Log.e(TAG ,"UID ---------------------------->"+ f.userId?.let { it1 -> Log.i(TAG , it1) }.toString())
            val user = User(
                fullName = binding.signUpFullName.getText().trim(),
                tel = binding.signUpTel.getText().trim(),
                address = Address(
                    street = binding.signUpStreetET.getText().trim(),
                    number = binding.signUpNumberET.getText().trim(),
                    zip = binding.signUpZipET.getText().trim(),
                    city = binding.signUpCityET.getText().trim(),
                    country = binding.signUpCountryET.getText().trim()
                )
            )
            if(viewModel.checkEmptyFieldInProfile(user,requireContext())){
                val isRegisteringUser = viewModel.userProfile.value != null


                if (isRegisteringUser) {
                    Navigation.findNavController(binding.root).navigate(R.id.signInFragment)
                } else {

                }

            }
            viewModel.sessionState.observe(viewLifecycleOwner) { state ->
                val message = when (state) {
                    SessionState.REGISTERED -> {
                        "profile has been successfully saved!"
                    }
                    SessionState.FAILED -> "Your action failed!"
                    else -> throw NotImplementedError()
                }
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }



    }

}