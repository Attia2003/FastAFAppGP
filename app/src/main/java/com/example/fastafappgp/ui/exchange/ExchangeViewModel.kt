package com.example.fastafappgp.ui.exchange

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fastafappgp.apimanager.ApiManager
import com.example.fastafappgp.ui.login.pharmacy.PharmacyRepository
import kotlinx.coroutines.launch

class ExchangeViewModel : ViewModel() {
    private val api = ApiManager().getWebService()

    private val _receipts = MutableLiveData<List<ResponseExchange>?>()
    val receipts: LiveData<List<ResponseExchange>?> = _receipts

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    init {
        getReceipts()
    }

    fun getReceipts(receiptId: Int? = null) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val pharmacyId = PharmacyRepository.getPharmacyIdSafely()
                if (pharmacyId != null) {
                    val response = api.getreceipts(
                        receiptId = receiptId,
                        drugId = null,
                        todate = null,
                        fromdate = null,
                        cashierId = null,
                        page = 0,
                        size = 30
                    )

                    if (response.isSuccessful) {
                        val receiptsList = response.body()
                        Log.d("ExchangeViewModel", "API Response: $receiptsList")

                        if (!receiptsList.isNullOrEmpty()) {
                            _receipts.value = receiptsList
                            Log.d("ExchangeViewModel", "Posted ${receiptsList.size} receipts")
                        } else {
                            _receipts.value = emptyList()
                            Log.d("ExchangeViewModel", "No receipts found")
                        }
                    } else {
                        _error.value = "Failed to load receipts: ${response.code()}"
                        Log.e("ExchangeViewModel", "Failed to load receipts: ${response.code()}")
                    }
                } else {
                    _error.value = "Pharmacy ID is null"
                    Log.e("ExchangeViewModel", "Pharmacy ID is null")
                }
            } catch (e: Exception) {
                val errorMsg = "Error: ${e.localizedMessage}"
                _error.value = errorMsg
                Log.e("ExchangeViewModel", errorMsg, e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}