package com.example.fastafappgp.ui.cart

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fastafappgp.apimanager.ApiManager
import com.example.fastafappgp.ui.login.pharmacy.PharmacyRepository
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {

    private val api = ApiManager().getWebService()

    private val _cartItems = MutableLiveData<List<ResponseDrugReceiopts>>()
    val cartItems: LiveData<List<ResponseDrugReceiopts>> get() = _cartItems

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchCartItems(selectedIds: List<Int>) {
        viewModelScope.launch {
            try {
                val pharmacyId = PharmacyRepository.getPharmacyIdSafely()
                if (pharmacyId != null) {
                    val response = api.getReceiptDetails(pharmacyId, selectedIds)
                    if (response.isSuccessful && response.body() != null) {
                        _cartItems.postValue(response.body())
                    } else {
                        _error.postValue("Failed: ${response.message()}")
                    }
                } else {
                    _error.postValue("Pharmacy ID is null.")
                }
            } catch (e: Exception) {
                _error.postValue("Error: ${e.localizedMessage}")
            }
        }
    }

}