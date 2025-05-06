package com.example.fastafappgp.ui.login.pharmacy

import com.example.fastafappgp.apimanager.ApiManager

object PharmacyRepository {

    private val api = ApiManager().getWebService()
    private var cachedPharmacyId: Int? = null

    suspend fun getPharmacyIdSafely(): Int? {
        if (cachedPharmacyId != null) return cachedPharmacyId
        val response = api.getPharmacyID()
        if (response.isSuccessful) {
            val id = response.body()?.firstOrNull()?.id
            cachedPharmacyId = id
            return id
        }
        return null
    }

    fun clearCache() {
        cachedPharmacyId = null
    }
}