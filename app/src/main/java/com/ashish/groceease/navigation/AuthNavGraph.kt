package com.ashish.groceease.navigation

import android.util.Log
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.ashish.groceease.screen.ForgotPasswordScreen
import com.ashish.groceease.screen.LoginScreen
import com.ashish.groceease.screen.RegisterScreen
import com.ashish.groceease.screen.ResetPasswordScreen
import com.ashish.groceease.screen.SplashScreen
import com.ashish.groceease.utils.TokenManager


fun NavGraphBuilder.authNavGraph(tokenManager: TokenManager, navController: NavHostController) {
    navigation(route=Graph.AUTH, startDestination = AuthScreen.SplashScreen.route){
        composable(AuthScreen.SplashScreen.route){
            SplashScreen(navController,tokenManager)
        }
        navigation(
            route= AuthScreen.AuthenticationScreen.route,
            startDestination = AuthScreen.AuthenticationScreen.RegisterScreen.route
        ){
            composable(AuthScreen.AuthenticationScreen.RegisterScreen.route){
                RegisterScreen(
                    tokenManager,
                    onClick={
                        navController.popBackStack()
                        navController.navigate(Graph.HOME)
                    },
                    onLogin={
                        navController.navigate(AuthScreen.AuthenticationScreen.LoginScreen.route)
                    }
                )
            }
            composable(AuthScreen.AuthenticationScreen.LoginScreen.route){
                LoginScreen(
                    tokenManager,
                    onClick={
                        navController.popBackStack()
                        navController.navigate(Graph.HOME)
                    },
                    onRegister={
                        navController.navigate(AuthScreen.AuthenticationScreen.RegisterScreen.route)
                    },
                    onForgot={
                        navController.navigate(AuthScreen.AuthenticationScreen.ForgotPassword.route)
                    }
                )
            }
            navigation(
                route= AuthScreen.AuthenticationScreen.ForgotPassword.route,
                startDestination =AuthScreen.AuthenticationScreen.ForgotPassword.ForgotPasswordScreen.route
            ){
                composable(AuthScreen.AuthenticationScreen.ForgotPassword.ForgotPasswordScreen.route){
                    ForgotPasswordScreen(navController)
                }
                composable(
                    AuthScreen.AuthenticationScreen.ForgotPassword.ResetPasswordScreen.route,arguments=
                    listOf(navArgument("email"){type= NavType.StringType})
                ){
                    val email=it.arguments?.getString("email")
                    Log.d("EMAIL",email!!)
                    ResetPasswordScreen(navController, email)
                }
            }
        }
    }
}
