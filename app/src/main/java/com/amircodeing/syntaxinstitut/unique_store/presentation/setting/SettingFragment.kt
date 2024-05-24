package com.amircodeing.syntaxinstitut.unique_store.presentation.setting

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import coil.load
import com.amircodeing.syntaxinstitut.unique_store.R
import com.amircodeing.syntaxinstitut.unique_store.databinding.FragmentSettingBinding
import com.amircodeing.syntaxinstitut.unique_store.presentation.MainActivity
import com.amircodeing.syntaxinstitut.unique_store.presentation.signin.SignInFragmentDirections
import com.amircodeing.syntaxinstitut.unique_store.utils.Constants
import com.amircodeing.syntaxinstitut.unique_store.utils.CustomToolbar
import com.amircodeing.syntaxinstitut.unique_store.utils.ToolbarComponents
import com.google.firebase.auth.FirebaseAuth

class SettingFragment : Fragment(R.layout.fragment_setting) {

    private lateinit var binding: FragmentSettingBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        CustomToolbar.setToolbar(
            ToolbarComponents(
                view = binding.root, title = "Setting", visibility = false,
                backButtonVisibility = true,
                path = R.id.toolbar_setting,
                icon = null
            )
        )
        mAuth = FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = Constants.currentUser
        if (user != null) {
            with(binding) {
                addImageProfileIV.load(user.image)
                fullName.text = user.fullName
                emailTV.text = user.email
                telTV.text = user.tel
                streetTV.text = user.address?.street
                numberTV.text = user.address?.number
                zipTV.text = user.address?.zip
                cityTV.text = user.address?.city
                countryTV.text = user.address?.country
                logOutB.setOnClickListener {
                    mAuth.signOut()
                    Toast.makeText(context, "Sign Out Successful", Toast.LENGTH_LONG).show()
                    val action = SettingFragmentDirections.actionSettingFragmentToSignInFragment()
                    findNavController().navigate(action)
                }
            }
        }

    }
}