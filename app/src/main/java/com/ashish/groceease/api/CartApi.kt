package com.ashish.groceease.api

import com.ashish.groceease.model.Cart
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CartApi {
    @GET("api/cart/{userId}")
    suspend fun getCart(@Path("userId") userId:String):Response<Cart>

    @POST("api/cart")
    suspend fun addToCart(cart:Cart):Response<Cart>

}