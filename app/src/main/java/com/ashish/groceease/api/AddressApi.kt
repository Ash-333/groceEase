package com.ashish.groceease.api

import com.ashish.groceease.model.Address
import com.ashish.groceease.model.MessageResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AddressApi {
    @GET("api/{userId}/addresses")
    suspend fun getAllAddress(@Path("userId") userId:String):Response<List<Address>>

    @GET("api/addresses/{addressId}")
    suspend fun getAddressById(@Path("addressId") addressId:String):Response<List<Address>>

    @POST("api/addresses")
    suspend fun addNewAddress(@Body address: Address):Response<List<Address>>

    @PUT("api/addresses/{addressId}")
    suspend fun updateAddress(@Path("addressId") addressId: String,@Body address: Address):Response<Void>

    @DELETE("api/addresses/{addressId}")
    suspend fun deleteAddress(@Path("addressId") addressId: String):Response<MessageResponse>
}