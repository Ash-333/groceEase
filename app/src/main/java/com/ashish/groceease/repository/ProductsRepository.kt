package com.ashish.groceease.repository

import android.util.Log
import com.ashish.groceease.api.ProductApi
import com.ashish.groceease.model.Product
import com.ashish.groceease.utils.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class ProductsRepository @Inject constructor(private val productApi: ProductApi) {
    private val _product= MutableStateFlow<NetworkResult<List<Product>?>?> (null)
    val product: StateFlow<NetworkResult<List<Product>?>?>
        get()=_product

    suspend fun getAllProduct(){
        Log.d("API CALL","made call to api")
        _product.emit(NetworkResult.Loading())
        val response=productApi.getAllProducts()
        handleResponse(response)
    }

    suspend fun getProductByName(name:String){
        _product.emit(NetworkResult.Loading())
        val response=productApi.getProductByName(name)
        handleResponse(response)
    }
    suspend fun getProductByCategory(category:String){
        _product.emit(NetworkResult.Loading())
        val response=productApi.getProductByCategory(category)
        handleResponse(response)
    }

    private suspend fun handleResponse(response: Response<List<Product>>){
        if(response.isSuccessful && response.body()!=null){
            Log.d("RESPONSE",response.body().toString())
            _product.emit(NetworkResult.Success(response.body()))
        }
        else if(response.errorBody()!=null){
            val errorMsg= JSONObject(response.errorBody()!!.charStream().readText())
            Log.d("RESPONSE",errorMsg.toString())
            _product.emit(NetworkResult.Error(errorMsg.getString("msg")))
        }
        else{
            _product.emit(NetworkResult.Error("Something went wrong"))
        }
    }
}