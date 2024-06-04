package com.amircodeing.syntaxinstitut.unique_store.presentation.home


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import coil.load
import com.amircodeing.syntaxinstitut.unique_store.R
import com.amircodeing.syntaxinstitut.unique_store.data.remote.firebaseService.FirebaseService
import com.amircodeing.syntaxinstitut.unique_store.data.remote.firebaseService.FirestoreService
import com.amircodeing.syntaxinstitut.unique_store.databinding.FragmentHomeBinding
import com.amircodeing.syntaxinstitut.unique_store.utils.ChangeButtonNavVisibility
import com.amircodeing.syntaxinstitut.unique_store.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var databaseReference : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        //TODO return null
        viewModel.loadCategory()
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callImageProfile()
        binding.homeProfile.load(viewModel.userProfile.value?.image)
        binding.homeProfile.setOnClickListener {
            val navController = Navigation.findNavController(binding.root)
            navController.navigate(R.id.profileFragment)
        }

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



    }

    private fun callImageProfile() {
        databaseReference = FirebaseDatabase.getInstance().reference.child("users")
        Constants.currentUser?.let {
            databaseReference.child(it.id).get().addOnSuccessListener {
                if (it.exists()) {
                    val image = it.child("image").value
                    Toast.makeText(requireContext(), "Result Found", Toast.LENGTH_LONG).show()
                    binding.homeProfile.load(image)
                } else {
                    Toast.makeText(requireContext(), "Result Not Found", Toast.LENGTH_LONG).show()
                }
            }.addOnFailureListener {
                Toast.makeText(requireContext(), "Somthing", Toast.LENGTH_LONG).show()
            }
        }
    }


    override fun onResume() {
        super.onResume()
        activity?.let { ChangeButtonNavVisibility.visibilityNavButton(it) }
    }

}