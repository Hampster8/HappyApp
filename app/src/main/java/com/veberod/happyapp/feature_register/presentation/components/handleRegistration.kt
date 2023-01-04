package com.veberod.happyapp.feature_register.presentation.components

import android.content.Context
import androidx.navigation.NavHostController
import com.veberod.happyapp.NavRoutes
import com.veberod.happyapp.feature_admin.domain.model.User
import com.veberod.happyapp.feature_admin.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun handleRegistration(
    firstName: String,
    lastName: String,
    username: String,
    email: String,
    password: String,
    confirmPassword: String,
    gender: String,
    age: Int,
    context: Context,
    navController: NavHostController,
    onRegistrationCompleted: (Boolean) -> Unit
) {
    val scope = CoroutineScope(Dispatchers.Default)
    val userRepository = UserRepository(context)
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
            if (userRepository.addUser(user, context)) {
                navController.navigate(NavRoutes.Smilies.route)
                onRegistrationCompleted(true)
            } else {
                onRegistrationCompleted(false)
            }
        }
    } else {
        onRegistrationCompleted(false)
    }
}