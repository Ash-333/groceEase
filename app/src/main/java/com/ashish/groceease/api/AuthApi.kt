package com.ashish.groceease.api

import com.ashish.groceease.model.ForgotPassword
import com.ashish.groceease.model.MessageResponse
import com.ashish.groceease.model.UserLogin
import com.ashish.groceease.model.UserRegister
import com.ashish.groceease.model.UserResponse
import com.ashish.groceease.model.ResetPassword
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("api/register")
    suspend fun register(@Body user:UserRegister): Response<UserResponse>

    @POST("api/login")
    suspend fun login(@Body user:UserLogin):Response<UserResponse>

    @POST("api/forget-password")
    suspend fun forgetPassword(@Body email:ForgotPassword):Response<MessageResponse>

    @POST("api/reset-password")
    suspend fun resetPassword(@Body password: ResetPassword):Response<MessageResponse>
}