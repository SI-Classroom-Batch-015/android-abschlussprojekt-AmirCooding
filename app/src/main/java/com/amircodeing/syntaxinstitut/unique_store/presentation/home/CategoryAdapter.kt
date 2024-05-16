package com.amircodeing.syntaxinstitut.unique_store.presentation.home
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.amircodeing.syntaxinstitut.unique_store.data.model.Category
import com.amircodeing.syntaxinstitut.unique_store.databinding.ItemCategoryBinding

class CategoryAdapter (
        private val dataset: List<Category>,
    ) : RecyclerView.Adapter<CategoryAdapter.ItemViewHolder>() {
        inner class ItemViewHolder(private val binding: ItemCategoryBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(category: Category) = with(binding) {
                binding.categoryBackground.load(category.image)
                binding.categoryTitleTV.text= category.category
            }

        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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