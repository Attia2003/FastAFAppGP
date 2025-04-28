package com.example.fastafappgp.ui.cart.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fastafappgp.apimanager.ApiManager
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val searchApi = ApiManager().getWebService()


    private val _searchResults = MutableLiveData<List<ResponseSearchItem>>()
    val searchResults: LiveData<List<ResponseSearchItem>> get() = _searchResults

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun searchDrugs(name: String, page: Int = 0, size: Int = 20) {
        viewModelScope.launch {
            try {
                val response = searchApi.getsearch(name, page, size)
                if (response.isSuccessful && response.body() != null) {
                    _searchResults.postValue(response.body() ?: emptyList())
                } else {
                    _error.postValue("Error: ${response.code()} ${response.message()}")
                }
            } catch (e: Exception) {
                _error.postValue("Exception: ${e.message}")
            }
        }
    }

}