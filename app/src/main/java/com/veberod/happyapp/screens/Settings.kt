package com.veberod.happyapp.screens

import android.Manifest
import android.app.TimePickerDialog
import android.content.pm.PackageManager
import android.icu.util.Calendar
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.veberod.happyapp.Reminder.ReminderService


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun Settings() {
    val context = LocalContext.current
    val myService = ReminderService(context)
    val myCalendar = Calendar.getInstance()
    val timeReceived = Calendar.getInstance()
    val myHours = myCalendar[Calendar.HOUR_OF_DAY]
    val myMinutes = myCalendar[Calendar.MINUTE]
    val myTime = remember { mutableStateOf("") }
    val myTimeDialog = TimePickerDialog(
        context,
        { _, myHours: Int, myMinutes: Int ->
            myTime.value = "$myHours:$myMinutes"
            if (myMinutes<10){
                myTime.value = "$myHours:0$myMinutes"
            }
            Toast.makeText(context,
                "Time has been selected", Toast.LENGTH_LONG).show()
            timeReceived.apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, myHours)
                set(Calendar.MINUTE, myMinutes)
            }
        }, myHours, myMinutes, false
    )
    var notificationPermissionGranted by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    val myPermission = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            notificationPermissionGranted = isGranted
            if(myTime.value != ""){
                myService.scheduleNotification(timeReceived.timeInMillis,myTime.value)
            }else Toast.makeText(context,
                "You should choose the time first",
                Toast.LENGTH_LONG).show()
        })
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = "You can choose a time to receive a reminder each day by using the buttons below."
        )
        Spacer(modifier = Modifier.height((30.dp)))
        Text(text = "Reminder Time: ${myTime.value}")
        Button(onClick = {
            myTimeDialog.show()
        }) {
            Text(text = "Choose Time")
        }
        Button(onClick = {
            if (notificationPermissionGranted) {
                if (myTime.value != "") {
                    myService.scheduleNotification(timeReceived.timeInMillis, myTime.value)
                } else Toast.makeText(
                    context,
                    "You should choose the time first",
                    Toast.LENGTH_LONG
                ).show()
            } else myPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
        }) {
            Text(text = "Schedule Reminder")
        }
    }
}