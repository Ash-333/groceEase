package com.ashish.groceease.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ashish.groceease.screen.HomeScreen
import com.ashish.groceease.utils.TokenManager

@Composable
fun RootNavGraph(tokenManager: TokenManager,navController: NavHostController) {
    NavHost(navController = navController,route=Graph.ROOT, startDestination = Graph.AUTH){
        authNavGraph(tokenManager = tokenManager, navController = navController )
        composable(route=Graph.HOME){
            HomeScreen()
        }
    }
}

object Graph{
    const val ROOT="root_graph"
    const val AUTH="auth_graph"
    const val HOME="main_graph"
}