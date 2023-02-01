package com.veberod.happyapp.feature_register.presentation.components

import android.widget.Spinner
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
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
    isAdmin: MutableState<Boolean>
) {
// First Name
    TextField(
        value = firstName.value,
        onValueChange = { firstName.value = it },
        label = { Text("First Name") },
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    )
    // Last Name
    TextField(
        value = lastName.value,
        onValueChange = { lastName.value = it },
        label = { Text("Last Name") },
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    )

// Username
    TextField(
        value = username.value,
        onValueChange = { username.value = it },
        label = { Text("Username") },
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    )

// Email
    TextField(
        value = email.value,
        onValueChange = { email.value = it },
        label = { Text("Email") },
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    )

// Password
    TextField(
        value = password.value,
        onValueChange = { password.value = it },
        label = { Text("Password") },
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        obscureText = true
    )

// Confirm Password
    TextField(
        value = confirmPassword.value,
        onValueChange = { confirmPassword.value = it },
        label = { Text("Confirm Password") },
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        obscureText = true
    )

// Gender
    Spinner(
        selectedItem = gender.value,
        onSelectionChange = { gender.value = it },
        items = Gender.values().toList(),
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        dropdownModifier = Modifier.fillMaxWidth()
    )

// Age
    TextField(
        value = age.value.toString(),
        onValueChange = { age.value = it.toIntOrNull() ?: 0 },
        keyboardType = KeyboardType.Number,
        label = { Text("Age") },
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    )

// Is Admin
    Checkbox(
        checked = isAdmin.value,
        onCheckedChange = { isAdmin.value = it },
        label = { Text("Is Admin") },
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    )
}

