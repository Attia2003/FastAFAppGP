package com.example.fastafappgp.ui.expiry

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fastafappgp.databinding.ItemExpiryBinding
import com.example.fastafappgp.ui.cart.search.ResponseSearchItem

class ExpiryAdapter : ListAdapter<ResponseSearchItem, ExpiryAdapter.ExpiryViewHolder>(ExpiryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpiryViewHolder {
        val binding = ItemExpiryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExpiryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExpiryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ExpiryViewHolder(private val binding: ItemExpiryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponseSearchItem) {
            binding.apply {
                tvName.text = item.drug?.name
                tvForm.text = item.drug?.form
                tvStock.text = "Stock: ${item.stock}"
                tvExpiryDate.text = "Expiry Date: ${item.expiryDate}"
            }
        }
    }

    private class ExpiryDiffCallback : DiffUtil.ItemCallback<ResponseSearchItem>() {
        override fun areItemsTheSame(oldItem: ResponseSearchItem, newItem: ResponseSearchItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ResponseSearchItem, newItem: ResponseSearchItem): Boolean {
            return oldItem == newItem
        }
    }
} 