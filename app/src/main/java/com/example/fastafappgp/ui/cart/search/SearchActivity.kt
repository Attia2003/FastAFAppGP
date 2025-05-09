package com.example.fastafappgp.ui.cart.search

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
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
import com.example.fastafappgp.databinding.ActivitySearchBinding
import com.example.fastafappgp.ui.cart.CartActivity
import com.example.fastafappgp.ui.details.DetailsActivity

class SearchActivity : AppCompatActivity() {
    lateinit var binding: ActivitySearchBinding
    val viewModel: SearchViewModel by viewModels()

    val adapter = SearchRecAdapter(
        onInfoClick = { drugId ->
            openDetailActivity(drugId)
        },
        onDrugSelect = { drugId ->
            viewModel.addSelectedDrugId(drugId)
        }
    )

    private val handler = Handler(Looper.getMainLooper())
    private var searchRunnable: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initView()
        subscribeLiveData()


        binding.iconback.setOnClickListener {
            viewModel.fetchSelectedDrugsDetails()
            val intent = Intent(this, CartActivity::class.java)
            intent.putIntegerArrayListExtra(Constant.listidsreset, ArrayList(viewModel.getSelectedDrugIds()))
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.searchBar.addTextChangedListener(object : TextWatcher {
            private val debounceTime: Long = 300

            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                searchRunnable?.let { handler.removeCallbacks(it) }

                searchRunnable = Runnable {
                    if (query.length >= 1) {
                        viewModel.searchDrugs(query)
                    }
                }
                handler.postDelayed(searchRunnable!!, debounceTime)
            }
        })
    }

    private fun initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        binding.searchRecyclerview.adapter = adapter
    }

    private fun subscribeLiveData() {
        viewModel.searchResults.observe(this) { results ->
            Log.d("LiveData", "Search results updated: ${results.size} items")
            adapter.updateData(results)

            if (results.isEmpty()) {
                Toast.makeText(this, "No results found", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.error.observe(this) { errorMsg ->
            Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show()
        }
    }

    private fun openDetailActivity(drugId: Int) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(Constant.DetailsDrugID, drugId)
        startActivity(intent)
    }
}

