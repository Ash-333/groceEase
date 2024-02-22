package com.ashish.groceease.screen

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ashish.groceease.navigation.BottomBarItem
import com.ashish.groceease.navigation.HomeNavGraph

@Composable
fun HomeScreen(navController: NavHostController = rememberNavController()) {
    Scaffold(
        bottomBar = {
            BottomBar(navController)
        }
    ) {paddingValues ->
        HomeNavGraph(navController,paddingValues)
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens= listOf(
        BottomBarItem.Main,
        BottomBarItem.Cart,
        BottomBarItem.Profile
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = screens.any { it.route == currentDestination?.route }

    if (bottomBarDestination){
        NavigationBar {
            screens.forEach{screen->
                AddItem(screen=screen,currentDestination=currentDestination,navController=navController)
            }
        }
    }
}

@Composable
fun RowScope.AddItem(screen: BottomBarItem,currentDestination: NavDestination?,navController: NavHostController) {
    NavigationBarItem(
        label = {
            Text(text=screen.title)
        },
        icon = {
            Icon(imageVector=screen.icon,contentDescription = screen.title)
        },
        selected = currentDestination?.hierarchy?.any{
            it.route==screen.route
        }==true,
        onClick = {
            navController.navigate(screen.route){
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop=true
            }
        }
    )
}
