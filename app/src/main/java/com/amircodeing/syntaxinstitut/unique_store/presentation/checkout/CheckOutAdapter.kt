package com.amircodeing.syntaxinstitut.unique_store.presentation.checkout

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.amircodeing.syntaxinstitut.unique_store.R
import com.amircodeing.syntaxinstitut.unique_store.data.model.Product
import com.amircodeing.syntaxinstitut.unique_store.data.model.User
import com.amircodeing.syntaxinstitut.unique_store.databinding.ItemPaymentBinding
import com.amircodeing.syntaxinstitut.unique_store.databinding.ItemProductBinding
import com.amircodeing.syntaxinstitut.unique_store.presentation.home.HomeViewModel
import com.amircodeing.syntaxinstitut.unique_store.presentation.home.ProductAdapter

class CheckOutAdapter : ListAdapter<Product , CheckOutAdapter.ItemViewHolder>(object : DiffUtil.ItemCallback<Product>(){
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean = oldItem == newItem

}){

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

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) = holder.bind(getItem(position))

}