package com.example.fastafappgp.ui.cart

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fastafappgp.databinding.ItemCartBinding

class CartAdapter(
    private var items: MutableList<ResponseDrugReceiopts> = mutableListOf()
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCartBinding.inflate(inflater, parent, false)
        return CartViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = items[position]
        val binding = holder.binding

        binding.name.text = item.drug.name
        binding.form.text = item.drug.form
        binding.unitprice.text = item.drug.fullPrice.toString()
        binding.stock.text = item.stock.toString()
        binding.unitsin.text = item.drug.units.toString()

        binding.packedit.setText(item.inputpacks?.toString() ?: "")
        binding.unitedit.setText(item.inputunits?.toString() ?: "")

        binding.packedit.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val newPacks = binding.packedit.text.toString().toIntOrNull() ?: 0
                items[position] = items[position].copy(inputpacks = newPacks)
            }
        }

        binding.unitedit.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val newUnits = binding.unitedit.text.toString().toIntOrNull() ?: 0
                items[position] = items[position].copy(inputunits = newUnits)
            }
        }
    }

    fun updateData(newList: List<ResponseDrugReceiopts>) {
        items = newList.toMutableList()
        notifyDataSetChanged()
    }

    fun getUpdatedItems(): List<ResponseDrugReceiopts> = items
}