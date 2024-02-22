package com.ashish.groceease.repository

import android.util.Log
import com.ashish.groceease.api.AddressApi
import com.ashish.groceease.model.Address
import com.ashish.groceease.model.MessageResponse
import com.ashish.groceease.utils.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class AddressRepository @Inject constructor(private val addressApi: AddressApi) {
    private val _address= MutableStateFlow<NetworkResult<List<Address>?>?> (null)
    val address: StateFlow<NetworkResult<List<Address>?>?>
        get() =_address

    private val _addressData=MutableStateFlow<NetworkResult<MessageResponse?>?>(null)
    val addressData:StateFlow<NetworkResult<MessageResponse?>?>
        get() = _addressData

    suspend fun getAllAddress(userId:String){
        _address.emit(NetworkResult.Loading())
        val response=addressApi.getAllAddress(userId)

        handleResponse(response)
    }

    suspend fun getAddressById(addressId:String){
        _address.emit(NetworkResult.Loading())
        val response=addressApi.getAddressById(addressId)
        handleResponse(response)
    }

    suspend fun addNewAddress(address: Address){
        _address.emit(NetworkResult.Loading())
        val response=addressApi.addNewAddress(address)
        handleResponse(response)
    }

    suspend fun updateAddress(addressId:String,address: Address){
        _address.emit(NetworkResult.Loading())
        val response=addressApi.updateAddress(addressId,address)
        //handleResponse(response)
    }

    suspend fun deleteAddress(addressId: String){
        val response=addressApi.deleteAddress(addressId)
        handelErrors(response)
    }

    private suspend fun handleResponse(response: Response<List<Address>>){
        Log.d("Response",response.body().toString())
        if(response.isSuccessful && response.body()!=null){
            Log.d("RESPONSE",response.body().toString())
            _address.emit(NetworkResult.Success(response.body()))
        }
        else if(response.errorBody()!=null){
            val errorMsg= JSONObject(response.errorBody()!!.charStream().readText())
            Log.d("RESPONSE",errorMsg.toString())
            _address.emit(NetworkResult.Error(errorMsg.getString("msg")))
        }
        else{
            _address.emit(NetworkResult.Error("Something went wrong"))
        }
    }

    private suspend fun handelErrors(response: Response<MessageResponse>){
        if(response.isSuccessful && response.body()!=null){
            _addressData.emit(NetworkResult.Success(response.body()))
        }
        else if(response.errorBody()!=null){
            val errorMsg=JSONObject(response.errorBody()!!.charStream().readText())
            _addressData.emit(NetworkResult.Error(errorMsg.getString("msg")))
        }
        else{
            _addressData.emit(NetworkResult.Error("Something went wrong"))
        }
    }

}