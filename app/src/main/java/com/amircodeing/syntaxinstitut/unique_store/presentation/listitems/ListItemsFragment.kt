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
import androidx.lifecycle.Observer
import com.amircodeing.syntaxinstitut.unique_store.R
import com.amircodeing.syntaxinstitut.unique_store.data.model.Product
import com.amircodeing.syntaxinstitut.unique_store.databinding.FragmentListItemsBinding
import com.amircodeing.syntaxinstitut.unique_store.presentation.home.CategoryAdapter
import com.amircodeing.syntaxinstitut.unique_store.presentation.home.HomeViewModel
import com.amircodeing.syntaxinstitut.unique_store.utils.ChangeButtonNavVisibility
import com.amircodeing.syntaxinstitut.unique_store.utils.CustomToolbar
import com.amircodeing.syntaxinstitut.unique_store.utils.ToolbarComponents

class ListItemsFragment : Fragment(R.layout.fragment_list_items) {
    private lateinit var binding: FragmentListItemsBinding
    private val viewModel: ListItemsViewModel by viewModels()
    private val homeViewModel: HomeViewModel by activityViewModels()
    private lateinit var adapter: ListCategoryAdapter
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
                backButtonVisibility = true,
                path = R.id.toolbar_list,
                icon = null
            )
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ListCategoryAdapter(homeViewModel)
        binding.listRV.adapter = adapter
       viewModel.categoryMen.observe(viewLifecycleOwner){
           adapter.submitList(it)
       }
        setupCategoryClickListeners()
        observeCategories()
    }

    /**
     *      Set up click listener for the Electronics category TextView
     *      Update the background tint of the selected TextView to indicate it's selected
     *      Update the ViewModel with the selected category "electronics"
     */
    private fun setupCategoryClickListeners() {
        with(binding) {
            categoryWomanTV.setOnClickListener {
                updateBackgroundTint(categoryWomanTV)
                viewModel.setSelectedCategory("women")
            }

            categoryJeweleryTV.setOnClickListener {
                updateBackgroundTint(categoryJeweleryTV)
                viewModel.setSelectedCategory("jewelery")
            }

            categoryElectronicTV.setOnClickListener {
                updateBackgroundTint(categoryElectronicTV)
                viewModel.setSelectedCategory("electronics")
            }

            categoryMenTV.setOnClickListener {
                updateBackgroundTint(categoryMenTV)
                viewModel.setSelectedCategory("men")
            }
        }
    }

    /**
     * Observe changes to the selected category in the ViewModel
     *  When the selected category changes, perform the corresponding action
     *         }
     */
    private fun observeCategories() {
        viewModel.selectedCategory.observe(viewLifecycleOwner) { category ->
            when (category) {
                "women" -> viewModel.categoryWomen.observe(viewLifecycleOwner, categoryObserver)
                "jewelery" -> viewModel.categoryJewelery.observe(
                    viewLifecycleOwner,
                    categoryObserver
                )

                "electronics" -> viewModel.categoryE.observe(viewLifecycleOwner, categoryObserver)
                "men" -> viewModel.categoryMen.observe(viewLifecycleOwner, categoryObserver)
            }
        }
    }

    private val categoryObserver = Observer<List<Product>> { items ->
        adapter.submitList(items)
    }

    override fun onResume() {
        super.onResume()
        viewModel.selectedCategory.value?.let { category ->
            updateUIForCategory(category)
        }
        activity?.let { ChangeButtonNavVisibility.visibilityNavButton(it) }
    }
    /**
     * Updates the UI to reflect the selected category by highlighting the respective TextView.
     * It uses the updateBackgroundTint method to change the tint of the selected category TextView.
     * @param category the category identifier as a String ("women", "jewelery", "electronics", "men").
     */
    private fun updateUIForCategory(category: String) {
        when (category) {
            "women" -> updateBackgroundTint(binding.categoryWomanTV)
            "jewelery" -> updateBackgroundTint(binding.categoryJeweleryTV)
            "electronics" -> updateBackgroundTint(binding.categoryElectronicTV)
            "men" -> updateBackgroundTint(binding.categoryMenTV)
        }
    }
/**
 * Reset background tint for all categories
 * Set the background tint for the selected category
 * Set the text color for the selected category
 */
    private fun updateBackgroundTint(selectedTextView: TextView) {
        with(binding) {
            // Reset background tint for all categories
            listOf(categoryWomanTV, categoryMenTV, categoryElectronicTV, categoryJeweleryTV).forEach {
                it.backgroundTintList = null
                it.setTextColor(ContextCompat.getColor(requireContext(), R.color.primaryDark))
            }

            // Set the background tint for the selected category
            selectedTextView.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.primaryDark
                )
            )

            // Set the text color for the selected category
            selectedTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.divider))
        }
    }

}


