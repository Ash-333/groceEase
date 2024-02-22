package com.ashish.groceease.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ashish.groceease.R
import com.ashish.groceease.navigation.AuthScreen
import com.ashish.groceease.navigation.Graph
import com.ashish.groceease.utils.Constant.SPLASH_SCREEN_DELAY_MS
import com.ashish.groceease.utils.TokenManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(navHostController: NavHostController,tokenManager: TokenManager) {
    val coroutineScope = rememberCoroutineScope()
    DisposableEffect(Unit) {
        coroutineScope.launch {
            if (tokenManager.getToken() == null) {
                navHostController.navigate(AuthScreen.AuthenticationScreen.route)
            } else {
                navHostController.popBackStack()
                navHostController.navigate(Graph.HOME)
            }
            delay(SPLASH_SCREEN_DELAY_MS)
        }
        onDispose { /* Clean up, if needed */ }
    }
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
        Image(
            painter = painterResource(id = R.drawable.shopping),
            contentDescription = "splash",
            modifier = Modifier
                .height(350.dp)
                .padding(8.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "GroceEase", fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.titleLarge)
    }
}