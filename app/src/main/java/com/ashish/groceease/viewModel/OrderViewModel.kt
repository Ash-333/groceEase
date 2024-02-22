package com.ashish.groceease.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashish.groceease.model.Order
import com.ashish.groceease.repository.OrderRepository
import com.ashish.groceease.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(private val orderRepository:OrderRepository):ViewModel() {
    val order: StateFlow<NetworkResult<List<Order>?>?>
        get() = orderRepository.order

    fun getOrder(userId:String){
        viewModelScope.launch {
            orderRepository.getOrders(userId)
        }
    }

    fun getOrderById(orderId:String){
        viewModelScope.launch {
            orderRepository.getOrderById(orderId)
        }
    }

    fun makeOrder(order:Order){
        viewModelScope.launch {
            orderRepository.makeOrder(order)
        }
    }
}