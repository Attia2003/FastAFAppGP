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

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val viewmodel : MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initview()
        SubscribetoLiveData()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    fun initview(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.vm = viewmodel
        binding.lifecycleOwner = this
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