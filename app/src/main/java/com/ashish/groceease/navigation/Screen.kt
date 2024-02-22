package com.ashish.groceease.navigation

sealed class Screen(val route:String){
    object HomeScreen: Screen("home")
    object MainScreen: Screen("main")
    object CartScreen: Screen("cart")
    object ProfileScreen: Screen("profile")
    object DetailScreen:Screen("detail/{id}")
}