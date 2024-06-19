package com.amircodeing.syntaxinstitut.unique_store.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import coil.load
import com.amircodeing.syntaxinstitut.unique_store.R
import com.amircodeing.syntaxinstitut.unique_store.databinding.FragmentHomeBinding
import com.amircodeing.syntaxinstitut.unique_store.presentation.MainActivity
import com.amircodeing.syntaxinstitut.unique_store.utils.visibilityNavButton
import com.google.firebase.database.DatabaseReference

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        activity?.let {visibilityNavButton(it) }
        viewModel.loadCategory()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.homeProfile.apply {
            load(viewModel.user.value?.image)
        }
        binding.homeProfile.setOnClickListener {
            val navController = Navigation.findNavController(binding.root)
            navController.navigate(R.id.profileFragment)
        }

        viewModel.products.observe(viewLifecycleOwner) { product ->
            binding.bestSellerRV.adapter = ProductAdapter(
                product.filter { it.rating?.rate != null }
                    .sortedByDescending { it.rating?.rate ?: 0.0 }
                    .take(7),
                viewModel
            )
        }
        viewModel.category.observe(viewLifecycleOwner) { category ->
            binding.categoryItemsRV.adapter = CategoryAdapter(category, viewModel)
        }
        val helper: SnapHelper = PagerSnapHelper()
        helper.attachToRecyclerView(binding.categoryItemsRV)
        binding.categorySeeAllTV.setOnClickListener {
            val navController = Navigation.findNavController(binding.root)
            navController.navigate(R.id.listItemsFragment)
        }
        viewModel.showCountFavoritesLiveData.observe(viewLifecycleOwner) { numberProductInFavorites ->
            (activity as MainActivity).setFavoritesBadge(numberProductInFavorites)
        }

        (activity as MainActivity).setCartsBadgeFAB()

    }


    override fun onResume() {
        super.onResume()
        // activity?.let { ChangeButtonNavVisibility.visibilityNavButton(it) }
    }
}
