package com.amircodeing.syntaxinstitut.unique_store.presentation.listitems

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.amircodeing.syntaxinstitut.unique_store.data.model.Category
import com.amircodeing.syntaxinstitut.unique_store.data.model.Product
import com.amircodeing.syntaxinstitut.unique_store.databinding.ItemCategoryBinding
import com.amircodeing.syntaxinstitut.unique_store.databinding.ItemProductBinding

class ListCategoryAdapter(
    private val dataset: List<Product>,
) : RecyclerView.Adapter<ListCategoryAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) = with(binding) {
            imageProductIV.load(product.image)
            itemNameTV.text = product.title
            // set max 50 Char for Description and And it ends with three dots
            val description = product.description ?: ""
            val truncatedDescription = if (description.length > 20) description.substring(0, 20) +"..."  else ""
            itemDescriptionTV.text = truncatedDescription
            val previousPriceTextView: TextView = itemUpvPriceTV
            previousPriceTextView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            itemCurrentPriceTV.text = String.format("%.2f €", product.price)
            val previousPrice = (product.price!! * 1.20)
            previousPriceTextView.text = String.format("%.2f €", previousPrice)

        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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