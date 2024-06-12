package com.amircodeing.syntaxinstitut.unique_store.presentation.details

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import coil.load
import com.amircodeing.syntaxinstitut.unique_store.R
import com.amircodeing.syntaxinstitut.unique_store.data.model.Cart
import com.amircodeing.syntaxinstitut.unique_store.data.model.Product
import com.amircodeing.syntaxinstitut.unique_store.databinding.FragmentDetailsBinding
import com.amircodeing.syntaxinstitut.unique_store.presentation.home.HomeViewModel
import com.amircodeing.syntaxinstitut.unique_store.utils.ChangeButtonNavVisibility
import com.amircodeing.syntaxinstitut.unique_store.utils.Constants
import com.amircodeing.syntaxinstitut.unique_store.utils.CustomToolbar.Companion.setToolbar
import com.amircodeing.syntaxinstitut.unique_store.utils.ToolbarComponents

const val TAG = " SignUpFragment"

class DetailsFragment : Fragment(R.layout.fragment_details) {
    private lateinit var binding: FragmentDetailsBinding
    private val viewModel: HomeViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Refresh the favorite IDs
        viewModel.getFavoriteProductsId()
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

                }
            }

            addToFavoriteB.setOnClickListener {
                if (product != null) {

                    viewModel.favoriteProductsId.observe(viewLifecycleOwner) { productsId ->
                        if (!productsId.contains(product.id)) {
                            viewModel.addFavorite(product)
                            Toast.makeText(
                                context,
                                "The product has been added to your cart",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                context,
                                "The desired product is already in your cart",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
            shareB.setOnClickListener {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                val shareMessage =
                    "Check out this product:\n ${product?.title}\n\n\nPrice: ${product?.price} €"
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                startActivity(Intent.createChooser(shareIntent, "Share Product Details"))
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



