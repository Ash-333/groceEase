package com.ashish.groceease.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class CartItem(
    @PrimaryKey
    val productId:String,
    val productName:String,
    val productPrice:Int,
    val productImage:String,
    val quantity:Int
)
