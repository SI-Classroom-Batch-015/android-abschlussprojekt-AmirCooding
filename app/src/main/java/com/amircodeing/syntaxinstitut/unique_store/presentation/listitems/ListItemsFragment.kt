package com.amircodeing.syntaxinstitut.unique_store.presentation.listitems

import android.content.res.ColorStateList
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.amircodeing.syntaxinstitut.unique_store.R
import com.amircodeing.syntaxinstitut.unique_store.databinding.FragmentListItemsBinding
import com.amircodeing.syntaxinstitut.unique_store.presentation.home.CategoryAdapter
import com.amircodeing.syntaxinstitut.unique_store.presentation.home.HomeViewModel
import com.amircodeing.syntaxinstitut.unique_store.utils.ChangeButtonNavVisibility
import com.amircodeing.syntaxinstitut.unique_store.utils.CustomToolbar
import com.amircodeing.syntaxinstitut.unique_store.utils.ToolbarComponents

class ListItemsFragment : Fragment(R.layout.fragment_list_items) {
    private lateinit var binding: FragmentListItemsBinding
    private val viewModel: ListItemsViewModel by viewModels()
    private val homeViewModel : HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListItemsBinding.inflate(inflater, container, false)
        CustomToolbar.setToolbar(
            ToolbarComponents(
                view = binding.root,
                title = "Category",
                visibility = false,
                backButtonVisibility =true,
                path = R.id.toolbar_list,
                icon = null
            )
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            viewModel.categoryMen.observe(viewLifecycleOwner){
                binding.recyclerView.adapter = ListCategoryAdapter(it , homeViewModel)
            }
            categoryWomanTV.setOnClickListener {
                updateBackgroundTint(categoryWomanTV)
                viewModel.categoryWomen.observe(viewLifecycleOwner){
                    binding.recyclerView.adapter = ListCategoryAdapter(it , homeViewModel)
                }

            }

            categoryJeweleryTV.setOnClickListener {
                updateBackgroundTint(categoryJeweleryTV)
                viewModel.categoryJewelery.observe(viewLifecycleOwner){
                    binding.recyclerView.adapter = ListCategoryAdapter(it , homeViewModel)
                }
            }

            categoryElectronicTV.setOnClickListener {
                updateBackgroundTint(categoryElectronicTV)
             viewModel.categoryE.observe(viewLifecycleOwner){
                 binding.recyclerView.adapter = ListCategoryAdapter(it , homeViewModel)
             }
            }

            categoryMenTV.setOnClickListener {
                updateBackgroundTint(categoryMenTV)
                viewModel.categoryMen.observe(viewLifecycleOwner){
                    binding.recyclerView.adapter = ListCategoryAdapter(it , homeViewModel)
                }
            }


        }

    }
    override fun onResume() {
        super.onResume()
        activity?.let { ChangeButtonNavVisibility.visibilityNavButton(it) }
    }
    private fun updateBackgroundTint(selectedTextView: TextView) {
        with(binding) {
            categoryWomanTV.backgroundTintList = null
            categoryMenTV.backgroundTintList = null
            categoryElectronicTV.backgroundTintList = null
            categoryJeweleryTV.backgroundTintList = null
            selectedTextView.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.primaryDark
                )
            )
        }
    }

}