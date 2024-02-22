package com.ashish.groceease.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ashish.groceease.components.OrderItem
import com.ashish.groceease.utils.NetworkResult
import com.ashish.groceease.utils.TokenManager
import com.ashish.groceease.viewModel.OrderViewModel

@Composable
fun OrderScreen(navController: NavHostController) {
    val context= LocalContext.current
    val orderViewModel:OrderViewModel= hiltViewModel()
    val tokenManager= remember { TokenManager(context) }
    val orderState by orderViewModel.order.collectAsState()
    val userId=tokenManager.getUserId()


    Column{
        Text(text="All Orders",modifier=Modifier.padding(12.dp), style = MaterialTheme.typography.titleLarge)

        when (val currentState = orderState) {
            is NetworkResult.Loading -> {
                CircularProgressIndicator()
            }
            is NetworkResult.Success -> {
                val data = currentState.data
                if (data != null) {
                    LazyColumn {
                        items(data) { order ->
                            OrderItem(orderItem = order, navController)
                        }
                    }
                } else {
                    // Handle the case where data is null
                }
            }
            is NetworkResult.Error -> {
                val errorMessage = currentState.message
                Text("Error loading products: $errorMessage")
            }
            else -> {
                CircularProgressIndicator()
            }
        }
    }

    LaunchedEffect(userId){
        orderViewModel.getOrder(userId!!)
    }
}