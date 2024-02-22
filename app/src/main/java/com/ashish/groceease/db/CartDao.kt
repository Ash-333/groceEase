package com.ashish.groceease.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Insert
    suspend fun insertProduct(product: CartItem)

    @Delete
    suspend fun deleteProduct(product: CartItem)

    @Query("DELETE FROM cart")
    suspend fun deleteAllProduct()

    @Query("SELECT * FROM cart")
    fun getAllProducts(): Flow<List<CartItem>>

    @Query("UPDATE cart SET quantity = :quantity WHERE productId = :id")
    suspend fun updateQuantity(id: String, quantity: Int)

    @Query("SELECT * FROM cart WHERE productId=:id")
    fun isExit(id:String):Flow<CartItem?>
}