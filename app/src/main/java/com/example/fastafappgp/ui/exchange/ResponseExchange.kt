package com.example.fastafappgp.ui.exchange

import android.os.Parcelable
import com.example.fastafappgp.ui.details.Shift
import kotlinx.parcelize.Parcelize

@Parcelize
data class PaginatedResponse(
	val content: List<ResponseExchange>?,
	val totalElements: Int?,
	val totalPages: Int?,
	val size: Int?,
	val number: Int?
) : Parcelable

@Parcelize
data class ResponseExchange(
	val id: Int,
	val items: List<ReceiptItem>?,
	val cashier: Cashier?,
	val shift: Shift?,
	val total: Double?,
	val revenue: Double?,
	val profit: Double?,
	val status: String?,
	val createdAt: String?,
	val updatedAt: String?
) : Parcelable

@Parcelize
data class ReceiptItem(
	val drugName: String?,
	val units: Int?,
	val pack: Int?,
	val discount: Double?,
	val amountDue: Double?,
	val status: String?
) : Parcelable

@Parcelize
data class Cashier(
	val id: Int?,
	val name: String?,
	val username: String?,
	val email: String?,
	val phone: String?,
	val role: String?,
	val fbUser: Boolean?,
	val managedUser: Boolean?,
	val createdAt: String?,
	val updatedAt: String?
) : Parcelable