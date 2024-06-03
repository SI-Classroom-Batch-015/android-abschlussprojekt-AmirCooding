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
import com.amircodeing.syntaxinstitut.unique_store.data.model.Auth
import com.amircodeing.syntaxinstitut.unique_store.data.model.User
import com.amircodeing.syntaxinstitut.unique_store.data.model.hashPassword
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

        activity?.let { ChangeButtonNavVisibility.inVisibilityNavButton(it) }
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signInButtonInSignUp.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.signInFragment)
        }
        binding.customButtonCreateAccount.setOnClickListener {
            val auth = Auth(
                binding.signUpUserName.getText().trim(),
                hashPassword(binding.signUpPassword.getText().trim())
            )
            if (viewModel.checkEmptyFieldInAuth(auth, requireContext())) {
                viewModel.signUp(auth)
                viewModel.sessionState.observe(viewLifecycleOwner) { state ->
                    val message = when (state) {
                        SessionState.REGISTERED -> {
                            Navigation.findNavController(binding.root).navigate(R.id.signInFragment)
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

}