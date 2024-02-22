package com.ashish.groceease.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ashish.groceease.model.Order
import java.text.SimpleDateFormat

@Composable
fun OrderItem(orderItem: Order, navController: NavHostController) {
    val date= convertDateFormat(orderItem.createdAt!!)
    Log.d("ID",orderItem._id!!)
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clickable {
                       navController.navigate("orderDetail/${orderItem._id}")
            },
    ){
        Column(
            modifier=Modifier.padding(16.dp)
        ){
            Row(
                modifier=Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Row {
                    Text(text="Order Id: ", fontWeight = FontWeight.Bold)
                    Text(text = orderItem._id.takeLast(10))
                }
                Text(text = date)
            }

            Spacer(modifier=Modifier.height(4.dp))

            Row(
                modifier=Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Row {
                    Text(text="Amount: ", fontWeight = FontWeight.Bold)
                    Text(text = "Rs ${orderItem.total}")
                }
                Text(text = orderItem.status!!)
            }
        }
    }
}

fun convertDateFormat(inputDate: String): String {
    val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    val outputDateFormat = SimpleDateFormat("dd MMM yyyy")

    val parsedDate = inputDateFormat.parse(inputDate)
    return outputDateFormat.format(parsedDate)
}