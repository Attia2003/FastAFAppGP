package com.example.fastafappgp.ui.order

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.fastafappgp.R
import com.example.fastafappgp.databinding.ActivityOrderBinding
import com.example.fastafappgp.ui.order.previeworder.PreViewOrder
import com.example.fastafappgp.ui.order.previeworder.PreViewViewModel
import com.example.fastafappgp.ui.order.previeworder.PreviewItem

class OrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderBinding
    private val viewModel: OrderViewModel by viewModels()
    private val previewViewModel = PreViewViewModel.getInstance()
    private lateinit var adapter: OrderRecAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initview()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initview() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        adapter = OrderRecAdapter(
            emptyList(),
            onAmountChanged = { drugId, amount ->
                Log.d("OrderActivity", "Amount changed for drug ID $drugId: $amount")
            }
        )
        binding.recyclerOrder.adapter = adapter

        binding.btnSendToPreview.setOnClickListener {
            currentFocus?.clearFocus()
            binding.root.post {
                sendAllItemsToPreview()
            }
        }

        observeShortageData()
        viewModel.getShortageForCurrentPharmacy()
    }

    private fun sendAllItemsToPreview() {
        val items = adapter.itemorder
        val userInputMap = adapter.getUserInputMap()
        var hasItems = false

        Log.d("OrderActivity", "Total items: ${items.size}")
        Log.d("OrderActivity", "User input map size: ${userInputMap.size}")
        Log.d("OrderActivity", "User input map: $userInputMap")

        previewViewModel.clearPreviewItems()

        val previewItemsList = mutableListOf<PreviewItem>()

        for (item in items) {
            val drugId = item.drug?.id
            if (drugId != null) {
                val amount = userInputMap[drugId] ?: 0
                Log.d("OrderActivity", "Processing item: ${item.drug?.name}, ID: $drugId, Amount: $amount")
                
                if (amount > 0) {
                    hasItems = true
                    val previewItem = PreviewItem(
                        drugName = item.drug?.name ?: "Unknown",
                        drugForm = item.drug?.form ?: "-",
                        amount = amount
                    )
                    previewItemsList.add(previewItem)
                    Log.d("OrderActivity", "Added to preview list: ${previewItem.drugName}, Amount: ${previewItem.amount}")
                }
            }
        }

        if (previewItemsList.isNotEmpty()) {
            Log.d("OrderActivity", "Setting ${previewItemsList.size} items to preview")
            previewViewModel.setPreviewItems(previewItemsList)
            val intent = Intent(this, PreViewOrder::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Please enter amounts for at least one item", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeShortageData() {
        viewModel.shortageData.observe(this) { list ->
            list?.let {
                adapter.itemorder = it
                adapter.notifyDataSetChanged()
            }
        }
    }
}