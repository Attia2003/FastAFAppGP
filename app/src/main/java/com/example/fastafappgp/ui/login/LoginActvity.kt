package com.example.fastafappgp.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.fastafappgp.R
import com.example.fastafappgp.databinding.ActivityLoginActvityBinding
import com.example.fastafappgp.ui.main.MainActivity

class LoginActvity : AppCompatActivity() {
    lateinit var bidning : ActivityLoginActvityBinding
    val viewmodel : LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initview()
        TokenManager.init(this)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        subscribeLiveData()
    }

    fun initview(){
        bidning = DataBindingUtil.setContentView(this,R.layout.activity_login_actvity)
        bidning.vm = viewmodel
        bidning.lifecycleOwner = this
    }
    fun subscribeLiveData(){
        viewmodel.event.observe(this){
            when(it){
                NavigateEvent.NavigateToCart->{
                    GoToCartActivity()
                }
            }
        }
        viewmodel.loginError.observe(this) { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    fun GoToCartActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}