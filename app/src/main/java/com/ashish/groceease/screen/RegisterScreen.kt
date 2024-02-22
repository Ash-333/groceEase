package com.ashish.groceease.screen

import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import com.ashish.groceease.R
import com.ashish.groceease.model.UserRegister
import com.ashish.groceease.utils.NetworkResult
import com.ashish.groceease.utils.TokenManager
import com.ashish.groceease.viewModel.AuthViewModel
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(tokenManager: TokenManager,onClick: () -> Unit,onLogin:()->Unit) {
    val register: AuthViewModel = hiltViewModel()
    val context= LocalContext.current
    var email by rememberSaveable { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val text1= buildAnnotatedString {
        append("Already have an account?")
        pushStringAnnotation("login","login")
        pushStyle(
            style = SpanStyle(
                color=Color.Black,
                textDecoration = TextDecoration.Underline
            )
        )
        append("Login here")
        pop()
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier=Modifier.padding(22.dp)
    ) {

        Text(
            text = "Create Account",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Create a new account",
            color = Color.Gray,
            fontSize = 18.sp,
            modifier = Modifier.padding(2.dp),
        )
        Spacer(modifier = Modifier.height(12.dp))


        Image(
            painter = painterResource(id = R.drawable.login),
            contentDescription = "",
            modifier = Modifier
                .height(250.dp)
                .padding(8.dp)
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            maxLines=1,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            maxLines=1,
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
            maxLines=1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password,imeAction = ImeAction.Go),
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
        if(isLoading){
            CircularProgressIndicator(modifier = Modifier.size(48.dp))
        }

        Button(onClick = {
            isLoading=true

            FirebaseMessaging.getInstance().token
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        if (task.result != null && !TextUtils.isEmpty(task.result)) {
                            val fcm= task.result!!
                            val user=UserRegister(name, email, password,fcm)
                            Log.d("USER",user.toString())
                            register.register(UserRegister(name, email, password,fcm))
                        }
                    }
                }

            val networkResult = register.user
            register.viewModelScope.launch{
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
                                Toast.makeText(context,"Something went wrong Success",Toast.LENGTH_SHORT).show()
                            }
                        }

                        is NetworkResult.Error -> {
                            errorMessage = result.message
                        }

                        is NetworkResult.Loading -> {
                            isLoading=true
                        }

                        else -> {
                            isLoading=true
                            Toast.makeText(context,"Something went wrong else",Toast.LENGTH_SHORT).show()
                        }
                    }
                    isLoading=false

                }
            }
        },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)) {
            Text(text = "Register", modifier = Modifier.padding(4.dp))
        }

        Spacer(modifier = Modifier.height(4.dp))

        ClickableText(text = text1, onClick = {offSet->
            text1.getStringAnnotations("login",offSet,offSet).firstOrNull()?.let {
                onLogin()
            }

        },modifier=Modifier.align(Alignment.End))
    }
}
