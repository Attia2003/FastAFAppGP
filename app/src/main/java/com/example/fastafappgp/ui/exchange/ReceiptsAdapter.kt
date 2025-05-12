package com.example.fastafappgp.ui.exchange

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fastafappgp.databinding.ItemExchangeBinding
import java.text.SimpleDateFormat
import java.util.Locale

class ReceiptsAdapter(
    private var items: List<ResponseExchange> = emptyList(),
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<ReceiptsAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemExchangeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponseExchange, onItemClick: (Int) -> Unit) {
            Log.d("ReceiptsAdapter", "Binding receipt: id=${item.id}," +
                    " cashier=${item.cashier?.username}, total=${item.total}, items=${item.items?.size}")
            binding.apply {
                tvReceiptNumber.text = "Receipt #${item.id}"
                tvEmployee.text = item.cashier?.username ?: "Unknown"
                
                val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault())
                val outputFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                val date = try {
                    inputFormat.parse(item.createdAt ?: "")
                } catch (e: Exception) {
                    null
                }
                tvDate.text = date?.let { outputFormat.format(it) } ?: (item.createdAt ?: "No date")
                
                tvTotal.text = "Total: ${item.total ?: 0.0}"

                root.setOnClickListener {
                    item.id?.let { id -> onItemClick(id) }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemExchangeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], onItemClick)
    }

    override fun getItemCount(): Int = items.size

    fun updateData(newItems: List<ResponseExchange>) {
        Log.d("ReceiptsAdapter", "updateData called with ${newItems.size} receipts")
        items = newItems
        notifyDataSetChanged()
    }
} 