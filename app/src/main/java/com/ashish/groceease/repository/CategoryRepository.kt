package com.ashish.groceease.repository

import android.util.Log
import com.ashish.groceease.api.CategoryApi
import com.ashish.groceease.model.Category
import com.ashish.groceease.model.Product
import com.ashish.groceease.utils.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class CategoryRepository @Inject constructor(private val categoryApi: CategoryApi) {
    private val _category= MutableStateFlow<NetworkResult<List<Category>?>?> (null)
    val category: StateFlow<NetworkResult<List<Category>?>?>
        get()=_category

    suspend fun getAllCategory(){
        _category.emit(NetworkResult.Loading())
        val response=categoryApi.getAllCategory()
        handleResponse(response)
    }

    suspend fun getCategoryById(id:String){
        _category.emit(NetworkResult.Loading())
        val response=categoryApi.getCategoryById(id)
        handleResponse(response)
    }

    private suspend fun handleResponse(response: Response<List<Category>>){
        if(response.isSuccessful && response.body()!=null){
            Log.d("RESPONSE",response.body().toString())
            _category.emit(NetworkResult.Success(response.body()))
        }
        else if(response.errorBody()!=null){
            val errorMsg= JSONObject(response.errorBody()!!.charStream().readText())
            Log.d("RESPONSE",errorMsg.toString())
            _category.emit(NetworkResult.Error(errorMsg.getString("msg")))
        }
        else{
            _category.emit(NetworkResult.Error("Something went wrong"))
        }
    }
}