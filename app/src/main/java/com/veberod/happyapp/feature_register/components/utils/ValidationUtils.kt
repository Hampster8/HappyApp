package com.veberod.happyapp.feature_register.components.utils

import android.util.Patterns

class ValidationUtils {
    companion object {
        fun isValidEmail(email: String): Boolean {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun isValidPassword(password: String): Boolean {
            // Add your own password validation rules here
            return password.length >= 6
        }

        fun isFormValid(
            firstName: String,
            lastName: String,
            username: String,
            email: String,
            password: String,
            confirmPassword: String,
            gender: String,
            isAdmin: Boolean
        ): Boolean {
            return firstName.isNotEmpty() &&
                    lastName.isNotEmpty() &&
                    username.isNotEmpty() &&
                    email.isNotEmpty() &&
                    password.isNotEmpty() &&
                    confirmPassword.isNotEmpty() &&
                    gender.isNotEmpty() &&
                    password == confirmPassword &&
                    isAdmin
        }

    }
}