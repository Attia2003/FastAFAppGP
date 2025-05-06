package com.example.fastafappgp.ui.cart.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fastafappgp.apimanager.ApiManager
import com.example.fastafappgp.ui.login.pharmacy.PharmacyRepository
import com.example.fastafappgp.ui.login.pharmacy.PharmacyRepository.getPharmacyIdSafely
import kotlinx.coroutines.launch


class SearchViewModel : ViewModel() {

    private val api = ApiManager().getWebService()

    private val _searchResults = MutableLiveData<List<Drug>>()
    val searchResults: LiveData<List<Drug>> get() = _searchResults

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val selectedDrugIds = mutableSetOf<Int>()
    fun searchDrugs(name: String, page: Int = 0, size: Int = 20) {
        viewModelScope.launch {
            try {
                val pharmacyId = PharmacyRepository.getPharmacyIdSafely()
                if (pharmacyId != null) {
                    val response = api.getsearch(pharmacyId, name, page, size)
                    if (response.isSuccessful && response.body() != null) {
                        val drugs = response.body()!!.mapNotNull { it.drug }
                        _searchResults.postValue(drugs)
                    } else {
                        _error.postValue("Search failed: ${response.code()} ${response.message()}")
                    }
                } else {
                    _error.postValue("Pharmacy ID is null.")
                }
            } catch (e: Exception) {
                _error.postValue("Exception: ${e.localizedMessage}")
            }
        }
    }

    fun fetchSelectedDrugsDetails() {
        viewModelScope.launch {
            try {
                val pharmacyId = getPharmacyIdSafely()
                if (pharmacyId != null) {
                    val selectedIds = getSelectedDrugIds()
                    val response = api.getReceiptDetails(pharmacyId, selectedIds)

                    if (response.isSuccessful) {
                        val details = response.body()
                        Log.d("DrugDetails", details.toString())
                    } else {
                        _error.postValue("Failed: ${response.message()}")
                    }
                } else {
                    _error.postValue("Failed to fetch Pharmacy ID.")
                }
            } catch (e: Exception) {
                _error.postValue("Exception: ${e.localizedMessage}")
                e.printStackTrace()
            }
        }
    }

    fun addSelectedDrugId(drugId: Int) {
        selectedDrugIds.add(drugId)
    }

    fun getSelectedDrugIds(): List<Int> {
        return selectedDrugIds.toList()
    }

}
