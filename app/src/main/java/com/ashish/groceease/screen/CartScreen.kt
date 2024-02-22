package com.ashish.groceease.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ashish.groceease.components.CartItem
import com.ashish.groceease.viewModel.CartViewModel

@Composable
fun CartScreen(navController: NavHostController) {
    val cartViewModel:CartViewModel= hiltViewModel()
    val cartState by cartViewModel.cart.collectAsState()
    val totalSum = cartState.sumOf { cartItem -> cartItem.quantity * cartItem.productPrice }

    if(cartState.isEmpty()){
        Text(text="Cart is empty")
    }
    else{
        LazyColumn(
            modifier= Modifier.padding(8.dp)
        ){
            item{
                Text(text = "Checkout")
                Spacer(modifier = Modifier.height(16.dp))
            }
            items(cartState){cartItem->
                CartItem(item = cartItem,cartViewModel)
            }

            item{
                Spacer(modifier = Modifier.height(8.dp))
                ElevatedCard(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    )
                ){
                    Column(
                        modifier=Modifier.padding(12.dp)
                    ){
                        Text(text = "Bill details", fontWeight = FontWeight.Bold,style = MaterialTheme.typography.titleLarge)
                        Row(
                            modifier=Modifier.fillMaxWidth()
                        ){
                            Text(text = "Item total",modifier=Modifier.weight(1f))
                            Text(text = "Rs $totalSum",modifier=Modifier.weight(1f))
                        }
                        Row(
                            modifier=Modifier.fillMaxWidth()
                        ){
                            Text(text = "Delivery charge",modifier=Modifier.weight(1f))
                            Text(text = "Rs 100",modifier=Modifier.weight(1f))
                        }
                        Row(
                            modifier=Modifier.fillMaxWidth()
                        ){
                            Text(text = "Grand total",modifier=Modifier.weight(1f))
                            Text(text = "Rs ${totalSum+100}",modifier=Modifier.weight(1f))
                        }
                    }
                }

            }

            item{
                Spacer(modifier = Modifier.height(8.dp))
                ElevatedCard(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    )
                ){
                    Column(
                        modifier=Modifier.padding(12.dp)
                    ){
                        Text(text = "Cancellation Policy",style = MaterialTheme.typography.titleLarge)
                        Text(text = "Order cannot be cancelled once packed for delivery.")
                        Text(text = "In case of unexpected delays, a refund will be provided if applicable.")
                    }
                }
                Button(onClick = {
                    navController.navigate("checkout")
                },modifier=Modifier.fillMaxWidth().padding(8.dp)) {
                    Text(text="Checkout", style = MaterialTheme.typography.titleLarge)
                }
                Spacer(modifier = Modifier.height(72.dp))

            }
        }
    }

    LaunchedEffect(Unit) {
        cartViewModel.getAllProducts()
    }
    
}