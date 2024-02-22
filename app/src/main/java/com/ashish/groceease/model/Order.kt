package com.ashish.groceease.model

data class Order(
	val total: Int? = null,
	val shippingAddress: String? = null,
	val userId: String? = null,
	val items: List<Items?>? = null,
	val _id:String?=null,
	val createdAt:String?=null,
	val status:String?=null
)

data class Items(
	val quantity: Int? = null,
	val productId: String? = null,
	val price: Int? = null,
	val productImg: String? = null,
	val productName: String? = null
)

