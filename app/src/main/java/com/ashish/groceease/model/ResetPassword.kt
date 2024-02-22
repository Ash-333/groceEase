package com.ashish.groceease.model

data class ResetPassword(
    val email:String,
    val token:String,
    val newPassword: String
)
