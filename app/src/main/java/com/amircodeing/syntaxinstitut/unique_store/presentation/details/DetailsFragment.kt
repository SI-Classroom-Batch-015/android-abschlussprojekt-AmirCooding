package com.amircodeing.syntaxinstitut.unique_store.presentation.details

import android.graphics.Paint
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import coil.load
import com.amircodeing.syntaxinstitut.unique_store.R
import com.amircodeing.syntaxinstitut.unique_store.data.model.Address
import com.amircodeing.syntaxinstitut.unique_store.data.model.Product
import com.amircodeing.syntaxinstitut.unique_store.data.model.User
import com.amircodeing.syntaxinstitut.unique_store.databinding.FragmentDetailsBinding
import com.amircodeing.syntaxinstitut.unique_store.databinding.FragmentSignUpBinding
import com.amircodeing.syntaxinstitut.unique_store.presentation.favorite.FavoriteViewModel
import com.amircodeing.syntaxinstitut.unique_store.presentation.home.HomeViewModel
import com.amircodeing.syntaxinstitut.unique_store.utils.ChangeButtonNavVisibility
import com.amircodeing.syntaxinstitut.unique_store.utils.CustomToolbar.Companion.setToolbar
import com.amircodeing.syntaxinstitut.unique_store.utils.ToolbarComponents
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class DetailsFragment : Fragment(R.layout.fragment_details) {
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageRef: StorageReference
    private lateinit var binding: FragmentDetailsBinding
    private var uri: Uri? = null
    private val viewModel: HomeViewModel by activityViewModels()
    private val viewModelFavorite: FavoriteViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbar(
            ToolbarComponents(
                view = binding.root,
                title = "Details",
                visibility = false,
                backButtonVisibility = false, path = R.id.toolbar_details,
                icon = null
            )
        )
        activity?.let { ChangeButtonNavVisibility.inVisibilityNavButton(it) }
        val product = viewModel.getProduct().value
        with(binding) {
            if (product != null) {
                detailTitleTV.text = product.title.toString()
                imageDetailsIV.load(product.image)
                ratingBar.rating = product.rating?.rate!!.toFloat()
                ratingText.text = "(${product.rating?.count})"
                detailDescriptionTV.text = product.description
                detailPriceTV.text = String.format("%.2f €", product.price)
                val (previousPriceTextView: TextView, previousPrice) = upvCalculate(product)
                previousPriceTextView.text = String.format("UPV %.2f €", previousPrice)

            }
            var isFavorite = false
            addToFavoriteB.setOnClickListener {
                if (isFavorite) {
                    // Set the drawable to the empty heart icon
                    addToFavoriteB.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.icon_heart_dark_empty, 0, 0, 0
                    )
                    addToFavoriteB.text = "to favorite"
                    if (product != null) {
                        viewModelFavorite.setProduct(product)
                    }


                } else {
                    // Set the drawable to the filled heart icon
                    addToFavoriteB.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.icon_heart_fill,
                        0,
                        0,
                        0
                    )
                    addToFavoriteB.text = "added to favorite"
                    if (product != null) {
                        viewModelFavorite.removeProductFromFavorites(product)
                    }
                }
                // Toggle the state
                isFavorite = !isFavorite


            }


        }
    }

    private fun FragmentDetailsBinding.upvCalculate(
        product: Product
    ): Pair<TextView, Double> {
        val previousPriceTextView: TextView = previousPriceTV
        previousPriceTextView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        val previousPrice = (product.price!! * 1.20)
        return Pair(previousPriceTextView, previousPrice)
    }


}



