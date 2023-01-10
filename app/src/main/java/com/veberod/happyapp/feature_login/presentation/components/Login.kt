package com.veberod.happyapp.feature_login.presentation.components

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.veberod.happyapp.NavRoutes
import com.veberod.happyapp.R
import com.veberod.happyapp.UserState
import com.veberod.happyapp.database.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun Login(context: Context, navController: NavHostController, userState: MutableState<UserState>) {

    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }


    val scope = CoroutineScope(Dispatchers.Main)

    fun login() {
        val userRepository = UserRepository(context)
        scope.launch {
            val user = withContext(Dispatchers.IO) {
                userRepository.getByUsernameAndPassword(username, password)
            }
            if (user != null) {
                userState.value = userState.value.copy(isLoggedIn = true, user = user, isAdmin = user.isAdmin)

                withContext(Dispatchers.Main) {
                    userRepository.showToast(context, "Login succeeded.")
                    navController.navigate(NavRoutes.Smilies.route)
                }
            } else {
                withContext(Dispatchers.Main) {
                    userRepository.showToast(context, "Login failed. Please try again.")
                }
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.60f)
            .background(MaterialTheme.colors.background)
            .padding(10.dp)
            .verticalScroll(rememberScrollState())

    ) {
        Box {
            Image(
                painterResource(R.drawable.smartabyahappyapplogo),
                contentDescription = "HappyApp Logo",
                modifier = Modifier
                    .padding(8.dp)
                    .size(150.dp)
            )
        }

        Text("Sign In")
        Spacer(modifier = Modifier.padding(20.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") }
            )
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                singleLine = true,
                placeholder = { Text("Password") },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Filled.Star
                    else Icons.Outlined.Star

                    // Localized description for accessibility services
                    val description = if (passwordVisible) "Hide password" else "Show password"

                    // Toggle button to hide or display password
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, description)
                    }
                })
            Button(onClick = {
                login()
/*                isLoggedIn.value = true*/
            }) {
                Text("Login")
            }
            Button(
                onClick = {
                    navController.navigate(NavRoutes.Register.route) {
                        popUpTo("login_page") {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .background(color = MaterialTheme.colors.primary)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    "Create An Account",
                    style = MaterialTheme.typography.body1,
                    color = Color.White
                )
            }
        }

    }
}

