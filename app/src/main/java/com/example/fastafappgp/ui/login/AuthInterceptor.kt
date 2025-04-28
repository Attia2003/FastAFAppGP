package com.example.fastafappgp.ui.login

import com.example.fastafappgp.apimanager.ApiManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val tokenManager: TokenManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        val accessToken = tokenManager.getAccessToken()
        if (!accessToken.isNullOrEmpty()) {
            request = request.newBuilder()
                .addHeader("Authorization", "Bearer $accessToken")
                .build()
        }

        val response = chain.proceed(request)

        if (response.code == 401) {
            response.close()

            val newAccessToken = runBlocking {
                refreshAccessToken(tokenManager.getRefreshToken())
            }

            if (!newAccessToken.isNullOrEmpty()) {
                tokenManager.saveAccessToken(newAccessToken)

                val newRequest = request.newBuilder()
                    .header("Authorization", "Bearer $newAccessToken")
                    .build()

                return chain.proceed(newRequest)
            }
        }

        return response
    }

    private suspend fun refreshAccessToken(refreshToken: String?): String? {
        if (refreshToken.isNullOrEmpty()) return null

        return try {
            val apiManager = ApiManager()
            val response = apiManager.getWebService().refreshToken(RefreshRequest(refreshToken))

            if (response.isSuccessful) {
                response.body()?.accessToken
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}