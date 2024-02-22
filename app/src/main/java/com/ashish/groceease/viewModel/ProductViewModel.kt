package com.ashish.groceease.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashish.groceease.model.Product
import com.ashish.groceease.repository.ProductsRepository
import com.ashish.groceease.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val productsRepository: ProductsRepository):ViewModel() {
    val product:StateFlow<NetworkResult<List<Product>?>?>
        get() = productsRepository.product

    init{
        getAllProducts()
    }

    fun getAllProducts(){
        viewModelScope.launch {
            productsRepository.getAllProduct()
        }
    }

    fun getProductByName(name:String){
        viewModelScope.launch {
            productsRepository.getProductByName(name)
        }
    }

    fun getProductByCategory(category:String){
        viewModelScope.launch {
            productsRepository.getProductByCategory(category)
        }
    }
}