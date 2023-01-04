package com.veberod.happyapp.feature_register.presentation.components

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
    }
}