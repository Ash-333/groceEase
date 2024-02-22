package com.ashish.groceease

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.ashish.groceease.navigation.HomeNavGraph
import com.ashish.groceease.navigation.RootNavGraph
import com.ashish.groceease.ui.theme.GroceEaseTheme
import com.ashish.groceease.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var tokenManager: TokenManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        setContent {
            GroceEaseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    val navHostController = rememberNavController()
                    val userToken=tokenManager.getToken()

                    if (userToken != null) {
                        // User is authenticated, navigate to home
                        HomeNavGraph(navHostController, PaddingValues(0.dp))
                    } else {
                        // User is not authenticated, navigate to authentication
                        RootNavGraph(tokenManager, navHostController)
                    }
                }
            }
        }
    }
}

//        val navHostController = rememberNavController()
//
//        if (tokenManager.getToken() == null) {
//            navHostController.navigate(AuthScreen.AuthenticationScreen.route)
//        } else {
//            // Check if the backstack contains any destinations
//            if (navHostController.currentBackStackEntry?.destination?.route != Graph.HOME) {
//                navHostController.popBackStack()
//            }
//            navHostController.navigate(Graph.HOME)
//        }

//                    RootNavGraph(tokenManager = tokenManager, navController = rememberNavController() )
