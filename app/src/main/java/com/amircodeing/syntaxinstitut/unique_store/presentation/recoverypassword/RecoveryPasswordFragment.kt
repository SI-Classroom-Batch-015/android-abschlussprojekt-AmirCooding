package com.amircodeing.syntaxinstitut.unique_store.presentation.recoverypassword

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.amircodeing.syntaxinstitut.unique_store.R
import com.amircodeing.syntaxinstitut.unique_store.data.remote.CrudFDataBase
import com.amircodeing.syntaxinstitut.unique_store.databinding.FragmentRecoveryPasswordBinding
import com.amircodeing.syntaxinstitut.unique_store.utils.ChangeButtonNavVisibility
import com.amircodeing.syntaxinstitut.unique_store.utils.Constants
import com.amircodeing.syntaxinstitut.unique_store.utils.CustomToolbar
import com.amircodeing.syntaxinstitut.unique_store.utils.ToolbarComponents
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RecoveryPasswordFragment : Fragment() {
    private lateinit var binding: FragmentRecoveryPasswordBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase
    val viewModel: RecoveryPasswordViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecoveryPasswordBinding.inflate(inflater, container, false)
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("users")
        CustomToolbar.setToolbar(
            ToolbarComponents(
                view = binding.root,
                title = "New Password",
                visibility = false,
                backButtonVisibility = false, path = R.id.toolbar_recovery_password,
                icon = null
            )
        )
        viewModel.setupEmailInputField(binding.root)
        viewModel.setupNewPasswordInputField(binding.root)
        viewModel.setupRepeatNewPasswordInputField(binding.root)
        activity?.let { ChangeButtonNavVisibility.inVisibilityNavButton(it) }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.customButtonReplacePassword.setOnClickListener {
            val email = binding.recoveryPasswordEmailTV.getText().trim()
            val password = binding.recoveryPasswordNewPasswordTV.getText().trim()
            val repeatPassword = binding.recoveryPasswordRepeatTV.getText().trim()

            if (email.isNotEmpty() && password.isNotEmpty() && repeatPassword.isNotEmpty()) {
                viewModel.updatePassword(
                    email,
                    password,
                    repeatPassword,
                    databaseReference,
                    requireContext(),
                    binding.root
                )
            } else {
                Toast.makeText(
                    requireContext(),
                    "Email, Password or Repeat Password is empty",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}


