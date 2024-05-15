package com.amircodeing.syntaxinstitut.unique_store.presentation.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.amircodeing.syntaxinstitut.unique_store.R
import com.amircodeing.syntaxinstitut.unique_store.databinding.FragmentHomeBinding
import com.amircodeing.syntaxinstitut.unique_store.utils.ChangeButtonNavVisibility

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel : HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
       viewModel.loadData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //navigateButton = view.findViewById(R.id.button_nav)
        viewModel.products.observe(viewLifecycleOwner){products ->
            binding.bestSellerRV.adapter = HomeAdapter(products)
        }
    }


    override fun onResume() {
        super.onResume()
        activity?.let { ChangeButtonNavVisibility.visibilityNavButton(it) }
    }

}