package com.example.fastafappgp.ui.order.orderprocess

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.fastafappgp.R
import com.example.fastafappgp.databinding.ActivityOrderProcessActvityBinding

class DeliveryActivity : AppCompatActivity() {
    lateinit var binding : ActivityOrderProcessActvityBinding
    val viewModel : DeliveryViewModel  by viewModels()
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

    fun initview(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_process_actvity)
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }
}