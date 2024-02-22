package com.ashish.groceease.repository

import android.util.Log
import com.ashish.groceease.api.AuthApi
import com.ashish.groceease.model.ForgotPassword
import com.ashish.groceease.model.MessageResponse
import com.ashish.groceease.model.UserLogin
import com.ashish.groceease.model.UserRegister
import com.ashish.groceease.model.UserResponse
import com.ashish.groceease.model.ResetPassword
import com.ashish.groceease.utils.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val authApi: AuthApi) {
    private val _userData=MutableStateFlow<NetworkResult<UserResponse?>?> (null)
    val userData:StateFlow<NetworkResult<UserResponse?>?>
        get()=_userData

    private val _passwordData=MutableStateFlow<NetworkResult<MessageResponse?>?>(null)
    val passwordData:StateFlow<NetworkResult<MessageResponse?>?>
        get() = _passwordData

    suspend fun register(user: UserRegister){
        _userData.emit(NetworkResult.Loading())
        val response=authApi.register(user)
        handleResponse(response)
    }

    suspend fun login(user: UserLogin){
        _userData.emit(NetworkResult.Loading())
        val response=authApi.login(user)
        handleResponse(response)
    }

    suspend fun forgotPassword(email:ForgotPassword){
        Log.d("FORGOT","FORGOT PASSWORD")
        val response=authApi.forgetPassword(email)
        Log.d("FORGOT","FORGOT PASSWORD EMAIL SENT")
        handelErrors(response)
    }

    suspend fun resetPassword(password: ResetPassword){
        val response=authApi.resetPassword(password)
        handelErrors(response)
    }

    private suspend fun handleResponse(response:Response<UserResponse>){
        if(response.isSuccessful && response.body()!=null){
            _userData.emit(NetworkResult.Success(response.body()))
        }
        else if(response.errorBody()!=null){
            val errorMsg=JSONObject(response.errorBody()!!.charStream().readText())
            _userData.emit(NetworkResult.Error(errorMsg.getString("msg")))
        }
        else{
            _userData.emit(NetworkResult.Error("Something went wrong"))
        }
    }

    private suspend fun handelErrors(response: Response<MessageResponse>){
        if(response.isSuccessful && response.body()!=null){
            _passwordData.emit(NetworkResult.Success(response.body()))
        }
        else if(response.errorBody()!=null){
            val errorMsg=JSONObject(response.errorBody()!!.charStream().readText())
            _passwordData.emit(NetworkResult.Error(errorMsg.getString("msg")))
        }
        else{
            _passwordData.emit(NetworkResult.Error("Something went wrong"))
        }
    }
}