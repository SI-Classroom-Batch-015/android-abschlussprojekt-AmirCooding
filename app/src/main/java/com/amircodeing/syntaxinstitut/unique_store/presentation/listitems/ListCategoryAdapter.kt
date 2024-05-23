package com.amircodeing.syntaxinstitut.unique_store.presentation.listitems

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
import com.amircodeing.syntaxinstitut.unique_store.databinding.ItemListCategoryBinding
import com.amircodeing.syntaxinstitut.unique_store.databinding.ItemProductBinding
import com.amircodeing.syntaxinstitut.unique_store.presentation.home.HomeViewModel


class ListCategoryAdapter(
    private val viewModel: HomeViewModel
) : ListAdapter<Product, ListCategoryAdapter.ItemViewHolder>(object : DiffUtil.ItemCallback<Product>(){
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean = oldItem.id == newItem.id


    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean = newItem == oldItem

}){
   inner class ItemViewHolder(private val binding : ItemListCategoryBinding)  : RecyclerView.ViewHolder(binding.root){
       fun bind(product: Product) = with(binding) {
           imageProductIV.load(product.image)
           itemNameTV.text = product.title
           // set max 50 Char for Description and And it ends with three dots
           val description = product.description ?: ""
           val truncatedDescription =
               if (description.length > 20) description.substring(0, 70) + "..." else ""
           itemDescriptionTV.text = truncatedDescription
           val previousPriceTextView: TextView = itemUpvPriceTV
           previousPriceTextView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
           itemCurrentPriceTV.text = String.format("%.2f €", product.price)
           val previousPrice = (product.price!! * 1.20)
           previousPriceTextView.text = String.format("%.2f €", previousPrice)
           binding.root.setOnClickListener {
               val navController = Navigation.findNavController(binding.root)
               viewModel.setProduct(product)
               navController.navigate(R.id.detailsFragment)

           }
       }
   }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCategoryAdapter.ItemViewHolder =
        ItemViewHolder(
            ItemListCategoryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ListCategoryAdapter.ItemViewHolder, position: Int) = holder.bind(getItem(position))

}