package com.ashish.groceease.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarItem(
    val route:String,
    val title:String,
    val icon: ImageVector
){
    object Main: BottomBarItem(
        route="home",
        title="Main",
        icon= Icons.Default.Home
    )
    object Cart: BottomBarItem(
        route="cart",
        title="Cart",
        icon= Icons.Default.ShoppingCart
    )
    object Profile: BottomBarItem(
        route="profile",
        title="Profile",
        icon= Icons.Default.Person
    )

}
