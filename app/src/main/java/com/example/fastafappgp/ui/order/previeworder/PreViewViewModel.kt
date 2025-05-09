package com.example.fastafappgp.ui.order.previeworder

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PreViewViewModel : ViewModel() {
    private val _previewItems = MutableLiveData<List<PreviewItem>>(emptyList())
    val previewItems: LiveData<List<PreviewItem>> = _previewItems

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

    override fun onCleared() {
        super.onCleared()
        instance = null
    }
}