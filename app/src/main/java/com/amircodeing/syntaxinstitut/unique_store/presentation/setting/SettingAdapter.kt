package com.amircodeing.syntaxinstitut.unique_store.presentation.setting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.amircodeing.syntaxinstitut.unique_store.R
import com.amircodeing.syntaxinstitut.unique_store.data.model.Setting
import com.amircodeing.syntaxinstitut.unique_store.databinding.ItemSettingBinding


class SettingAdapter :
    ListAdapter<Setting, SettingAdapter.ItemViewHolder>(object : DiffUtil.ItemCallback<Setting>() {
        override fun areItemsTheSame(oldItem: Setting, newItem: Setting): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Setting, newItem: Setting): Boolean =
            oldItem == newItem

    }) {
    inner class ItemViewHolder(private val binding: ItemSettingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(setting: Setting?, position: Int) {
            with(binding) {
                icon.load(setting?.fixIcon)
                title.text = setting?.title
                itemDesc.text = setting?.description
                val isExpandable: Boolean = setting?.isExpandable ?: false
                itemDesc.visibility = if (isExpandable) View.VISIBLE else View.GONE
                val setArrow = if (isExpandable)  R.drawable.icon_arrow_up else R.drawable.iconamoon_arrow_down_thin
                iconArrow.load(setArrow)
                iconArrow.setOnClickListener {
                    setting?.isExpandable = !setting?.isExpandable!!
                    notifyItemChanged(position)
                }

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder(
            ItemSettingBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }
}
