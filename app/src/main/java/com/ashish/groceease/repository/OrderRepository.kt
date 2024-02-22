package com.ashish.groceease.repository

import android.util.Log
import com.ashish.groceease.api.OrderApi
import com.ashish.groceease.model.Order
import com.ashish.groceease.utils.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class OrderRepository @Inject constructor(private val orderApi:OrderApi) {
    private val _order= MutableStateFlow<NetworkResult<List<Order>?>?> (null)
    val order:StateFlow<NetworkResult<List<Order>?>?>
        get() =_order

    suspend fun getOrders(userId:String){
        _order.emit(NetworkResult.Loading())
        val response=orderApi.getOrder(userId)
        handleResponse(response)
    }

    suspend fun getOrderById(orderId:String){
        _order.emit(NetworkResult.Loading())
        val response=orderApi.getOrderById(orderId)
        handleResponseOf(response)
    }

    suspend fun makeOrder(order:Order){
        _order.emit(NetworkResult.Loading())
        val response=orderApi.order(order)
        //handleResponse(response)
    }


    private suspend fun handleResponse(response: Response<List<Order>>){
        if(response.isSuccessful && response.body()!=null){
            Log.d("RESPONSE",response.body().toString())
            _order.emit(NetworkResult.Success(response.body()))
        }
        else if(response.errorBody()!=null){
            val errorMsg= JSONObject(response.errorBody()!!.charStream().readText())
            Log.d("RESPONSE",errorMsg.toString())
            _order.emit(NetworkResult.Error(errorMsg.getString("msg")))
        }
        else{
            _order.emit(NetworkResult.Error("Something went wrong"))
        }
    }

    private suspend fun handleResponseOf(response: Response<Order>){
        if(response.isSuccessful && response.body()!=null){
            val orderList = listOf(response.body()!!)
            Log.d("RESPONSE",response.body().toString())
            _order.emit(NetworkResult.Success(orderList))
        }
        else if(response.errorBody()!=null){
            val errorMsg= JSONObject(response.errorBody()!!.charStream().readText())
            Log.d("RESPONSE",errorMsg.toString())
            _order.emit(NetworkResult.Error(errorMsg.getString("msg")))
        }
        else{
            _order.emit(NetworkResult.Error("Something went wrong"))
        }
    }
}