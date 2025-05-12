package com.example.fastafappgp.ui.exchange.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fastafappgp.apimanager.ApiManager
import com.example.fastafappgp.util.SingleLiveEvent
import kotlinx.coroutines.launch

class ExchangeDetailsViewModel : ViewModel() {
    private val api = ApiManager().getWebService()

    private val _receiptDetails = MutableLiveData<ResponseDetailsExchange?>()
    val receiptDetails: LiveData<ResponseDetailsExchange?> = _receiptDetails

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    val event = SingleLiveEvent<EventNavigateDetails>()

    fun getReceiptDetails(receiptId: Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = api.getReceiptDetails(receiptId)
                if (response.isSuccessful) {
                    _receiptDetails.value = response.body()
                } else {

                    val errorBody = response.errorBody()?.string()
                    _error.value = "Failed to load receipt details: ${response.code()} - $errorBody"

                    android.util.Log.e("ExchangeDetails", "API error: $errorBody")
                }
            } catch (e: Exception) {
                _error.value = "Error: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun onBackPressed() {
        event.postValue(EventNavigateDetails.NavigateBack)
    }
}