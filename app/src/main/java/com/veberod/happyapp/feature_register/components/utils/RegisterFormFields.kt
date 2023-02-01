package com.veberod.happyapp.feature_register.components.utils


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Checkbox
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.veberod.happyapp.database.domain.model.Gender

@Composable
fun RegisterFormFields(
    firstName: MutableState<String>,
    lastName: MutableState<String>,
    username: MutableState<String>,
    email: MutableState<String>,
    password: MutableState<String>,
    confirmPassword: MutableState<String>,
    gender: MutableState<Gender>,
    age: MutableState<Int>,
    isAdmin: MutableState<Boolean> = remember { mutableStateOf(false) }
) {

    Column {
        TextField(
            value = firstName.value,
            onValueChange = { firstName.value = it.trim() },
            label = { Text(text = "First Name") },
            singleLine = true
        )

        TextField(
            value = lastName.value,
            onValueChange = { lastName.value = it.trim() },
            label = { Text(text = "Last Name") },
            singleLine = true
        )

        TextField(
            value = username.value,
            onValueChange = { username.value = it.trim() },
            label = { Text(text = "Username") },
            singleLine = true
        )

        TextField(
            value = email.value,
            onValueChange = { email.value = it.trim().lowercase() },
            label = { Text(text = "Email") },
            singleLine = true
        )

        TextField(
            value = password.value,
            onValueChange = { password.value = it.trim() },
            label = { Text(text = "Password") },
            singleLine = true
        )

        TextField(
            value = confirmPassword.value,
            onValueChange = { confirmPassword.value = it.trim() },
            label = { Text(text = "Confirm Password") },
            singleLine = true
        )

        // Gender
        GenderDropdownMenu(selected = gender.value, onSelection = { selectedGender ->
            gender.value = selectedGender
        })


    Text(text = "Age")
    Slider(
        value = age.value.toFloat(),
        onValueChange = { age.value = it.toInt() },
        valueRange = 0f..100f,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
    Text(text = "${age.value}")

    Checkbox(
        checked = isAdmin.value,
        onCheckedChange = { isAdmin.value = it },
    )
    Text("Is Admin")

}}

@Composable
fun GenderDropdownMenu(selected: Gender, onSelection: (Gender) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Column {
        Text(selected.toString(), Modifier.clickable(onClick = { expanded = !expanded }))
        if (expanded) {
            Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState())) {
                Gender.values().forEach { gender ->
                    Text(gender.toString(), Modifier.clickable(onClick = {
                        expanded = false
                        onSelection(gender)
                    }))
                }
            }
        }
    }
}





