package com.amircodeing.syntaxinstitut.unique_store.presentation.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.amircodeing.syntaxinstitut.unique_store.data.model.Product
import com.amircodeing.syntaxinstitut.unique_store.databinding.ItemToCartBinding

class CartAdapter(
    private val viewModel: CartViewModel
) : ListAdapter<Product, CartAdapter.ItemViewHolder>(ProductDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemToCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
    }

    inner class ItemViewHolder(private val binding: ItemToCartBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) = with(binding) {
            imageCartIV.load(product.image)
            priceCartTV.text = product.price.toString()
            countProduct.text = product.quantity.toString()

            increaseItem.setOnClickListener {
                viewModel.increaseItemQuantity(product)
            }

            decreaseFromCart.setOnClickListener {
                viewModel.decreaseItemQuantity(product)
            }
        }
    }

    private class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}
