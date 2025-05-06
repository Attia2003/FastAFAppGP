package com.example.fastafappgp.ui.cart.search

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fastafappgp.databinding.ItemSearchBinding

class SearchRecAdapter(
    private var itemsearch: List<Drug> ?= null,
    private val onInfoClick: (Int) -> Unit,
    private val onDrugSelect: (Int) -> Unit
) : RecyclerView.Adapter<SearchRecAdapter.SearchViewHolder>() {

    class SearchViewHolder(val binding: ItemSearchBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        Log.d("AdapterDebug", "onCreateViewHolder called")
           return SearchViewHolder(binding)
    }

    override fun getItemCount(): Int = itemsearch?.size?:0

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = itemsearch!![position]
        Log.d("AdapterDebug", "Binding item: ${item.name}")

        holder.binding.titleName.text = item.name
        holder.binding.type.text = item.form

        holder.binding.infoIcon.setOnClickListener {
            item.id?.let { it1 -> onInfoClick(it1) }
        }

        holder.binding.titleName.setOnClickListener {
            item.id?.let { id -> onDrugSelect(id) }
        }

        holder.binding.type.setOnClickListener {
            item.id?.let { id -> onDrugSelect(id) }
        }
    }

    fun updateData(newList: List<Drug>) {
        itemsearch = newList
        notifyDataSetChanged()
        Log.d("AdapterDebug", "Updated list size: ${itemsearch!!.size}")
    }
}
