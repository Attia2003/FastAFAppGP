package com.example.fastafappgp.ui.order.previeworder

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fastafappgp.apimanager.ApiManager
import com.example.fastafappgp.ui.login.pharmacy.PharmacyRepository
import kotlinx.coroutines.launch




class PreViewViewModel : ViewModel() {
    private val _previewItems = MutableLiveData<List<PreviewItem>>(emptyList())
    val previewItems: LiveData<List<PreviewItem>> = _previewItems

    val orderName = MutableLiveData<String>("")
    private val _event = MutableLiveData<PreViewOrderEvent>()
    val event: LiveData<PreViewOrderEvent> = _event

    companion object {
        private var instance: PreViewViewModel? = null

        fun getInstance(): PreViewViewModel {
            if (instance == null) {
                instance = PreViewViewModel()
            }
            return instance!!
        }
    }

    fun addPreviewItem(item: PreviewItem) {
        val currentList = _previewItems.value?.toMutableList() ?: mutableListOf()
        currentList.add(item)
        Log.d("PreViewViewModel", "Adding item: ${item.drugName}, Amount: ${item.amount}")
        Log.d("PreViewViewModel", "Current list size: ${currentList.size}")
        Log.d("PreViewViewModel", "Current list: ${currentList.map { "${it.drugName} (${it.amount})" }}")
        _previewItems.value = currentList
    }

    fun setPreviewItems(items: List<PreviewItem>) {
        Log.d("PreViewViewModel", "Setting ${items.size} items")
        Log.d("PreViewViewModel", "Items: ${items.map { "${it.drugName} (${it.amount})" }}")
        _previewItems.value = items
    }

    fun clearPreviewItems() {
        Log.d("PreViewViewModel", "Clearing preview items")
        _previewItems.value = emptyList()
    }

    fun uploadOrder() {
        val name = orderName.value ?: ""
        val items = previewItems.value ?: emptyList()
        if (name.isBlank()) {
            _event.value = PreViewOrderEvent.Error("Order name required")
            return
        }
        if (items.isEmpty()) {
            _event.value = PreViewOrderEvent.Error("No items in order")
            return
        }
        _event.value = PreViewOrderEvent.Loading
        viewModelScope.launch {
            try {
                val pharmacyId = PharmacyRepository.getPharmacyIdSafely()
                if (pharmacyId == null) {
                    _event.value = PreViewOrderEvent.Error("Pharmacy ID not found")
                    return@launch
                }

                val orderItems = items.map { item ->
                    Log.d("PreViewViewModel", "Creating order item with drugId: ${item.drugId}")
                    if (item.drugId == 0) {
                        Log.e("PreViewViewModel", "Invalid drug ID (0) for item: ${item.drugName}")
                        null
                    } else {
                        OrderItemRequest(
                            drugId = item.drugId,
                            required = item.amount
                        )
                    }
                }.filterNotNull()
                
                if (orderItems.isEmpty()) {
                    _event.value = PreViewOrderEvent.Error("No valid items to order")
                    return@launch
                }
                val orderRequest = OrderRequest(name, orderItems)
                val api = ApiManager().getWebService()
                val response = api.uploadOrder(pharmacyId, orderRequest)
                if (response.isSuccessful) {
                    _event.value = PreViewOrderEvent.Success
                } else {
                    _event.value = PreViewOrderEvent.Error("Failed: ${response.code()} ${response.message()}")
                }
            } catch (e: Exception) {
                _event.value = PreViewOrderEvent.Error(e.message ?: "Unknown error")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        instance = null
    }
}


