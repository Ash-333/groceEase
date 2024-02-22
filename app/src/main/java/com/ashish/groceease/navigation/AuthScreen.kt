package com.ashish.groceease.navigation

sealed class AuthScreen(val route:String){
    object SplashScreen: AuthScreen("splash")
    object AuthenticationScreen: AuthScreen("auth"){
        object RegisterScreen: AuthScreen("register")
        object LoginScreen: AuthScreen("login")
        object ForgotPassword: AuthScreen("forgot"){
            object ResetPasswordScreen: AuthScreen("reset/{email}")
            object ForgotPasswordScreen: AuthScreen("forgotPass")
        }
    }
}