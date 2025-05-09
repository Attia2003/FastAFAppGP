package com.example.fastafappgp.ui.expiry

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.fastafappgp.R
import com.example.fastafappgp.databinding.ActivityExpiryBinding
import com.example.fastafappgp.ui.login.pharmacy.PharmacyRepository
import kotlinx.coroutines.launch

class ExpiryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExpiryBinding
    private val viewModel: ExpiryViewModel by viewModels()
    private val adapter = ExpiryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initview()
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupRecyclerView()
        setupObservers()
        setupClickListeners()
        setupSearchListener()
        performInitialSearch()
    }

    private fun setupRecyclerView() {
        binding.expiryRecyclerView.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.searchResults.observe(this) { items ->
            adapter.submitList(items)
        }

        viewModel.error.observe(this) { error ->
            // You can show a Toast or Snackbar here if needed
        }
    }

    private fun setupClickListeners() {
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupSearchListener() {
        binding.searchExpiray.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                viewModel.performSearch(s.toString())
            }
        })
    }

    private fun performInitialSearch() {
        viewModel.performSearch()
    }

    fun initview() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_expiry)
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }
}