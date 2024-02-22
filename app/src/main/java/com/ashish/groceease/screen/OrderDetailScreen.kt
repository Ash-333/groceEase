package com.ashish.groceease.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.ashish.groceease.model.Items
import com.ashish.groceease.model.Order
import com.ashish.groceease.utils.NetworkResult
import com.ashish.groceease.viewModel.OrderViewModel


@Composable
fun OrderDetailScreen(orderId: String) {
    val orderViewModel: OrderViewModel = hiltViewModel()
    val orderState by orderViewModel.order.collectAsState()

    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        Text(text = "Your order status")


        if (orderState is NetworkResult.Success) {
            val orders: List<Order>? = (orderState as NetworkResult.Success<List<Order>?>).data

            val orderStatus: String? = orders?.firstOrNull()?.status
            orderStatus?.let { order ->
                Row {
                    when (order) {
                        "Pending" -> OrderStatus(status = order, height = 0.25f)
                        "Shipped" -> OrderStatus(status = order, height = 0.5f)
                        "Delivered" -> OrderStatus(status = order, height = 1f)
                        "Cancelled" -> OrderStatus(status = order, height = 1f)
                    }
                    Spacer(modifier = Modifier.width(12.dp))

                    Column(
                        modifier = Modifier.height(300.dp),
                        verticalArrangement = Arrangement.SpaceEvenly

                    ) {
                        OrderStatusText(order = order, status = "Pending")
                        OrderStatusText(order, "Shipped")
                        OrderStatusText(order, "Delivered")
                        OrderStatusText(order, "Cancelled")
                    }
                }
            }
        }



        Text(text = "Order Items", style = MaterialTheme.typography.titleLarge)

        when (val currentState = orderState) {
            is NetworkResult.Success -> {
                val orders = currentState.data ?: emptyList()
                LazyColumn {
                    items(orders) { order ->
                        order.items?.let { OrderItems(it) }
                    }
                }
            }

            is NetworkResult.Error -> {
                CircularProgressIndicator()
            }

            else -> {}
        }
    }

    LaunchedEffect(Unit) {
        orderViewModel.getOrderById(orderId)
    }
}

@Composable
fun OrderStatus(status: String, height: Float) {
    val statusColor = when (status) {
        "Pending" -> Color.Yellow
        "Shipped" -> Color.Green
        "Delivered" -> Color.Blue
        "Cancelled" -> Color.Red
        else -> Color.Gray
    }

    Box(
        modifier = Modifier
            .background(Color.Gray)
            .height(300.dp)
            .width(8.dp)
    ) {
        Box(
            modifier = Modifier
                .background(statusColor)
                .fillMaxWidth()
                .fillMaxHeight(height)
        )
    }
}

@Composable
fun OrderStatusText(order: String, status: String) {
    val fontWeight = if (order == status) FontWeight.Bold else FontWeight.SemiBold
    val textStyle = if (order == status) MaterialTheme.typography.bodyLarge else MaterialTheme.typography.bodyMedium

    Text(
        text = status,
        fontWeight = fontWeight,
        style = textStyle
    )
}

@Composable
fun OrderItems(items: List<Items?>) {
    items.forEach { order ->
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
                        model = order?.productImg,
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
                            text = order?.productName!!,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "RS ${order.price}",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "X${order.quantity}",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }
    }
}
