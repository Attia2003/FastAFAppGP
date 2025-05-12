package com.example.fastafappgp.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.fastafappgp.R
import com.example.fastafappgp.databinding.ActivityMainBinding
import com.example.fastafappgp.ui.cam.CamActivity
import com.example.fastafappgp.ui.cart.CartActivity
import com.example.fastafappgp.ui.cart.search.SearchActivity
import com.example.fastafappgp.ui.exchange.ExchangeActivity
import com.example.fastafappgp.ui.expiry.ExpiryActivity
import com.example.fastafappgp.ui.order.OrderActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val viewmodel: MainViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initview()
        setupBottomNavigation()
        setupWindowInsets()
        SubscribetoLiveData()
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initview() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.vm = viewmodel
        binding.lifecycleOwner = this
    }



    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {

                    true
                }
                R.id.navigation_search -> {
                    startActivity(Intent(this, SearchActivity::class.java))
                    true
                }
                R.id.navigation_orders -> {
                    startActivity(Intent(this, OrderActivity::class.java))
                    true
                }
                R.id.navigation_expiry -> {
                    startActivity(Intent(this, ExpiryActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    fun SubscribetoLiveData() {
        viewmodel.event.observe(this) {
            when (it) {
                EventNavigate.NavigateToCart -> opencart()
                EventNavigate.NavigateToOrder -> openorder()
                EventNavigate.NavigateToExpiry -> openexpiry()
                EventNavigate.NavigateToExchange -> openexchange()

            }
        }
    }


    fun openorder() {
        val intent = Intent(this, OrderActivity::class.java)
        startActivity(intent)
    }

    fun openexpiry() {
        val intent = Intent(this, ExpiryActivity::class.java)
        startActivity(intent)
    }
    fun opencart() {
        val intent = Intent(this, CartActivity::class.java)
        startActivity(intent)
    }
    fun openexchange() {
        val intent = Intent(this, ExchangeActivity::class.java)
        startActivity(intent)
    }

}