package com.example.fastafappgp.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fastafappgp.apimanager.ApiManager
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel() {
    val drugDetails = MutableLiveData<ResponseDetails>()
    val error = MutableLiveData<String>()

    fun fetchDetails(drugId: Int) {
        viewModelScope.launch {
            try {
                val response = ApiManager().getWebService().getdetails(drugId)
                if (response.isSuccessful) {
                    drugDetails.value = response.body()
                } else {
                    error.value = response.errorBody()?.string() ?: "Unknown error"
                }
            } catch (e: Exception) {
                error.value = e.message
            }
        }
    }
}