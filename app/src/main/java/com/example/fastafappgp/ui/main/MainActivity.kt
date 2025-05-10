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
import com.example.fastafappgp.ui.cart.search.SearchActivity
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
        setupClickListeners()
        setupBottomNavigation()
        setupWindowInsets()
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

    private fun setupClickListeners() {
        binding.camCard.setOnClickListener {
            startActivity(Intent(this, CamActivity::class.java))
        }

        binding.searchCard.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }

        binding.orderCard.setOnClickListener {
            startActivity(Intent(this, OrderActivity::class.java))
        }

        binding.expiryCard.setOnClickListener {
            startActivity(Intent(this, ExpiryActivity::class.java))
        }
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    // Already on home, do nothing
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
            Log.d("event", it.toString())
            when(it) {
                EventNavigate.NavigateToCam -> {
                    openamactivity()
                }
                EventNavigate.NavigateToSearch -> {
                    opensearch()
                }
                EventNavigate.NavigateToOrder -> {
                    openorder()
                }
                EventNavigate.NavigateToExpiry -> {
                    openexpiry()
                }
                else -> {}
            }
        }
    }

    fun openamactivity() {
        val intent = Intent(this, CamActivity::class.java)
        startActivity(intent)
    }

    fun opensearch() {
        val intent = Intent(this, SearchActivity::class.java)
        startActivity(intent)
    }

    fun openorder() {
        val intent = Intent(this, OrderActivity::class.java)
        startActivity(intent)
    }

    fun openexpiry() {
        val intent = Intent(this, ExpiryActivity::class.java)
        startActivity(intent)
    }
}