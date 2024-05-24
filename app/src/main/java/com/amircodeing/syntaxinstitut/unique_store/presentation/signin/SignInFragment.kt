package com.amircodeing.syntaxinstitut.unique_store.presentation.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.amircodeing.syntaxinstitut.unique_store.R
import com.amircodeing.syntaxinstitut.unique_store.databinding.FragmentSignInBinding
import com.amircodeing.syntaxinstitut.unique_store.utils.ChangeButtonNavVisibility
import com.google.firebase.database.FirebaseDatabase

class SignInFragment : Fragment(R.layout.fragment_sign_in) {
    private lateinit var binding: FragmentSignInBinding
    private val viewModel: SignInViewModel by viewModels()
    private lateinit var firebaseDatabase: FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        activity?.let { ChangeButtonNavVisibility.inVisibilityNavButton(it) }
        viewModel.setupEmailInputField(binding.root)
        viewModel.setupPasswordInputField(binding.root)
        firebaseDatabase = FirebaseDatabase.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val databaseReference = firebaseDatabase.reference.child("users")
        binding.signUpFFTV.setOnClickListener {
            val navController = Navigation.findNavController(binding.root)
            navController.navigate(R.id.signUpFragment)
        }

        binding.customButtonSignIn.setOnClickListener {
            val password = binding.signInPassword.getText().trim()
            val email = binding.signInemail.getText().trim()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.userNameAndPasswordValidation(email, password, databaseReference)
            } else {
                Toast.makeText(requireContext(), "Email or password is empty", Toast.LENGTH_LONG).show()
            }
        }

        viewModel.signInResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is SignInResult.Success -> {
                    Toast.makeText(context, "Sign In Successful", Toast.LENGTH_LONG).show()
                    val action = SignInFragmentDirections.actionSignInFragmentToHomeFragment()
                    findNavController().navigate(action)
                }
                is SignInResult.Failure -> {
                    Toast.makeText(context, "Sign In Failed", Toast.LENGTH_LONG).show()
                }
                is SignInResult.Error -> {
                    Toast.makeText(context, "Error: ${result.errorMessage}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }




}
