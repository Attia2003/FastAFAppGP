package com.example.fastafappgp.ui.exchange.details

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ResponseDetailsExchange(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("total")
	val total: Float? = null,

	@field:SerializedName("revenue")
	val revenue: Float? = null,

	@field:SerializedName("cashier")
	val cashier: Cashier? = null,

	@field:SerializedName("shift")
	val shift: Shift? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("items")
	val items: List<ItemsItem?>? = null,

	@field:SerializedName("profit")
	val profit: Float? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
) : Parcelable

@Parcelize
data class EndTime(

	@field:SerializedName("hour")
	val hour: Int? = null,

	@field:SerializedName("nano")
	val nano: Int? = null,

	@field:SerializedName("minute")
	val minute: Int? = null,

	@field:SerializedName("second")
	val second: Int? = null
) : Parcelable

@Parcelize
data class StartTime(

	@field:SerializedName("hour")
	val hour: Int? = null,

	@field:SerializedName("nano")
	val nano: Int? = null,

	@field:SerializedName("minute")
	val minute: Int? = null,

	@field:SerializedName("second")
	val second: Int? = null
) : Parcelable

@Parcelize
data class ItemsItem(

	@field:SerializedName("amountDue")
	val amountDue: Float? = null,

	@field:SerializedName("drugName")
	val drugName: String? = null,

	@field:SerializedName("discount")
	val discount: Float? = null,

	@field:SerializedName("units")
	val units: Int? = null,

	@field:SerializedName("pack")
	val pack: Int? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable

@Parcelize
data class Shift(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("startTime")
	val startTime: StartTime? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("endTime")
	val endTime: EndTime? = null
) : Parcelable

@Parcelize
data class Cashier(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("managedUser")
	val managedUser: Boolean? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("fbUser")
	val fbUser: Boolean? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
) : Parcelable
