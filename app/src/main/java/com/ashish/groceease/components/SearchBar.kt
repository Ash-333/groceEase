package com.ashish.groceease.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ashish.groceease.model.Product
import com.ashish.groceease.utils.NetworkResult
import com.ashish.groceease.viewModel.ProductViewModel
import com.ashish.groceease.viewModel.SharedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(navController: NavHostController, sharedViewModel: SharedViewModel) {
    val productViewModel:ProductViewModel=hiltViewModel()
    val productState by productViewModel.product.collectAsState()
    var text by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }
    DockedSearchBar(
        query = text,
        onQueryChange = { text = it },
        onSearch = {
            productViewModel.getProductByName(text)
        },
        active = active,
        onActiveChange = {
            active = it
        },
        placeholder= { Text(text="Search") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        trailingIcon = {
            if (active) {
                IconButton(onClick = {
                    if(text.isNotEmpty()) text="" else active=false
                }) {
                    Icon(Icons.Filled.Close, contentDescription = null)
                }
            }
        }
    ) {
        // Search result shown when active
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            when (productState) {
                is NetworkResult.Loading -> {
                    Log.d("LOAD","loading...")
                }
                is NetworkResult.Success -> {
                    items((productState as NetworkResult.Success<List<Product>?>).data!!) { product ->
                        ProductItem(product = product,navController,sharedViewModel)
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
    }
}




