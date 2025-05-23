package com.example.fastafappgp.apimanager

import com.example.fastafappgp.ui.exchange.PaginatedResponse
import com.example.fastafappgp.ui.exchange.ResponseExchange
import ResponsePharmacyItem
import com.example.fastafappgp.ui.cart.ReceiptItem
import com.example.fastafappgp.ui.cart.ResponseDrugReceiopts
import com.example.fastafappgp.ui.cart.search.Drug
import com.example.fastafappgp.ui.cart.search.ResponseSearchItem
import com.example.fastafappgp.ui.details.ResponseDetails
import com.example.fastafappgp.ui.exchange.details.ResponseDetailsExchange
import com.example.fastafappgp.ui.login.LoginRequest
import com.example.fastafappgp.ui.login.LoginResponse
import com.example.fastafappgp.ui.login.RefreshRequest
import com.example.fastafappgp.ui.order.ResponseOrder
import com.example.fastafappgp.ui.order.ResponseOrderItem
import com.example.fastafappgp.ui.order.previeworder.OrderRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface WebServices {

    @Headers("Content-Type: application/json")
    @POST("/api/v1/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("/api/v1/auth/refresh")
    suspend fun refreshToken(@Body refreshTokenRequest: RefreshRequest): Response<LoginResponse>

    @GET("/api/v1/pharmacies/{id}/drugs/search")
    suspend fun getsearch(
        @Path("id") pharmacyId: Int,
        @Query("name") name: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("EXPIRED") expired: String?=null,
        @Query("APPROACHING_EXPIRY")approachingExpiry:String?=null,
        @Query("NOT_EXPIRED") notexpired: String?=null
    ): Response<List<ResponseSearchItem>>

    @GET("api/v1/pharmacies/{id}/drugs/bulk")
    suspend fun getReceiptDetails(
        @Path("id") id: Int , @Query("ids") ids: List<Int>): Response<List<ResponseDrugReceiopts>>

    @GET("/api/v1/drugs/{id}")
    suspend fun getdetails(@Path("id") id: Int): Response<ResponseDetails>



    @GET("/api/v1/users/pharmacy")
    suspend fun getPharmacyID(): Response<List<ResponsePharmacyItem>>


    @GET("/api/v1/pharmacies/{id}/shortage/info")
    suspend fun getShortage(@Path("id") id: Int): Response<List<ResponseOrderItem>>

    @POST("/api/v1/orders/")
    suspend fun uploadOrder(
        @Query("pharmacy_id") pharmacyId: Int,
        @Body order: OrderRequest
    ): Response<Unit>

//    @GET("api/vi/orders/all")
//    suspend fun getallorders(@Query("pharmacy_id") pharmacyId: Int ,
//                             @Query("page") page : Int,
//                             @Query("size") size: Int) : Response<>


    @GET("/api/v1/receipts/filter")
    suspend fun getreceipts(
        @Query("receipt_id") receiptId: Int? = null,
        @Query("drug_id") drugId: Int? = null,
        @Query("to_date") todate: String? = null,
        @Query("from_date") fromdate: String? = null,
        @Query("cashier_id") cashierId: Int? = null,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 30
    ): Response<List<ResponseExchange>>


    @POST("/api/v1/receipts")
    suspend fun uploadreceipts(
        @Query("pharmacy_id") pharmacyId: Int,
        @Body receiptItems: List<ReceiptItem>
    ): Response<Unit>

    @GET("api/v1/receipts/{receipt_id}")
    suspend fun getReceiptDetails(@Path("receipt_id") receiptId: Int) : Response<ResponseDetailsExchange>


}


