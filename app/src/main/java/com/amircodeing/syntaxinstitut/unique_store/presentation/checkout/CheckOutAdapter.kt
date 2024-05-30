package com.amircodeing.syntaxinstitut.unique_store.presentation.checkout

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.amircodeing.syntaxinstitut.unique_store.R
import com.amircodeing.syntaxinstitut.unique_store.data.model.Product
import com.amircodeing.syntaxinstitut.unique_store.databinding.ItemPaymentBinding
import com.amircodeing.syntaxinstitut.unique_store.databinding.ItemProductBinding
import com.amircodeing.syntaxinstitut.unique_store.presentation.home.HomeViewModel
import com.amircodeing.syntaxinstitut.unique_store.presentation.home.ProductAdapter

class CheckOutAdapter(
    private val dataset: List<Product>,
) : RecyclerView.Adapter<CheckOutAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(private val binding: ItemPaymentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) = with(binding) {
            imageView.load(product.image)
            titleProductTV.text = product.title
            pricePaymentItem.text = String.format("%.2f €", product.price)
            quantityProductTV.text = product.quantity.toString()

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CheckOutAdapter.ItemViewHolder {
        val binding = ItemPaymentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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