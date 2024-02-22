package com.ashish.groceease.navigation

  import androidx.compose.foundation.layout.PaddingValues
  import androidx.compose.runtime.Composable
  import androidx.lifecycle.viewmodel.compose.viewModel
  import androidx.navigation.NavHostController
  import androidx.navigation.NavType
  import androidx.navigation.compose.NavHost
  import androidx.navigation.compose.composable
  import androidx.navigation.navArgument
  import com.ashish.groceease.screen.AddressScreen
  import com.ashish.groceease.screen.CartScreen
  import com.ashish.groceease.screen.CatProducts
  import com.ashish.groceease.screen.CheckoutScreen
  import com.ashish.groceease.screen.DetailScreen
  import com.ashish.groceease.screen.MainScreen
  import com.ashish.groceease.screen.OrderDetailScreen
  import com.ashish.groceease.screen.OrderScreen
  import com.ashish.groceease.screen.ProfileScreen
  import com.ashish.groceease.viewModel.SharedViewModel

@Composable
fun HomeNavGraph(navController:NavHostController, paddingValues: PaddingValues) {
    val sharedViewModel:SharedViewModel= viewModel()
    NavHost(navController = navController, route = Graph.HOME, startDestination =BottomBarItem.Main.route ){

        composable(route=BottomBarItem.Main.route){
            MainScreen(navController,sharedViewModel)
        }

        composable(route=BottomBarItem.Profile.route){
            ProfileScreen(navController)
        }

        composable(route=BottomBarItem.Cart.route){
            CartScreen(navController = navController)
        }
        composable(route="details"){
            DetailScreen(sharedViewModel,navController)
        }

        composable(route="catProducts/{id}",arguments=
        listOf(navArgument("id"){type= NavType.StringType})){
            val id=it.arguments?.getString("id")!!
            CatProducts(id,navController,sharedViewModel)
        }

        composable(route="checkout"){
            CheckoutScreen()
        }

        composable(route="orders"){
            OrderScreen(navController)
        }
        composable(route="orderDetail/{id}",arguments=listOf(navArgument("id"){type= NavType.StringType})){
            val orderId=it.arguments?.getString("id")!!
            OrderDetailScreen(orderId)
        }

        composable(route="addresses"){
            AddressScreen(navController = navController)
        }
    }
}