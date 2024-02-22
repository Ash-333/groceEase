package com.ashish.groceease.model

data class UserRegister(
    val name:String,
    val email:String,
    val password:String,
    val fcmToken:String
)
