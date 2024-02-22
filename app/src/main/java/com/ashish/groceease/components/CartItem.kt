package com.ashish.groceease.components

import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ashish.groceease.db.CartItem
import com.ashish.groceease.viewModel.CartViewModel

@Composable
fun CartItem(item: CartItem, cartViewModel: CartViewModel) {
    Log.d("CART", item.toString())
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clickable {

            },
    ) {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(8.dp)
            ) {
                AsyncImage(
                    model = item.productImage,
                    contentDescription = null,
                    modifier = Modifier
                        .width(80.dp)
                        .height(80.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = item.productName,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Row(
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "RS ${item.productPrice}",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyMedium
                        )

                        ElevatedCard(
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 6.dp
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp)
                            ) {
                                Image(imageVector = Icons.Default.Add,
                                    contentDescription = "add",
                                    modifier = Modifier.clickable {
                                        increaseQuantity(
                                            item,
                                            cartViewModel
                                        )
                                    }.padding(end=2.dp)
                                )
                                Text(text = item.quantity.toString())
                                Image(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "add",
                                    modifier = Modifier
                                        .clickable {
                                            decreaseQuantity(
                                                item,
                                                cartViewModel
                                            )
                                        }
                                        .padding(start=2.dp)
                                )
                            }
                        }


                    }
                }

            }
        }
    }
}

fun increaseQuantity(cartItem: CartItem, cart: CartViewModel) {
    cart.updateQuantity(cartItem.productId, cartItem.quantity + 1)
}

fun decreaseQuantity(cartItem: CartItem, cart: CartViewModel) {
    if (cartItem.quantity < 1) {
        cart.deleteProduct(cartItem)
    }
    cart.updateQuantity(cartItem.productId, cartItem.quantity - 1)

}