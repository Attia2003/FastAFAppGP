package com.example.fastafappgp.ui.order.previeworder

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fastafappgp.databinding.ItemPreviewBinding

class PreviewAdapter(
    private var items: List<PreviewItem> = emptyList()
) : RecyclerView.Adapter<PreviewAdapter.PreviewViewHolder>() {

    class PreviewViewHolder(val binding: ItemPreviewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviewViewHolder {
        val binding = ItemPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PreviewViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PreviewViewHolder, position: Int) {
        val item = items[position]
        Log.d("PreviewAdapter", "Binding item at position $position: ${item.drugName}, Amount: ${item.amount}")
        holder.binding.apply {
            name.text = item.drugName
            amount.text = "Amount: ${item.amount}"
            type.text = item.drugForm
        }
    }

    fun updateItems(newItems: List<PreviewItem>) {
        Log.d("PreviewAdapter", "Updating items. New size: ${newItems.size}")
        Log.d("PreviewAdapter", "New items: ${newItems.map { "${it.drugName} (${it.amount})" }}")
        items = newItems
        notifyDataSetChanged()
    }
} 