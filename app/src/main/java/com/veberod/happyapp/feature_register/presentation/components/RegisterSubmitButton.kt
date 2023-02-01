package com.veberod.happyapp.feature_register.presentation.components

import android.content.Context
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.veberod.happyapp.NavRoutes
import com.veberod.happyapp.database.domain.model.Gender
import com.veberod.happyapp.database.domain.model.User
import com.veberod.happyapp.database.domain.repository.UserRepository
import com.veberod.happyapp.feature_register.components.utils.ValidationUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun RegisterSubmitButton(
    context: Context,
    navController: NavHostController,
    userRepository: UserRepository,
    firstName: MutableState<String>,
    lastName: MutableState<String>,
    username: MutableState<String>,
    email: MutableState<String>,
    password: MutableState<String>,
    confirmPassword: MutableState<String>,
    gender: MutableState<Gender>,
    age: MutableState<Int>,
    isAdmin: MutableState<Boolean>


) {
    Button(
        onClick = {
            if (ValidationUtils.isFormValid(
                    firstName.value,
                    lastName.value,
                    username.value,
                    email.value,
                    password.value,
                    confirmPassword.value,
                    gender.value,
                    isAdmin.value
                )
            ) {
                CoroutineScope(Dispatchers.Default).launch {
                    val user = User(
                        userId = 0,
                        firstName = firstName.value,
                        lastName = lastName.value,
                        username = username.value,
                        email = email.value,
                        password = password.value,
                        gender = gender.value,
                        age = age.value,
                        isAdmin = isAdmin.value
                    )
                    withContext(Dispatchers.IO) {
                        userRepository.addUser(user, context)
                    }
                    withContext(Dispatchers.Main) {
                        navController.navigate(NavRoutes.Login.route)
                    }
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
    ) {
        Text(text = "REGISTER")
    }
}
