package com.ashish.groceease.screen

import android.text.TextUtils
import android.util.Log
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.ashish.groceease.model.ResetPassword
import com.ashish.groceease.navigation.AuthScreen
import com.ashish.groceease.utils.NetworkResult
import com.ashish.groceease.viewModel.AuthViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetPasswordScreen(navHostController: NavHostController,resetEmail:String) {

    val resetPassword: AuthViewModel = hiltViewModel()
    val context= LocalContext.current
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(22.dp)
            .fillMaxSize()
    ){
        var pass1 by rememberSaveable { mutableStateOf("") }
        var pass2 by rememberSaveable { mutableStateOf("") }
        var code by rememberSaveable { mutableStateOf("") }

        OutlinedTextField(
            value = code,
            onValueChange = { code = it },
            label = { Text("Reset PIN") },
            maxLines=1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        )

        OutlinedTextField(
            value = pass1,
            onValueChange = { pass1 = it },
            label = { Text("Confirm password") },
            maxLines=1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        )

        OutlinedTextField(
            value = pass2,
            onValueChange = { pass2 = it },
            label = { Text("Confirm password") },
            maxLines=1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password,imeAction= ImeAction.Go),
            visualTransformation = PasswordVisualTransformation(),
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
                if(TextUtils.isEmpty(pass1) || TextUtils.isEmpty(pass2) ||TextUtils.isEmpty(code)){
                    errorMessage="Please enter all fields"
                }
                else if(pass1!=pass2){
                    errorMessage="Passwords did not matched"
                }
                else if(pass1.length<5){
                    errorMessage="Password should be at least 6 characters"
                }
                Log.d("CODE",resetEmail)
                resetPassword.resetPassword(ResetPassword(resetEmail,code,pass1))
                val networkResult = resetPassword.password
                resetPassword.viewModelScope.launch{
                    networkResult.collect { result ->
                        when (result) {
                            is NetworkResult.Success -> {
                                val passwordData = result.data
                                if (passwordData != null) {
                                    successMessage=passwordData.msg
                                    navHostController.navigate(AuthScreen.AuthenticationScreen.route)
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
                                Toast.makeText(context,"Something went wrong", Toast.LENGTH_SHORT).show()
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
            Text(text = "Change Password", modifier = Modifier.padding(4.dp))
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