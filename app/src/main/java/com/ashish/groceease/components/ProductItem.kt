package com.ashish.groceease.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import com.ashish.groceease.model.Product
import com.ashish.groceease.viewModel.CartViewModel
import com.ashish.groceease.viewModel.SharedViewModel
import kotlinx.coroutines.launch

@Composable
fun ProductItem(product: Product,navController: NavHostController,sharedViewModel:SharedViewModel) {
    val cartViewModel:CartViewModel= hiltViewModel()
    val scope = rememberCoroutineScope()
    val isItemInCart by cartViewModel.isExist(product._id!!).collectAsState(initial = null)
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clickable {
                sharedViewModel.addProduct(product)
                navController.navigate("details")
            },
    ) {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier= Modifier.padding(8.dp)
            ) {

                AsyncImage(
                    model = product.image ?: "https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492__480.jpg",
                    contentDescription = null,
                    modifier = Modifier
                        .width(120.dp)
                        .height(120.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = product.name!!,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = product.category?.name!!,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                }

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,

                ) {
                OutlinedButton(
                    onClick={

                    },
                    modifier = Modifier.weight(1f)
                ){
                    Text(text = "Rs ${product.price.toString()}")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        when (isItemInCart) {
                            null -> scope.launch {
                                cartViewModel.insertProduct(CartItem(product._id!!,product.name!!,product.price!!,product.image!!,1))
                            }
                            else -> navController.navigate("cart")
                        }

                    },
                    modifier = Modifier.weight(1f)
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
    }
}
