package com.ashish.groceease.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
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
import com.ashish.groceease.components.CheckOutItems
import com.ashish.groceease.model.Address
import com.ashish.groceease.model.Items
import com.ashish.groceease.model.Order
import com.ashish.groceease.utils.NetworkResult
import com.ashish.groceease.utils.TokenManager
import com.ashish.groceease.viewModel.AddressViewModel
import com.ashish.groceease.viewModel.CartViewModel
import com.ashish.groceease.viewModel.OrderViewModel

@Composable
fun CheckoutScreen() {
    val context=LocalContext.current
    val cartViewModel: CartViewModel = hiltViewModel()
    val addressViewModel: AddressViewModel = hiltViewModel()
    val tokenManager = remember { TokenManager(context) }
    val orderViewModel: OrderViewModel = hiltViewModel()
    val cartState by cartViewModel.cart.collectAsState()
    val addressState by addressViewModel.address.collectAsState()
    val userId=tokenManager.getUserId()
    val totalSum = cartState.sumOf { cartItem -> cartItem.quantity * cartItem.productPrice }
    val items: List<Items> = cartState.map { cartItem ->
        Items(
            productId = cartItem.productId,
            productName = cartItem.productName,
            quantity = cartItem.quantity,
            price = cartItem.productPrice,
            productImg = cartItem.productImage
        )
    }


    Column(
        modifier=Modifier.padding(8.dp)
    ) {
        Text(text = "Order Summary")

        LazyColumn{
            item{
                when (val result = addressState) {
                    is NetworkResult.Loading -> {
                        CircularProgressIndicator()
                    }
                    is NetworkResult.Success -> {
                        val address:List<Address>?=result.data
                        val currentAddress= address?.get(0)
                        Address(address = currentAddress!!)
                    }
                    is NetworkResult.Error -> {
                        val errorMessage = (addressState as NetworkResult.Error<List<Address>?>).message
                        Log.d("error",errorMessage.toString())
                    }

                    else -> {
                        CircularProgressIndicator()
                    }
                }
            }
            items(cartState){cartItem->

                CheckOutItems(item = cartItem)
            }
            item{
                Button(onClick = {
                    if(addressState is NetworkResult.Success){
                        val address:List<Address>?= (addressState as NetworkResult.Success<List<Address>?>).data
                        val currentAddress= address?.get(0)
                        placeOrder(orderViewModel,totalSum,currentAddress?._id!!,userId!!,items)
                        cartViewModel.deleteAllProduct()
                    }

                    Log.d("ORDER","order placed")
                },modifier= Modifier
                    .fillMaxWidth()
                    .padding(8.dp)) {
                    Text(text="Checkout", style = MaterialTheme.typography.titleLarge)
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        cartViewModel.getAllProducts()
        addressViewModel.getAllAddress(userId!!)
    }
}

@Composable
fun Address(address: Address) {
    Column(
        modifier=Modifier.fillMaxWidth()
    ) {
        Row(
            modifier=Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(text = "Delivery to")
            Text(text = "Edit", modifier = Modifier.clickable {  })
        }
        Text(text = address.fullName!!)
        Text(text = address.phoneNumber!!)
        Text(text = "${ address.addressLine1!! }, ${address.city!!}")
    }
}

fun placeOrder(orderViewModel: OrderViewModel,totalSum:Int,addressId:String,userId:String,items:List<Items>){
    val order= Order(totalSum,addressId,userId,items)
    orderViewModel.makeOrder(order)
}