package com.veberod.happyapp.feature_register.presentation.components

import android.content.Context
import android.util.Patterns
import android.widget.Toast
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
import com.veberod.happyapp.feature_admin.domain.model.User
import com.veberod.happyapp.feature_admin.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Composable
fun Register(context: Context) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var age by remember { mutableStateOf(0) }
    val userRepository = UserRepository(context)



    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxSize()) {

        TextField(value = firstName,
            onValueChange = { firstName = it },
            label = { Text(text = "First Name") })


        TextField(value = lastName,
            onValueChange = { lastName = it },
            label = { Text(text = "Last Name") })


        TextField(value = username,
            onValueChange = { username = it },
            label = { Text(text = "Username") })


        TextField(value = email, onValueChange = { email = it }, label = { Text(text = "Email") })


        TextField(value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") })


        TextField(value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text(text = "Confirm Password") })

/*        Text(text = "Gender")
        var selectedGender = Gender.MALE
        GenderSelection(selectedOption = selectedGender, onOptionSelected = { newGender -> selectedGender = newGender})*/
    //TODO
        TextField(value = gender,
            onValueChange = { gender = it },
            label = { Text(text = "Gender") })


        Text(text = "Age")
        Slider(
            value = age.toFloat(),
            onValueChange = { age = it.toInt() },
            valueRange = 0f..100f
        )
        Text("Age: $age")

        Button(onClick = {
            if (isValidEmail(email) && isValidPassword(password) && password == confirmPassword) {
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
                    userRepository.addUser(user, context)
            }
        }) {
            Text(text = "Register")
        }

    }
}

fun isValidEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun isValidPassword(password: String): Boolean {
    // Add your own password validation rules here
    return password.length >= 6
}

suspend fun showToast(context: Context, message: String) {
    withContext(Dispatchers.Main) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
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





