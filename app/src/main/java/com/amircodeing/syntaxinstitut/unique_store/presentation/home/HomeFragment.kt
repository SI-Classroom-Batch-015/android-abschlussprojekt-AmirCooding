package com.amircodeing.syntaxinstitut.unique_store.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.amircodeing.syntaxinstitut.unique_store.databinding.FragmentHomeBinding
import com.amircodeing.syntaxinstitut.unique_store.utils.ChangeButtonNavVisibility

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel : HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //navigateButton = view.findViewById(R.id.button_nav)
        viewModel.products.observe(viewLifecycleOwner){product ->
            binding.bestSellerRV.adapter = ProductAdapter(product,viewModel)
        }
        viewModel.category.observe(viewLifecycleOwner){
            category -> binding.categoryItemsRV.adapter = CategoryAdapter(category)
        }
         val helper : SnapHelper = PagerSnapHelper()
        helper.attachToRecyclerView(binding.categoryItemsRV)
    }


    override fun onResume() {
        super.onResume()
        activity?.let { ChangeButtonNavVisibility.visibilityNavButton(it) }
    }

}