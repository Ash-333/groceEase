package com.ashish.groceease.repository

import com.ashish.groceease.db.CartDao
import com.ashish.groceease.db.CartItem
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class CartRepository @Inject constructor(private val cartDao: CartDao) {
    suspend fun insertProduct(cart:CartItem){
        cartDao.insertProduct(cart)
    }

    suspend fun updateQuantity(id:String,quantity:Int){
        cartDao.updateQuantity(id, quantity)
    }

    suspend fun deleteProduct(cart:CartItem){
        cartDao.deleteProduct(cart)
    }

    suspend fun deleteAllProduct(){
        cartDao.deleteAllProduct()
    }
    fun getAllProducts():Flow<List<CartItem>>{
        return cartDao.getAllProducts()
    }

    fun isExist(id:String):Flow<CartItem?>{
        return cartDao.isExit(id)
    }
}