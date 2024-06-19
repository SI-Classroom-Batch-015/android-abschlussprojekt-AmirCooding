package com.amircodeing.syntaxinstitut.unique_store.presentation.recoverypassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.amircodeing.syntaxinstitut.unique_store.R
import com.amircodeing.syntaxinstitut.unique_store.databinding.FragmentRecoveryPasswordBinding
import com.amircodeing.syntaxinstitut.unique_store.utils.ToolbarComponents
import com.amircodeing.syntaxinstitut.unique_store.utils.inVisibilityNavButton
import com.amircodeing.syntaxinstitut.unique_store.utils.setToolbar
import com.google.firebase.auth.FirebaseAuth

const val TAG = "RecoveryPasswordFragment"
class RecoveryPasswordFragment : Fragment() {
    private lateinit var binding: FragmentRecoveryPasswordBinding
    private lateinit var firebaseAuth: FirebaseAuth
    val viewModel: RecoveryPasswordViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecoveryPasswordBinding.inflate(inflater, container, false)
       firebaseAuth = FirebaseAuth.getInstance()
       setToolbar(
            ToolbarComponents(
                view = binding.root,
                screensTitle = "New Password",
                iconsVisibility = false,
                navigateUp = false, rootPath = R.id.toolbar_recovery_password,
                iconPath = null
            )
        )
        viewModel.setupEmailInputField(binding.root)
        activity?.let { inVisibilityNavButton(it) }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.sendEmail.setOnClickListener {
       val email = binding.recoveryPasswordEmailTV.getText().trim()
            if(email.isNotEmpty()){
            firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Navigation.findNavController(binding.root).navigate(R.id.signInFragment)
                        Toast.makeText(requireContext(), "Check your Email please", Toast.LENGTH_SHORT).show()

                    } else {
                        val errorMessage = task.exception?.message
                        Toast.makeText(requireContext(), "Failed to send password reset email: $errorMessage", Toast.LENGTH_SHORT).show()
                    }
                }

            }else{
                Toast.makeText(requireContext(), "Please enter the correct email", Toast.LENGTH_SHORT).show()
            }
        }


    }
}


