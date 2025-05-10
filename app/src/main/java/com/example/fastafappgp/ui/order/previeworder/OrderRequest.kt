package com.example.fastafappgp.ui.order.previeworder

data class OrderItemRequest(
    val drugId: Int,
    val required: Int
)

data class OrderRequest(
    val name: String,
    val items: List<OrderItemRequest>
)

data class PreviewItemWithId(
    val drugId: Int,
    val drugName: String,
    val drugForm: String,
    val amount: Int
)