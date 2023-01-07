package com.veberod.happyapp.feature_register.presentation.components

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.veberod.happyapp.NavRoutes
import com.veberod.happyapp.database.domain.model.User
import com.veberod.happyapp.database.domain.repository.UserRepository
import com.veberod.happyapp.feature_register.components.utils.RegisterFormFields
import com.veberod.happyapp.feature_register.components.utils.ValidationUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun Register(context: Context, navController: NavHostController) {
    val firstName = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val username = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }
    val gender = remember { mutableStateOf("") }
    val age = remember { mutableStateOf(0) }
    val isAdmin = remember { mutableStateOf(false) }
    val userRepository = UserRepository(context)
    val scope = CoroutineScope(Dispatchers.Default)

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RegisterFormFields(
            firstName,
            lastName,
            username,
            email,
            password,
            confirmPassword,
            gender,
            age,
            isAdmin
        )

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
                    scope.launch {
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
        Button(
            onClick = {
                navController.navigate(NavRoutes.Login.route) {
                    popUpTo("login_page") {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }
        ) {
            Text("Back", color = Color.White)
        }

    }
}


/*@Composable
fun GenderSelection(selectedOption: Gender, onOptionSelected: (Gender) -> Unit) {
    val radioOptions = listOf(Gender.MALE, Gender.FEMALE, Gender.NON_BINARY)
    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column {
            radioOptions.forEach { text ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = { onOptionSelected(text) }
                        )
                        .padding(horizontal = 16.dp)
                ) {
                    RadioButton(
                        selected = (text == selectedOption),
                        onClick = { onOptionSelected(text) }
                    )
                    Text("$text")
                }
            }
        }
    }
}*/





