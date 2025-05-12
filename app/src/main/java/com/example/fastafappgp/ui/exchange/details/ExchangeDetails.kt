package com.example.fastafappgp.ui.exchange.details

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.fastafappgp.R
import com.example.fastafappgp.databinding.ActivityExchangeDetailsBinding

class ExchangeDetails : AppCompatActivity() {
    lateinit var  bidning : ActivityExchangeDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initviwe()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


    fun initviwe(){
        bidning = DataBindingUtil.setContentView(this,R.layout.activity_exchange_details)
        bidning.vm = ExchangeDetailsViewModel()
        bidning.lifecycleOwner = this

    }
}