package com.ashish.groceease.api

import com.ashish.groceease.model.Category
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoryApi {

    @GET("api/category")
    suspend fun getAllCategory(): Response<List<Category>>

    @GET("api/category/{catId}")
    suspend fun getCategoryById(@Path("catId") catId:String):Response<List<Category>>
}