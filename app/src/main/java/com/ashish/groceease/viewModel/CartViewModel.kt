package com.ashish.groceease.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashish.groceease.db.CartItem
import com.ashish.groceease.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val cartRepository: CartRepository):ViewModel() {
    val cart= MutableStateFlow<List<CartItem>>(emptyList())

    fun getAllProducts()=viewModelScope.launch {
        cartRepository.getAllProducts().catch {
            Log.d("Main",it.message.toString())
        }.collect{
            cart.value=it
        }
    }

    fun insertProduct(cart:CartItem){
        viewModelScope.launch {
            cartRepository.insertProduct(cart)
        }
    }

    fun updateQuantity(id:String,quantity:Int){
        viewModelScope.launch {
            cartRepository.updateQuantity(id,quantity)
        }
    }

    fun deleteProduct(cart:CartItem){
        viewModelScope.launch {
            cartRepository.deleteProduct(cart)
        }
    }

    fun deleteAllProduct(){
        viewModelScope.launch {
            cartRepository.deleteAllProduct()
        }
    }

    fun isExist(id:String): Flow<CartItem?> {
        return cartRepository.isExist(id)
    }
}