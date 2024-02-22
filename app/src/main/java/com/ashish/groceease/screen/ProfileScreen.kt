package com.ashish.groceease.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ashish.groceease.navigation.AuthScreen
import com.ashish.groceease.utils.TokenManager

@Composable
fun ProfileScreen(navController: NavHostController) {
    val context= LocalContext.current
    val tokenManager = remember { TokenManager(context) }
    val userName=tokenManager.getUserName()
    Column(
        modifier = Modifier.padding(8.dp)
    ){
        Text(text = "Hello, $userName", style = MaterialTheme.typography.titleLarge,modifier=Modifier.padding(12.dp))
        Spacer(modifier = Modifier.height(24.dp))
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            modifier = Modifier.padding(4.dp)
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ){

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier=Modifier.clickable {
                        navController.navigate("orders")
                    }
                ) {
                    Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "orders")
                    Column {
                        Text(text = "Your")
                        Text(text = "Order")
                    }
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier=Modifier.clickable {
                        navController.navigate("addresses")
                    }
                ) {
                    Icon(imageVector = Icons.Default.LocationOn, contentDescription = "address")
                    Column {
                        Text(text = "Delivery")
                        Text(text = " Address")
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        ElevatedCard(

            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ){
            Column(
                modifier=Modifier.padding(8.dp)
            ) {
                NavigationRow(Icons.Default.PlayArrow, "Payment", onClick={})
                NavigationRow(Icons.Default.Share, "Share the app", onClick={})
                NavigationRow(Icons.Default.Info, "About us", onClick={})
                NavigationRow(Icons.Default.Star, "Rate us on Play Store", onClick={})
                NavigationRow(Icons.Default.List, "Terms and Conditions", onClick={})
                NavigationRow(Icons.Default.ExitToApp, "Logout", onClick={
                    tokenManager.removeUserId()
                    tokenManager.removeToken()
                    tokenManager.removeUserName()
                    navController.navigate(AuthScreen.AuthenticationScreen.LoginScreen.route)
                })
            }
        }
    }
}

@Composable
fun NavigationRow(icon: ImageVector, text: String,onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 16.dp)
            .clickable {
                       onClick.invoke()
            },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Icon(imageVector = icon, contentDescription = text)
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = text)
        }
        Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "next")
    }
}
