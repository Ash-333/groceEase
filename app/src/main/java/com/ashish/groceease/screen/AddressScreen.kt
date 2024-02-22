package com.ashish.groceease.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ashish.groceease.components.AddressItem
import com.ashish.groceease.utils.NetworkResult
import com.ashish.groceease.utils.TokenManager
import com.ashish.groceease.viewModel.AddressViewModel

@Composable
fun AddressScreen(navController: NavHostController) {
    val context= LocalContext.current
    val addressViewModel:AddressViewModel= hiltViewModel()
    val addressStare by addressViewModel.address.collectAsState()
    val tokenManager= remember { TokenManager(context) }
    val userId=tokenManager.getUserId()
    var selectedAddressId by remember { mutableStateOf<String?>(null) }

    Column(
        modifier=Modifier.padding(8.dp)
    ){
        Text(text="All Addresses",modifier= Modifier.padding(12.dp), style = MaterialTheme.typography.titleLarge)

        Button(onClick = { /*TODO*/ },modifier=Modifier.fillMaxWidth()) {
            Text(text = "Add new address")
        }


        when (val currentState = addressStare) {
            is NetworkResult.Loading -> {
                CircularProgressIndicator()
            }
            is NetworkResult.Success -> {
                val data = currentState.data
                if (data != null) {
                    data.forEach { address -> 
                        AddressItem(
                            address = address,
                            navController = navController,
                            addressViewModel = addressViewModel,
                            selectedAddressId = selectedAddressId,
                            onAddressSelected = {
                                selectedAddressId=it
                            }
                        )    

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
        addressViewModel.getAllAddress(userId!!)
    }
}

//                    LazyColumn {
//                        items(data) { address ->
//                            AddressItem(address = address, navController =navController ,addressViewModel)
//                        }
//                    }