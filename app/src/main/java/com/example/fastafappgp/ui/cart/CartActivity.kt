package com.example.fastafappgp.ui.cart

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
import com.example.fastafappgp.util.Constant
import com.example.fastafappgp.R
import com.example.fastafappgp.databinding.ActivityCart2Binding
import com.example.fastafappgp.ui.cam.CamActivity
import com.example.fastafappgp.ui.cart.search.SearchActivity


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

        viewModel.submitResult.observe(this) { success ->
            if (success) {
                Toast.makeText(this, "Receipts uploaded successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Failed to upload receipts", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.event.observe(this){
            when(it){
                EventNavigateCart.NavigateToSearch -> {
                    opensearch()
                }
                EventNavigateCart.NavigateToCam -> {
                    opencam()
                }

            }
        }






    }

    private fun initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart2)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        binding.reclerReset.adapter = adapter

        binding.submitReset.setOnClickListener {
            submitCartData()
        }
    }

    private fun submitCartData() {
        val updatedItems = adapter.getUpdatedItems()
        if (updatedItems.isNotEmpty()) {
            viewModel.submitReceipts(updatedItems)
        } else {
            Toast.makeText(this, "No items to submit", Toast.LENGTH_SHORT).show()
        }
    }

    fun opencam(){
        val intent = Intent(this, CamActivity::class.java)
        startActivity(intent)
    }

    fun opensearch(){
        val intent = Intent(this, SearchActivity::class.java)
        startActivity(intent)
    }


}