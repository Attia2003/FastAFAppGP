package com.example.fastafappgp.ui.details

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.fastafappgp.Constant
import com.example.fastafappgp.R
import com.example.fastafappgp.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {
    lateinit var bidning : ActivityDetailsBinding
    val viewModel : DetailsViewModel by viewModels()
    private var drugId: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initview()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        drugId = intent.getIntExtra(Constant.DetailsDrugID, -1)
        if (drugId != -1) {

            viewModel.fetchDetails(drugId)
        } else {
            Toast.makeText(this, "Invalid Drug ID", Toast.LENGTH_SHORT).show()
        }
        SubscribeLiveData()
    }

    fun initview(){
        bidning = DataBindingUtil.setContentView(this,R.layout.activity_details)
        bidning.vm = viewModel
        bidning.lifecycleOwner = this

    }

    fun SubscribeLiveData(){
        viewModel.error.observe(this){
            Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
        }
    }


}