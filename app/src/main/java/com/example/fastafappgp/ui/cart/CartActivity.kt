package com.example.fastafappgp.ui.cart

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fastafappgp.Constant
import com.example.fastafappgp.R
import com.example.fastafappgp.databinding.ActivityCart2Binding

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCart2Binding
    private val viewModel: CartViewModel by viewModels()
    private val adapter = CartAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        initView()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val selectedIds = intent.getIntegerArrayListExtra(Constant.listidsreset) ?: emptyList()

        if (selectedIds.isNotEmpty()) {
            viewModel.fetchCartItems(selectedIds)
        }


        viewModel.cartItems.observe(this) { items ->
            Log.d("Cartshowingitems", "Received item")
            adapter.updateData(items)
        }


        viewModel.error.observe(this) { error ->
            if (error.isNotEmpty()) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart2)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        binding.reclerReset.adapter = adapter
    }

//    private fun submitCartData() {
//
//        val updatedItems = adapter.getUpdatedItems()
//
//        if (updatedItems.isNotEmpty()) {
//
//            viewModel.submitCart(updatedItems)
//        } else {
//            Toast.makeText(this, "No items to submit", Toast.LENGTH_SHORT).show()
//        }
//    }
}