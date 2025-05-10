package com.example.fastafappgp.ui.order.previeworder

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.fastafappgp.R
import com.example.fastafappgp.databinding.ActivityPreViewOrderBinding

class PreViewOrder : AppCompatActivity() {
    private lateinit var binding: ActivityPreViewOrderBinding
    private val viewModel = PreViewViewModel.getInstance()
    private lateinit var adapter: PreviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initView()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        observeEvents()
    }

    private fun initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pre_view_order)
        binding.vm = viewModel
        binding.lifecycleOwner = this

        adapter = PreviewAdapter()
        binding.recylerorder.adapter = adapter

        observePreviewItems()
    }

    private fun observePreviewItems() {
        viewModel.previewItems.observe(this) { items ->
            Log.d("PreViewOrder", "Received items update. Size: ${items.size}")
            Log.d("PreViewOrder", "Items: ${items.map { "${it.drugName} (${it.amount})" }}")
            adapter.updateItems(items)
        }
    }

    private fun observeEvents() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is PreViewOrderEvent.Success -> {
                    android.widget.Toast.makeText(this, "Order uploaded successfully!", android.widget.Toast.LENGTH_SHORT).show()
                    finish()
                }
                is PreViewOrderEvent.Error -> {
                    android.widget.Toast.makeText(this, event.message, android.widget.Toast.LENGTH_SHORT).show()
                }
                is PreViewOrderEvent.Loading -> {

                }
            }
        }
    }
}