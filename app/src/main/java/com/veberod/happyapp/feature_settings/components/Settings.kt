package com.veberod.happyapp.screens

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.veberod.happyapp.NavRoutes
import com.veberod.happyapp.UserState
import com.veberod.happyapp.feature_statistics.presentation.components.notifications.SelectTime

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun Settings(context: Context, navController: NavController, userState: MutableState<UserState>) {
    SelectTime(context)

    Button(onClick = {
        // set userState to default values
        userState.value = UserState(isLoggedIn = false)
        navController.navigate(NavRoutes.Login.route)
    }) {
        Text("Logout")
    }
}