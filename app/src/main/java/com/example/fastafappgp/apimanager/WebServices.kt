package com.example.fastafappgp.apimanager

import com.example.fastafappgp.ui.cart.search.ResponseSearch
import com.example.fastafappgp.ui.cart.search.ResponseSearchItem
import com.example.fastafappgp.ui.login.LoginRequest
import com.example.fastafappgp.ui.login.LoginResponse
import com.example.fastafappgp.ui.login.RefreshRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface WebServices {

    @Headers("Content-Type: application/json")
    @POST("/api/v1/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest ) : Response<LoginResponse>

    @POST("/api/v1/auth/refresh")
    suspend fun refreshToken(@Body refreshTokenRequest: RefreshRequest):Response<LoginResponse>

    @GET("/api/v1/drugs/search")
    suspend fun getsearch(
        @Query("name") name: String,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20
    ): Response<List<ResponseSearchItem>>

    @GET("/api/v1/drugs/{id}")
    suspend fun getdetails(@Query("id")id:Int):Response<>

}