package com.ashish.groceease.model

data class UserResponse (
    var user  : User,
    var token : String? = null
)