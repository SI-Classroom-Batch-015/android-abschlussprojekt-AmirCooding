package com.amircodeing.syntaxinstitut.unique_store.presentation.signup

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.amircodeing.syntaxinstitut.unique_store.R
import com.amircodeing.syntaxinstitut.unique_store.databinding.FragmentSignUpBinding
import com.amircodeing.syntaxinstitut.unique_store.utils.ChangeButtonNavVisibility
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageRef: StorageReference
    private lateinit var binding: FragmentSignUpBinding
    private var uri: Uri? = null

    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("users")
        storageRef = FirebaseStorage.getInstance().getReference("Images")
        selectImageProfile()

        activity?.let { ChangeButtonNavVisibility.inVisibilityNavButton(it) }
        viewModel.callInputComponents(binding.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signInButtonInSignUp.setOnClickListener {
            val navController = Navigation.findNavController(binding.root)
            navController.navigate(R.id.signInFragment)
        }

        binding.customButtonSignup.setOnClickListener {
            uri?.let { imageUri ->
                val provideParameters =
                    ProvideParameters(databaseReference, storageRef, requireContext(), imageUri)
                viewModel.getProfile(binding, { user ->
                    if (viewModel.checkEmptyField(user, requireContext())) {
                        databaseReference.orderByChild("email").equalTo(user.email)
                            .addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    if (!snapshot.exists()) {
                                        databaseReference.child(user.id).setValue(user)
                                        Toast.makeText(
                                            requireContext(),
                                            "Signup Successful",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    } else {
                                        Toast.makeText(
                                            requireContext(),
                                            "User already exists",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                    return
                                }
                                override fun onCancelled(error: DatabaseError) {
                                    Toast.makeText(
                                        requireContext(),
                                        "Database Error: $error",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            })
                        viewModel.clearSignUpInputs(binding.root)
                        val navController = Navigation.findNavController(binding.root)
                        navController.navigate(R.id.signInFragment)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Please fill all fields",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }, provideParameters)
            } ?: Toast.makeText(requireContext(), "Please select an image", Toast.LENGTH_LONG)
                .show()
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
            if (uri != null) this.uri = uri
        }
        /**
         * Set an OnClickListener on the ImageView (addImageProfileIV) to launch the image picker.
         * Launch the image picker with a MIME type filter of "image", allowing the user to select any type of image.
         */
        binding.addImageProfileIV.setOnClickListener { pickImage.launch("image/*") }
    }
}
