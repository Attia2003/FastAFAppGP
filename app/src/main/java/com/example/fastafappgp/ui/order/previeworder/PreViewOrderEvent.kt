package com.example.fastafappgp.ui.order.previeworder

sealed class PreViewOrderEvent {
    object Success : PreViewOrderEvent()
    data class Error(val message: String) : PreViewOrderEvent()
    object Loading : PreViewOrderEvent()
}
