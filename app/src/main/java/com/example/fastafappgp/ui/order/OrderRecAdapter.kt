package com.example.fastafappgp.ui.order

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fastafappgp.databinding.ItemOrderBinding

class OrderRecAdapter(
    var itemorder: List<ResponseOrderItem>,
    private val onAmountChanged: (drugId: Int, amount: Int) -> Unit
) : RecyclerView.Adapter<OrderRecAdapter.OrderViewHolder>() {

    private val userInputMap = mutableMapOf<Int, Int>()

    class OrderViewHolder(val binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun getItemCount(): Int = itemorder.size

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val item = itemorder[position]
        val drug = item.drug

        holder.binding.apply {
            tvDrugName.text = drug?.name ?: "Unknown"
            tvDrugForm.text = drug?.form ?: "-"
            tvShortageLabel.setText(item.shortage?.toString() ?: "0")

            etAmount.setText(userInputMap[drug?.id]?.toString() ?: "")

            etAmount.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    val enteredValue = etAmount.text.toString().toIntOrNull() ?: 0
                    drug?.id?.let { id ->
                        userInputMap[id] = enteredValue
                        Log.d("OrderRecAdapter", "Updated amount for drug ID $id: $enteredValue")
                        Log.d("OrderRecAdapter", "Current map size: ${userInputMap.size}")
                        onAmountChanged(id, enteredValue)
                    }
                }
            }
        }
    }

    fun getUserInputMap(): Map<Int, Int> {
        Log.d("OrderRecAdapter", "Getting user input map. Size: ${userInputMap.size}")
        Log.d("OrderRecAdapter", "Map contents: $userInputMap")
        return userInputMap.toMap()
    }

    fun clearUserInput() {
        userInputMap.clear()
    }
}
