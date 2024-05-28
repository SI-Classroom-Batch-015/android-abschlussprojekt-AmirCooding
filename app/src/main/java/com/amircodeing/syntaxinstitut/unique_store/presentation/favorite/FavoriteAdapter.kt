package com.amircodeing.syntaxinstitut.unique_store.presentation.favorite

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.amircodeing.syntaxinstitut.unique_store.R
import com.amircodeing.syntaxinstitut.unique_store.data.model.Product
import com.amircodeing.syntaxinstitut.unique_store.databinding.ItemProductBinding
import com.amircodeing.syntaxinstitut.unique_store.databinding.ItemToFavoriteBinding
import com.amircodeing.syntaxinstitut.unique_store.presentation.home.HomeViewModel
import com.amircodeing.syntaxinstitut.unique_store.presentation.home.ProductAdapter
import com.google.firebase.database.FirebaseDatabase

class FavoriteAdapter(
    private val dataset: List<Product>,
    private val viewModel: FavoriteViewModel
) : RecyclerView.Adapter<FavoriteAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(private val binding: ItemToFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) = with(binding) {
            imageProductIV.load(product.image)
            itemNameTV.text = product.title
            // set max 50 Char for Description and And it ends with three dots
            val description = product.description ?: ""
            val truncatedDescription = if (description.length > 70) description.substring(0, 70) +"..."  else ""
            itemDescriptionTV.text = truncatedDescription
            val previousPriceTextView: TextView = itemUpvPriceTV
            previousPriceTextView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            itemCurrentPriceTV.text = String.format("%.2f €", product.price)
            val previousPrice = (product.price!! * 1.20)
            previousPriceTextView.text = String.format("%.2f €", previousPrice)
            binding.root.setOnClickListener {
                val navController = Navigation.findNavController(binding.root)
                navController.navigate(R.id.detailsFragment)
            }
            removeProduct.setOnClickListener {
                viewModel.removeProductFromFavoriteList(product)
            }
        }




    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.ItemViewHolder {
        val binding = ItemToFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val product = dataset[position]
        holder.bind(product)

    }


    override fun getItemCount(): Int {
        return dataset.size
    }
}