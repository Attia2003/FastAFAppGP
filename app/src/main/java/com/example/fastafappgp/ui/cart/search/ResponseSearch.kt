package com.example.fastafappgp.ui.cart.search

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ResponseSearch(

	@field:SerializedName("ResponseSearch")
	val responseSearch: List<ResponseSearchItem?>? = null
) : Parcelable

@Parcelize
data class Location(

	@field:SerializedName("latitude")
	val latitude: String? = null,

	@field:SerializedName("longitude")
	val longitude: String? = null
) : Parcelable

@Parcelize
data class ResponseSearchItem(

	@field:SerializedName("expiryDate")
	val expiryDate: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("addedBy")
	val addedBy: AddedBy? = null,

	@field:SerializedName("price")
	val price: Float? = null,

	@field:SerializedName("pharmacy")
	val pharmacy: Pharmacy? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("stock")
	val stock: Int? = null,

	@field:SerializedName("drug")
	val drug: Drug? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
) : Parcelable

@Parcelize
data class AddedBy(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("phone")
	val phone: Int? = null,

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

@Parcelize
data class ShiftsItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("startTime")
	val startTime: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("endTime")
	val endTime: String? = null
) : Parcelable

@Parcelize
data class CreatedBy(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("phone")
	val phone: Int? = null,

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

@Parcelize
data class Drug(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("form")
	val form: String? = null,

	@field:SerializedName("createdBy")
	val createdBy: CreatedBy? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("units")
	val units: Int? = null,

	@field:SerializedName("fullPrice")
	val fullPrice: Float? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
) : Parcelable

@Parcelize
data class Owner(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("phone")
	val phone: Int? = null,

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

@Parcelize
data class Pharmacy(

	@field:SerializedName("owner")
	val owner: Owner? = null,

	@field:SerializedName("isBranch")
	val isBranch: Boolean? = null,

	@field:SerializedName("expiryThreshold")
	val expiryThreshold: Int? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("shifts")
	val shifts: List<ShiftsItem?>? = null,

	@field:SerializedName("location")
	val location: Location? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("mainBranch")
	val mainBranch: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
) : Parcelable
