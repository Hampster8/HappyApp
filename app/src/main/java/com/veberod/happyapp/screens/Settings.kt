package com.veberod.happyapp.screens

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import com.veberod.happyapp.notifications.SelectTime

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun Settings(context: Context){
    SelectTime(context)
}