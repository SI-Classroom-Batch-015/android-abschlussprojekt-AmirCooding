package com.amircodeing.syntaxinstitut.unique_store.presentation.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.amircodeing.syntaxinstitut.unique_store.R
import com.amircodeing.syntaxinstitut.unique_store.data.model.Auth
import com.amircodeing.syntaxinstitut.unique_store.data.remote.firebaseService.SessionState
import com.amircodeing.syntaxinstitut.unique_store.databinding.FragmentSignUpBinding
import com.amircodeing.syntaxinstitut.unique_store.utils.inVisibilityNavButton

const val TAG = " SignUpFragment"

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {
    private lateinit var binding: FragmentSignUpBinding
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        viewModel.setViewOnAuthInput(binding.root)
        activity?.let { inVisibilityNavButton(it) }
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
                binding.signUpPassword.getText().trim()
            )
            binding.signUpProgressbar.visibility = View.VISIBLE
            binding.signUpProgressbar2.visibility = View.VISIBLE
            if (viewModel.checkEmptyFieldInAuth(auth, requireContext())) {
                viewModel.signUp(auth)
                viewModel.sessionState.observe(viewLifecycleOwner) { state ->
                    val message = when (state) {
                        SessionState.REGISTERED -> {
                            Navigation.findNavController(binding.root).navigate(R.id.signInFragment)
                            "profile has been successfully saved!"
                        }

                        SessionState.FAILED -> {
                            binding.signUpProgressbar.visibility = View.GONE
                            binding.signUpProgressbar2.visibility = View.GONE
                            "Your action failed!"
                        }

                        else -> throw NotImplementedError()
                    }
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            }

        }
        binding.signUpProgressbar.visibility = View.GONE
        binding.signUpProgressbar2.visibility = View.GONE

    }

}