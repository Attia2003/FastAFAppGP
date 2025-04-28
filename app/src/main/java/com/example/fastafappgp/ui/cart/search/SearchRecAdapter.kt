package com.example.fastafappgp.ui.cart.search

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fastafappgp.databinding.ItemSearchBinding

class SearchRecAdapter  (var itemsearch : List<ResponseSearchItem>
    , private val onItemClick: (Int) -> Unit
    ): RecyclerView.Adapter<SearchRecAdapter.searchviewhodelr>(){

    class searchviewhodelr( val binding: ItemSearchBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): searchviewhodelr {
        val binding  = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)

            return searchviewhodelr(binding)
            }

    override fun getItemCount(): Int = itemsearch.size


    override fun onBindViewHolder(holder: searchviewhodelr, position: Int) {
            holder.binding.titleName.text = itemsearch[position].name
            holder.binding.type.text = itemsearch[position].form
        holder.binding.infoIcon.setOnClickListener {
            it.id?.let {
                id -> onItemClick(id) }
        }
        }



    fun updateData(newList: List<ResponseSearchItem>) {
        itemsearch = newList
        notifyDataSetChanged()
        Log.d("AdapterDebug", "Updated list size: ${itemsearch.size}")

    }

}