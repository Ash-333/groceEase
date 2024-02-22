package com.ashish.groceease.screen

import ImageSlider
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ashish.groceease.components.CategoryItem
import com.ashish.groceease.components.CategoryShimmerItem
import com.ashish.groceease.components.ProductItem
import com.ashish.groceease.components.ProductShimmerItem
import com.ashish.groceease.components.SearchBar
import com.ashish.groceease.model.Category
import com.ashish.groceease.model.Product
import com.ashish.groceease.utils.NetworkResult
import com.ashish.groceease.viewModel.CategoryViewModel
import com.ashish.groceease.viewModel.ProductViewModel
import com.ashish.groceease.viewModel.SharedViewModel

@Composable
fun MainScreen(navController: NavHostController,sharedViewModel: SharedViewModel) {
    val productViewModel: ProductViewModel = hiltViewModel()
    val categoryViewModel:CategoryViewModel= hiltViewModel()
    val categoryState by categoryViewModel.category.collectAsState()
    val productState by productViewModel.product.collectAsState()

    val images = listOf("https://media.npr.org/assets/img/2021/08/11/gettyimages-1279899488_wide-f3860ceb0ef19643c335cb34df3fa1de166e2761-s1100-c50.jpg",
        "https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492__480.jpg",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRrfPnodZbEjtJgE-67C-0W9pPXK8G9Ai6_Rw&usqp=CAU",
        "https://i.ytimg.com/vi/E9iP8jdtYZ0/maxresdefault.jpg",
        "https://cdn.shopify.com/s/files/1/0535/2738/0144/articles/shutterstock_149121098_360x.jpg")
    
    LazyColumn(
        modifier=Modifier.padding(8.dp)
    ){
        item {
            SearchBar(navController,sharedViewModel)
            Spacer(modifier = Modifier.height(12.dp))
            ImageSlider(images)
        }

        item{
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "Shop by Category")
            Spacer(modifier = Modifier.height(4.dp))
            Categories(categoryState = categoryState,navController)
        }

        item {
            Spacer(modifier = Modifier.height(12.dp))
            Row{
                Text(text = "Shop by Product")
            }
            Spacer(modifier = Modifier.height(4.dp))
        }

        when (productState) {
            is NetworkResult.Loading -> {
                item{
                    repeat(7){
                        ProductShimmerItem()
                    }
                }
            }
            is NetworkResult.Success -> {
                items((productState as NetworkResult.Success<List<Product>?>).data!!) { product ->
                    ProductItem(product = product,navController,sharedViewModel)
                }
            }
            is NetworkResult.Error -> {
                val errorMessage = (productState as NetworkResult.Error<List<Product>?>).message
                item{
                    Text(text = errorMessage.toString())
                }
            }

            else -> {
                Log.d("LOAD","loading...")
            }
        }

        item{
            Spacer(modifier = Modifier.height(72.dp))
        }

    }
}

@Composable
fun Categories(categoryState: NetworkResult<List<Category>?>?, navController: NavHostController) {
    when (categoryState) {
        is NetworkResult.Success -> {
            LazyRow {
                items(categoryState.data!!) { category ->
                    CategoryItem(category = category,navController)
                }
            }
        }
        is NetworkResult.Error -> {
            val errorMessage = categoryState.message
            Text("Error loading products: $errorMessage")
        }
        is NetworkResult.Loading -> {
            Row {
                repeat(7){
                    CategoryShimmerItem()
                }
            }
        }
        else ->{
            CircularProgressIndicator()
        }
    }
}


