package com.ashish.groceease.model

data class User(
    var _id       : String?  = null,
    var name     : String?  = null,
    var email    : String?  = null,
    var isAdmin  : Boolean? = null,
    var password : String?  = null,
    var fcmToken:String?=null
)
