package com.ashish.groceease.api

import com.ashish.groceease.model.Order
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderApi {
    @GET("/api/orders/{userId}")
    suspend fun getOrder(@Path("userId") userId:String):Response<List<Order>>

    @GET("api/order/{orderId}")
    suspend fun getOrderById(@Path("orderId") orderId:String):Response<Order>

    @POST("api/order")
    suspend fun order(@Body order: Order):Response<Void>
}