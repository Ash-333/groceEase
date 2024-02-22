package com.ashish.groceease.screen

import android.util.Log
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ashish.groceease.components.CatProductItem
import com.ashish.groceease.model.Product
import com.ashish.groceease.utils.NetworkResult
import com.ashish.groceease.viewModel.ProductViewModel
import com.ashish.groceease.viewModel.SharedViewModel

@Composable
fun CatProducts(catId:String,navController: NavHostController,sharedViewModel: SharedViewModel) {
    val productViewModel: ProductViewModel = hiltViewModel()
    val productState by productViewModel.product.collectAsState()
    
    LazyVerticalGrid(columns = GridCells.Fixed(2) ){
        when (productState) {
            is NetworkResult.Loading -> {
                Log.d("LOAD","loading...")
            }
            is NetworkResult.Success -> {
                items((productState as NetworkResult.Success<List<Product>?>).data!!) { product ->
                    CatProductItem(product = product,navController,sharedViewModel)
                }
            }
            is NetworkResult.Error -> {
                val errorMessage = (productState as NetworkResult.Error<List<Product>?>).message
                Log.d("error",errorMessage.toString())
            }

            else -> {
                Log.d("LOAD","loading...")
            }
        }
    }
    LaunchedEffect(Unit) {
        productViewModel.getProductByCategory(catId)
    }
}