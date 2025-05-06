package com.example.fastafappgp.ui.order

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fastafappgp.apimanager.ApiManager
import com.example.fastafappgp.ui.login.pharmacy.PharmacyRepository
import kotlinx.coroutines.launch

class OrderViewModel : ViewModel() {

    private val apiService = ApiManager().getWebService()

    private val _shortageData = MutableLiveData<ResponseOrderItem>()
    val shortageData: LiveData<ResponseOrderItem> = _shortageData

    fun getShortageForCurrentPharmacy() {
        viewModelScope.launch {
            try {
                val pharmacyId = PharmacyRepository.getPharmacyIdSafely()
                if (pharmacyId != null) {
                    val response = apiService.getShortage(pharmacyId)
                    if (response.isSuccessful) {
                        _shortageData.value = response.body()
                    } else {
                        Log.e("OrderViewModel", "Shortage API error: ${response.errorBody()?.string()}")
                    }
                } else {
                    Log.e("OrderViewModel", "Pharmacy ID not found")
                }
            } catch (e: Exception) {
                Log.e("OrderViewModel", "Exception: ${e.message}")
            }
        }
    }

}