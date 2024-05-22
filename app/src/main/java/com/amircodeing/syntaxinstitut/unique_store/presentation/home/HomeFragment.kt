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
import com.amircodeing.syntaxinstitut.unique_store.R
import com.amircodeing.syntaxinstitut.unique_store.databinding.FragmentHomeBinding
import com.amircodeing.syntaxinstitut.unique_store.presentation.listitems.ListItemsFragment
import com.amircodeing.syntaxinstitut.unique_store.utils.ChangeButtonNavVisibility

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by activityViewModels()
        val listFragment =  ListItemsFragment()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel.loadCategory()
       // viewModel.loadProducts()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //navigateButton = view.findViewById(R.id.button_nav)
        viewModel.products.observe(viewLifecycleOwner) { product ->
            binding.bestSellerRV.adapter = ProductAdapter(
               // product,
                 product.filter { it.rating?.rate != null }
                .sortedByDescending { it.rating?.rate ?: 0.0 }
                .take(7),
                viewModel
            )
        }
        viewModel.category.observe(viewLifecycleOwner) { category ->
            binding.categoryItemsRV.adapter = CategoryAdapter(category , viewModel)
        }
        val helper: SnapHelper = PagerSnapHelper()
        helper.attachToRecyclerView(binding.categoryItemsRV)
        binding.categorySeeAllTV.setOnClickListener {
                val navController = Navigation.findNavController(binding.root)
               navController.navigate(R.id.listItemsFragment)
        }
//TODO add photo to home profile
     //  Picasso.get().load()

    }


    override fun onResume() {
        super.onResume()
        activity?.let { ChangeButtonNavVisibility.visibilityNavButton(it) }
    }

}