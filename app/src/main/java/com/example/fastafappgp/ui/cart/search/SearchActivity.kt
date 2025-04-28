package com.example.fastafappgp.ui.cart.search

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.example.fastafappgp.R
import com.example.fastafappgp.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    lateinit var binding: ActivitySearchBinding
    val viewModel: SearchViewModel by viewModels()
    val adapter = SearchRecAdapter(emptyList()) { drugId -> // Handle item click
        openDetailActivity(drugId)
    }
    private val handler = Handler(Looper.getMainLooper())
    private var searchRunnable: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initView()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        subscribeLiveData()


        binding.searchBar.addTextChangedListener(object : TextWatcher {
            private val debounceTime: Long = 500
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()


                searchRunnable?.let { handler.removeCallbacks(it) }


                searchRunnable = Runnable {
                    if (query.length >=1 ) {
                        viewModel.searchDrugs(query)
                    }
                }


                handler.postDelayed(searchRunnable!!, debounceTime)
            }
        })
    }

    fun initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        binding.searchRecyclerview.adapter = adapter
    }

    fun subscribeLiveData() {
        viewModel.searchResults.observe(this) { results ->
            adapter.updateData(results)
        }

        viewModel.error.observe(this) { errorMsg ->
            Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show()
        }
    }
}