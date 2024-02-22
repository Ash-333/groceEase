package com.ashish.groceease.api

import com.ashish.groceease.model.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApi {

    @GET("/api/product")
    suspend fun getAllProducts():Response<List<Product>>

    @GET("api/product/{catId}")
    suspend fun getProductByCategory(@Path("catId") catId:String):Response<List<Product>>

    @GET("api/product/search")
    suspend fun getProductByName(@Query("q") query:String):Response<List<Product>>
}