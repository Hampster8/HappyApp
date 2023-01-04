package com.veberod.happyapp.feature_register.presentation.components

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.veberod.happyapp.NavRoutes
import com.veberod.happyapp.feature_admin.domain.model.User
import com.veberod.happyapp.feature_admin.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun Register(context: Context, navController: NavHostController) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var age by remember { mutableStateOf(0) }
    val userRepository = UserRepository(context)
    val scope = CoroutineScope(Dispatchers.Default)


    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxSize()) {

        TextField(value = firstName,
            onValueChange = { firstName = it.trim() },
            label = { Text(text = "First Name") })


        TextField(value = lastName,
            onValueChange = { lastName = it.trim() },
            label = { Text(text = "Last Name") })


        TextField(value = username,
            onValueChange = { username = it.trim() },
            label = { Text(text = "Username") })


        TextField(value = email, onValueChange = { email = it.trim().lowercase() }, label = { Text(text = "Email") })


        TextField(value = password,
            onValueChange = { password = it.trim() },
            label = { Text(text = "Password") })


        TextField(value = confirmPassword,
            onValueChange = { confirmPassword = it.trim() },
            label = { Text(text = "Confirm Password") })

/*        Text(text = "Gender")
        var selectedGender = Gender.MALE
        GenderSelection(selectedOption = selectedGender, onOptionSelected = { newGender -> selectedGender = newGender})*/

        TextField(value = gender,
            onValueChange = { gender = it.trim() },
            label = { Text(text = "Gender") })


        Text(text = "Age")
        Slider(
            value = age.toFloat(),
            onValueChange = { age = it.toInt() },
            valueRange = 0f..100f
        )
        Text("Age: $age")

        Button(onClick = {
            if (ValidationUtils.isValidEmail(email) && ValidationUtils.isValidPassword(password) && password == confirmPassword) {
                val user = User(
                    userId = 0,
                    firstName = firstName,
                    lastName = lastName,
                    username = username,
                    gender = gender,
                    age = age,
                    email = email,
                    password = password
                )
                scope.launch {
                    if(userRepository.addUser(user, context)){
                        navController.navigate(NavRoutes.Smilies.route)
                    }

                }
            }
        }) {
            Text(text = "Register")
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





