package com.ashish.groceease.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.ashish.groceease.db.CartItem
import com.ashish.groceease.viewModel.CartViewModel
import com.ashish.groceease.viewModel.SharedViewModel
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(viewModel: SharedViewModel,navController: NavHostController) {
    val product=viewModel.product
    val cartViewModel: CartViewModel = hiltViewModel()
    val scope = rememberCoroutineScope()
    val isItemInCart by cartViewModel.isExist(product?._id!!).collectAsState(initial = null)
    if(product!=null){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            AsyncImage(
                model = product.image,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RoundedCornerShape(24.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(12.dp))

            Text(text= product.name!!, style = MaterialTheme.typography.titleLarge,modifier = Modifier.padding(12.dp))
            Text(text = product.category?.name!!, style = MaterialTheme.typography.titleMedium)
            Text(text = "Rs ${ product.price.toString() }", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text(text= "Product detail", style = MaterialTheme.typography.titleLarge,modifier = Modifier.padding(12.dp))
            Text(text= product.description!!, style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    when (isItemInCart) {
                        null -> scope.launch {
                            cartViewModel.insertProduct(CartItem(product._id!!,product.name,product.price!!,product.image!!,1))
                        }
                        else -> {
                            navController.popBackStack()
                            navController.navigate("cart")

                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                var text  by rememberSaveable { mutableStateOf("") }
                text = when (isItemInCart) {
                    null -> "Add to cart"
                    else -> "Goto cart"
                }
                Text(
                    text=text
                )
            }
        }
    }
    else{
        Text(text="Loading...")
    }
}