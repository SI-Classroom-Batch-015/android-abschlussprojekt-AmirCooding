package com.amircodeing.syntaxinstitut.unique_store.presentation.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import coil.load
import com.amircodeing.syntaxinstitut.unique_store.R
import com.amircodeing.syntaxinstitut.unique_store.databinding.FragmentSettingBinding
import com.amircodeing.syntaxinstitut.unique_store.utils.Constants
import com.amircodeing.syntaxinstitut.unique_store.utils.CustomToolbar
import com.amircodeing.syntaxinstitut.unique_store.utils.ToolbarComponents
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
const val  TAG = "SettingFragment"
class SettingFragment : Fragment(R.layout.fragment_setting) {
    private lateinit var binding: FragmentSettingBinding
    private val viewModle  : SettingViewModel by viewModels()
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logOutB.setOnClickListener {
            viewModle.signOut()
            Toast.makeText(context, "Sign Out Successful", Toast.LENGTH_LONG).show()
            Navigation.findNavController(binding.root).navigate(R.id.signInFragment)

        }
        val user = Constants.currentUser
        if (user != null) {
            with(binding) {
                addImageProfileIV.load(user.image)
                fullName.text = user.fullName
                telTV.text = user.tel
                streetTV.text = user.address?.street
                numberTV.text = user.address?.number
                zipTV.text = user.address?.zip
                cityTV.text = user.address?.city
                countryTV.text = user.address?.country
            }
        }

    }
}