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
import com.amircodeing.syntaxinstitut.unique_store.R
import com.amircodeing.syntaxinstitut.unique_store.data.model.Address
import com.amircodeing.syntaxinstitut.unique_store.data.model.User
import com.amircodeing.syntaxinstitut.unique_store.data.remote.firebaseService.SessionState
import com.amircodeing.syntaxinstitut.unique_store.databinding.FragmentProfileBinding
import com.amircodeing.syntaxinstitut.unique_store.utils.inVisibilityNavButton

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private var imageUri: Uri? = null
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(inflater, container, false)
        viewModel.getProfile()
        viewModel.setViewOnProfileInput(binding.root)
        selectImageProfile()
        activity?.let { inVisibilityNavButton(it) }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            Toast.makeText(context, "one create view", Toast.LENGTH_LONG).show()
         viewModel.user.observe(viewLifecycleOwner){user ->
        if (user != null) {
            viewModel.setProfileDataToInputs(binding.root , user)
        } else {
            Toast.makeText(context,"there is nothing to show", Toast.LENGTH_LONG).show()
        }
         }
           /*  viewModel.setProfileDataToInputs(binding.root, user) */


        binding.customButtonSkipped.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.homeFragment)
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
                binding.profileProgressbar.visibility = View.VISIBLE
                binding.profileProgressbar2.visibility = View.VISIBLE
            }

            viewModel.sessionState.observe(viewLifecycleOwner) { state ->

                val message = when (state) {
                    SessionState.IS_PROFILE_SAVED -> {
                        Navigation.findNavController(binding.root).navigate(R.id.homeFragment)
                        binding.profileProgressbar.visibility = View.GONE
                        binding.profileProgressbar2.visibility = View.GONE
                        "save  been profile successfully"
                    }

                    SessionState.FAILED -> "Your action failed!"
                    else -> throw NotImplementedError()
                }
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
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