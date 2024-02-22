package com.ashish.groceease.model

data class Cart(
    val userId: String,
    val items: List<CartItem>,
    val total: Double,
)

data class CartItem(
    val productId: String,
    val quantity: Int,
    val price: Double
)


