package com.example.fastafappgp.ui.cart.search

import com.google.gson.annotations.SerializedName

data class ResponseSearch(

	@SerializedName("ResponseSearch")
	val responseSearch: List<ResponseSearchItem?>? = null
)

data class CreatedBy(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("phone")
	val phone: Any? = null,

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
)

data class ResponseSearchItem(

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
	val fullPrice: Any? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
