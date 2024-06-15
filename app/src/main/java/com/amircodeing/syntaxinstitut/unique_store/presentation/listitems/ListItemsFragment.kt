package com.amircodeing.syntaxinstitut.unique_store.presentation.listitems

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.amircodeing.syntaxinstitut.unique_store.R
import com.amircodeing.syntaxinstitut.unique_store.data.model.Product
import com.amircodeing.syntaxinstitut.unique_store.databinding.FragmentListItemsBinding
import com.amircodeing.syntaxinstitut.unique_store.presentation.home.HomeViewModel
import com.amircodeing.syntaxinstitut.unique_store.utils.BottomNavController
import com.amircodeing.syntaxinstitut.unique_store.utils.ToolbarComponents
import com.amircodeing.syntaxinstitut.unique_store.utils.setToolbar

class ListItemsFragment : Fragment(R.layout.fragment_list_items) {
    private lateinit var binding: FragmentListItemsBinding
    private val viewModel: ListItemsViewModel by viewModels()
    private val homeViewModel: HomeViewModel by activityViewModels()
    private lateinit var adapter: ListCategoryAdapter
    private var currentObserver: Observer<List<Product>>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListItemsBinding.inflate(inflater, container, false)
        setToolbar(
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

        setupCategoryClickListeners()
        observeCategories()
    }

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
     * the previous observer is removed before adding a new observer for the selected category.
     */
    private fun observeCategories() {
        viewModel.selectedCategory.observe(viewLifecycleOwner) { category ->
            currentObserver?.let {
                viewModel.categoryWomen.removeObserver(it)
                viewModel.categoryJewelery.removeObserver(it)
                viewModel.categoryE.removeObserver(it)
                viewModel.categoryMen.removeObserver(it)
            }
            /**
             * is used to track the current observer so that it can
             * be removed when switching categories.
             */
            currentObserver = Observer { items ->
                adapter.submitList(items)
            }
            when (category) {
                "women" -> viewModel.categoryWomen.observe(viewLifecycleOwner, currentObserver!!)
                "jewelery" -> viewModel.categoryJewelery.observe(viewLifecycleOwner, currentObserver!!)
                "electronics" -> viewModel.categoryE.observe(viewLifecycleOwner, currentObserver!!)
                "men" -> viewModel.categoryMen.observe(viewLifecycleOwner, currentObserver!!)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.selectedCategory.value?.let { category ->
            updateUIForCategory(category)
        }
        activity?.let { BottomNavController.visibilityNavButton(it) }
    }
/**
 * updates the UI to reflect the selected category by highlighting the respective TextView.
 * @param category selected Category
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
     *updates the background tint and text color of the selected category
     * TextView while resetting the others.
     */
    private fun updateBackgroundTint(selectedTextView: TextView) {
        with(binding) {
            listOf(categoryWomanTV, categoryMenTV, categoryElectronicTV, categoryJeweleryTV).forEach {
                it.backgroundTintList = null
                it.setTextColor(ContextCompat.getColor(requireContext(), R.color.primaryDark))
            }

            selectedTextView.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.primaryDark
                )
            )

            selectedTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.divider))
        }
    }
}
