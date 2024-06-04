package com.amircodeing.syntaxinstitut.unique_store.presentation.details

import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import coil.load
import com.amircodeing.syntaxinstitut.unique_store.R
import com.amircodeing.syntaxinstitut.unique_store.data.model.Cart
import com.amircodeing.syntaxinstitut.unique_store.data.model.Product
import com.amircodeing.syntaxinstitut.unique_store.databinding.FragmentDetailsBinding
import com.amircodeing.syntaxinstitut.unique_store.presentation.favorite.FavoriteViewModel
import com.amircodeing.syntaxinstitut.unique_store.presentation.home.HomeViewModel
import com.amircodeing.syntaxinstitut.unique_store.utils.ChangeButtonNavVisibility
import com.amircodeing.syntaxinstitut.unique_store.utils.Constants
import com.amircodeing.syntaxinstitut.unique_store.utils.CustomToolbar.Companion.setToolbar
import com.amircodeing.syntaxinstitut.unique_store.utils.ToolbarComponents

const val TAG = " SignUpFragment"

class DetailsFragment : Fragment(R.layout.fragment_details) {
    private lateinit var binding: FragmentDetailsBinding
    private val viewModel: HomeViewModel by activityViewModels()
    private val favorite: FavoriteViewModel by activityViewModels()
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

            addToCartB.setOnClickListener {
                if (product != null) {
                    viewModel.addProductToCart(product)

                    addToCartB.visibility = View.INVISIBLE
                }
            }

            addToFavoriteB.setOnClickListener {

                if (product != null) {
                    viewModel.addFavorite(product)
                }
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



