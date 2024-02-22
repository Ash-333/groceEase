package com.ashish.groceease.model


data class Product(
	val image: String? = null,
	val price: Int? = null,
	val name: String? = null,
	val _id: String? = null,
	val category: Categories? = null,
	val description:String?=null
)

data class Categories(
	val _id:String?=null,
	val name:String?=null
)

