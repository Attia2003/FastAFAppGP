package com.example.fastafappgp.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
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
import com.google.android.material.snackbar.Snackbar

class LoginActvity : AppCompatActivity() {
    lateinit var binding: ActivityLoginActvityBinding
    val viewmodel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initview()
        TokenManager.init(this)
        setupWindowInsets()
        subscribeLiveData()
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun initview() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_actvity)
        binding.vm = viewmodel
        binding.lifecycleOwner = this
    }

    fun subscribeLiveData() {

        viewmodel.event.observe(this) { event ->
            when (event) {
                NavigateEvent.NavigateToCart -> {
                    GoToCartActivity()
                }
            }
        }


        viewmodel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.loginButton.isEnabled = !isLoading
        }


        viewmodel.loginError.observe(this) { errorMessage ->
            showError(errorMessage)
        }
    }

    private fun showError(message: String) {

        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setAction("Dismiss") { }
            .show()
    }

    fun GoToCartActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
