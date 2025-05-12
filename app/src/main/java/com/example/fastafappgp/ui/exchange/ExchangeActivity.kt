package com.example.fastafappgp.ui.exchange

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.fastafappgp.R
import com.example.fastafappgp.databinding.ActivityExchangeBinding

class ExchangeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExchangeBinding
    private val viewModel: ExchangeViewModel by viewModels()
    private val adapter = ReceiptsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_exchange)
        binding.vm = viewModel
        binding.lifecycleOwner = this

        setupRecyclerView()
        setupSearchView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        binding.receiptsRecycler.adapter = adapter
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.isNullOrEmpty()) {
                    viewModel.getReceipts()
                    return true
                }
                
                val receiptId = query.toIntOrNull()
                if (receiptId != null) {
                    viewModel.getReceipts(receiptId)
                } else {
                    Toast.makeText(this@ExchangeActivity, "Please enter a valid receipt number", Toast.LENGTH_SHORT).show()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    viewModel.getReceipts()
                }
                return true
            }
        })
    }

    private fun observeViewModel() {
        viewModel.receipts.observe(this) { receipts ->
            if (receipts.isNullOrEmpty()) {
                binding.emptyState.visibility = View.VISIBLE
                binding.receiptsRecycler.visibility = View.GONE
            } else {
                binding.emptyState.visibility = View.GONE
                binding.receiptsRecycler.visibility = View.VISIBLE
                adapter.updateData(receipts)
            }
        }
        viewModel.error.observe(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_LONG).show()
        }
    }
}





