package com.ashish.groceease.screen

import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.ashish.groceease.model.ForgotPassword
import com.ashish.groceease.utils.NetworkResult
import com.ashish.groceease.viewModel.AuthViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(navHostController:NavHostController) {
    val forgotPassword: AuthViewModel = hiltViewModel()
    val context= LocalContext.current
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(22.dp).fillMaxSize()
    ) {
        var email by rememberSaveable { mutableStateOf("") }
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            maxLines=1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        )

        errorMessage?.let { error ->
            Text(
                text = error,
                color = Color.Red,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(8.dp)
            )
        }


        Button(
            onClick = {
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    errorMessage="Please enter valid email"
                }
                else if(TextUtils.isEmpty(email)){
                    errorMessage="Please enter an email"
                }

                forgotPassword.forgotPassword(ForgotPassword(email))
                val networkResult = forgotPassword.password
                forgotPassword.viewModelScope.launch{
                    networkResult.collect { result ->
                        when (result) {
                            is NetworkResult.Success -> {
                                val passwordData = result.data
                                if (passwordData != null) {
                                    successMessage=passwordData.msg
                                    navHostController.navigate("reset/$email")
                                } else {
                                    Toast.makeText(context,"Something went wrong", Toast.LENGTH_SHORT).show()
                                }
                            }

                            is NetworkResult.Error -> {
                                // Handle the error case
                                errorMessage = result.message
                            }

                            is NetworkResult.Loading -> {
                                isLoading=true
                            }

                            else -> {
                                isLoading=true
                                //Toast.makeText(context,"Something went wrong", Toast.LENGTH_SHORT).show()
                            }
                        }
                        isLoading=false

                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Text(text = "Send rest pin", modifier = Modifier.padding(4.dp))
        }
        successMessage?.let { error ->
            Text(
                text = error,
                color = Color.Green,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}