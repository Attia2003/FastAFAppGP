package com.example.fastafappgp.ui.details

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ResponseDetails(

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
data class CreatedBy(

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("credentialsNonExpired")
	val credentialsNonExpired: Boolean? = null,

	@field:SerializedName("employee")
	val employee: Employee? = null,

	@field:SerializedName("authorities")
	val authorities: List<AuthoritiesItem?>? = null,

	@field:SerializedName("enabled")
	val enabled: Boolean? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("managedUser")
	val managedUser: Boolean? = null,

	@field:SerializedName("accountNonExpired")
	val accountNonExpired: Boolean? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("fbUser")
	val fbUser: Boolean? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null,

	@field:SerializedName("accountNonLocked")
	val accountNonLocked: Boolean? = null
) : Parcelable



@Parcelize
data class Employee(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("endDate")
	val endDate: String? = null,

	@field:SerializedName("shift")
	val shift: Shift? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("salary")
	val salary: Long? = null,

	@field:SerializedName("user")
	val user: String? = null,

	@field:SerializedName("age")
	val age: Int? = null,

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
data class AuthoritiesItem(

	@field:SerializedName("authority")
	val authority: String? = null
) : Parcelable
