package com.veberod.happyapp.feature_register.presentation.components

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.veberod.happyapp.NavRoutes

@Composable
fun RegisterBackButton(navController: NavHostController) {
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
