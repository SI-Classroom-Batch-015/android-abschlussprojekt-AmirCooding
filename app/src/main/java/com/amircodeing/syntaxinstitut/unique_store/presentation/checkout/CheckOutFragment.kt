package com.amircodeing.syntaxinstitut.unique_store.presentation.checkout

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.amircodeing.syntaxinstitut.unique_store.R
import com.amircodeing.syntaxinstitut.unique_store.databinding.FragmentCheckOutBinding
import com.amircodeing.syntaxinstitut.unique_store.utils.ChangeButtonNavVisibility
import com.amircodeing.syntaxinstitut.unique_store.utils.CustomToolbar
import com.amircodeing.syntaxinstitut.unique_store.utils.ToolbarComponents

class CheckOutFragment : Fragment() {
    private lateinit var binding: FragmentCheckOutBinding
    private val viewModel: CheckOutViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  FragmentCheckOutBinding.inflate(inflater, container, false)

        CustomToolbar.setToolbar(
            ToolbarComponents(
                view = binding.root,
                title = "Check Out",
                visibility = false,
                backButtonVisibility = false, path = R.id.toolbar_checkOut,
                icon = null
            )
        )
        activity?.let { ChangeButtonNavVisibility.inVisibilityNavButton(it) }
        return  binding.root
    }

}