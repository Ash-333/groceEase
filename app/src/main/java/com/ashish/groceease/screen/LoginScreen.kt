package com.ashish.groceease.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import com.ashish.groceease.R
import com.ashish.groceease.model.UserLogin
import com.ashish.groceease.utils.NetworkResult
import com.ashish.groceease.utils.TokenManager
import com.ashish.groceease.viewModel.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(tokenManager: TokenManager,onClick: () -> Unit,onRegister: () -> Unit,onForgot: () -> Unit) {
    val login: AuthViewModel = hiltViewModel()
    val context = LocalContext.current
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val text1 = buildAnnotatedString {
        append("Don't have account? ")
        pushStringAnnotation("register", "register")
        pushStyle(
            style = SpanStyle(
                color = Color.Black,
                textDecoration = TextDecoration.Underline
            )
        )
        append("Register here")
        pop()
    }

    val forgot = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = Color.Black,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append("Forgot password?")
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(22.dp)
            .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Welcome Back",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Sign to continue",
                color = Color.Gray,
                fontSize = 22.sp,
                modifier = Modifier.padding(2.dp),
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        Image(
            painter = painterResource(id = R.drawable.login),
            contentDescription = "",
            modifier = Modifier
                .height(350.dp)
                .padding(8.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Go
            ),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        )
        ClickableText(
            text = forgot, onClick = {
                onForgot()
            },
            style = TextStyle(textAlign = TextAlign.Right)
        )
        Spacer(modifier = Modifier.height(12.dp))



        errorMessage?.let { error ->
            Text(
                text = error,
                color = Color.Red,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(8.dp)
            )
        }
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.size(48.dp))
        }
        Button(
            onClick = {
                isLoading = true
                login.login(UserLogin(email, password))
                val networkResult = login.user
                login.viewModelScope.launch {
                    networkResult.collect { result ->
                        when (result) {
                            is NetworkResult.Success -> {
                                val userResponse = result.data
                                if (userResponse != null) {
                                    tokenManager.saveToken(userResponse.token!!)
                                    tokenManager.saveUserId(userResponse.user._id!!)
                                    tokenManager.saveName(userResponse.user.name!!)
                                    onClick()
                                    Log.d("USER", userResponse.user.toString())
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Something went wrong",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }

                            is NetworkResult.Error -> {
                                // Handle the error case
                                errorMessage = result.message
                            }

                            is NetworkResult.Loading -> {
                                isLoading = true
                            }

                            else -> {
                                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                        isLoading = false
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Text(text = "Login", modifier = Modifier.padding(4.dp))
        }

        Spacer(modifier = Modifier.height(4.dp))

        ClickableText(text = text1, onClick = { offSet ->
            text1.getStringAnnotations("register", offSet, offSet).firstOrNull()?.let {
               onRegister()
            }

        })
    }
}

