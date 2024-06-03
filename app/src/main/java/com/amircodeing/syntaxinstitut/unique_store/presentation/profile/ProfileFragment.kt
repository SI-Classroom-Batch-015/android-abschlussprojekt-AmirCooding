package com.amircodeing.syntaxinstitut.unique_store.presentation.profile

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.amircodeing.syntaxinstitut.unique_store.R
import com.amircodeing.syntaxinstitut.unique_store.data.model.Address
import com.amircodeing.syntaxinstitut.unique_store.data.model.User
import com.amircodeing.syntaxinstitut.unique_store.data.remote.firebaseService.SessionState
import com.amircodeing.syntaxinstitut.unique_store.databinding.FragmentProfileBinding
import com.amircodeing.syntaxinstitut.unique_store.utils.ChangeButtonNavVisibility

class ProfileFragment : Fragment() {
    private lateinit var binding : FragmentProfileBinding
    private var imageUri: Uri? = null
    private val viewModel: ProfileViewModel  by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =FragmentProfileBinding.inflate(inflater, container, false)
        viewModel.setViewOnProfileInput(binding.root)
        selectImageProfile()
        activity?.let { ChangeButtonNavVisibility.inVisibilityNavButton(it) }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.customButtonSkipped.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.homeFragment)
        }

        viewModel.sessionState.observe(viewLifecycleOwner) { state ->
            val message = when (state) {
                SessionState.IS_PROFILE_SAVED -> {
                    Navigation.findNavController(binding.root).navigate(R.id.homeFragment)
                    "save  been profile successfully"
                }
                SessionState.FAILED -> "Your action failed!"
                else -> throw NotImplementedError()
            }
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        binding.saveProfile.setOnClickListener {
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

            if (viewModel.checkEmptyFieldInProfile(user, requireContext())) {
                viewModel.setProfileWithImage(user, imageUri)
            }
           // viewModel.setProfile(user)
        }
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

}