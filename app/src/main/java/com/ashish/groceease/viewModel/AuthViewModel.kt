package com.ashish.groceease.viewModel

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashish.groceease.model.ForgotPassword
import com.ashish.groceease.model.MessageResponse
import com.ashish.groceease.model.UserLogin
import com.ashish.groceease.model.UserRegister
import com.ashish.groceease.model.UserResponse
import com.ashish.groceease.repository.UserRepository
import com.ashish.groceease.model.ResetPassword
import com.ashish.groceease.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val userRepository: UserRepository):ViewModel(){
    val user:StateFlow<NetworkResult<UserResponse?>?>
        get() = userRepository.userData

    val password:StateFlow<NetworkResult<MessageResponse?>?>
        get() = userRepository.passwordData

    fun login(user: UserLogin){
        viewModelScope.launch {
            userRepository.login(user)
        }
    }

    fun register(user:UserRegister){
        viewModelScope.launch {
            userRepository.register(user)
        }
    }

    fun forgotPassword(email:ForgotPassword){
        viewModelScope.launch {
            userRepository.forgotPassword(email)
        }
    }

    fun resetPassword(password: ResetPassword){
        viewModelScope.launch {
            userRepository.resetPassword(password)
        }
    }

    fun validateCredentials(name:String,email:String,password: String):Pair<Boolean,String>{
        var result=Pair(true,"")
         if(TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
             result=Pair(false,"Please prove Credentials")
         }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            result=Pair(false,"Please enter valid email address")
         }
        else if(password.length<=5){
            result= Pair(false,"Password length should be greater than 5")
         }
        return result
    }
}