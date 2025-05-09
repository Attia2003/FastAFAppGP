package com.example.fastafappgp.ui.expiry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fastafappgp.apimanager.ApiManager
import com.example.fastafappgp.ui.cart.search.ResponseSearchItem
import com.example.fastafappgp.ui.login.pharmacy.PharmacyRepository
import kotlinx.coroutines.launch

class ExpiryViewModel : ViewModel() {

    private val api = ApiManager().getWebService()
    
    private val _searchResults = MutableLiveData<List<ResponseSearchItem>>()
    val searchResults: LiveData<List<ResponseSearchItem>> = _searchResults

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _currentFilter = MutableLiveData(ExpiryFilter.NOT_EXPIRED)
    val currentFilter: LiveData<ExpiryFilter> = _currentFilter

    private var currentQuery: String = ""

    fun setFilter(filter: ExpiryFilter) {
        _currentFilter.value = filter
        performSearch(currentQuery)
    }

    fun performSearch(query: String = "") {
        currentQuery = query
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val pharmacyId = PharmacyRepository.getPharmacyIdSafely()
                if (pharmacyId != null) {
                    val response = api.getsearch(
                        pharmacyId = pharmacyId,
                        name = query,
                        page = 0,
                        size = 20,
                        expired = if (_currentFilter.value == ExpiryFilter.EXPIRED) "true" else null,
                        approachingExpiry = if (_currentFilter.value == ExpiryFilter.APPROACHING_EXPIRY) "true" else null,
                        notexpired = if (_currentFilter.value == ExpiryFilter.NOT_EXPIRED) "true" else null
                    )
                    
                    if (response.isSuccessful) {
                        _searchResults.value = response.body()
                    } else {
                        _error.value = "Failed to fetch results: ${response.message()}"
                    }
                } else {
                    _error.value = "Failed to fetch Pharmacy ID"
                }
            } catch (e: Exception) {
                _error.value = "Exception: ${e.localizedMessage}"
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}

