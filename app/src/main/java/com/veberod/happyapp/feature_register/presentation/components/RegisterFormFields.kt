package com.veberod.happyapp.feature_register.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun RegisterFormFields(
    firstName: MutableState<String>,
    lastName: MutableState<String>,
    username: MutableState<String>,
    email: MutableState<String>,
    password: MutableState<String>,
    confirmPassword: MutableState<String>,
    gender: MutableState<String>,
    age: MutableState<Int>,
    isAdmin: MutableState<Boolean>
) {
    // Code for displaying form fields and their validation
}