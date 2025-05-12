package com.example.fastafappgp.ui.cart

data class ReceiptItem(
    val drugId: Int,
    val discount: Float = 0f,
    val units: Int,
    val packs: Int
) 