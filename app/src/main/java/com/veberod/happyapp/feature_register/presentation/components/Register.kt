package com.veberod.happyapp.feature_register.presentation.components

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.veberod.happyapp.database.domain.repository.UserRepository
import com.veberod.happyapp.feature_register.components.utils.RegisterFormFields


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
    val userRepository = remember { UserRepository(context) }

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
        RegisterSubmitButton(context, navController, userRepository, firstName, lastName, username, email, password, confirmPassword, gender, age, isAdmin)
        RegisterBackButton(navController)
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





