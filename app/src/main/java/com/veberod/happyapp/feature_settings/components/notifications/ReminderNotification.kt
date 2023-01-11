package com.veberod.happyapp.feature_settings.components.notifications


import android.Manifest
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.veberod.happyapp.NavRoutes
import com.veberod.happyapp.UserState
import java.util.*


private lateinit var alarmManager: AlarmManager
private lateinit var pendingIntent: PendingIntent

@RequiresApi(Build.VERSION_CODES.O)
fun createNotification(context: Context) {
    val name = "Reminder Channel"
    val desc = "This is a reminder for you"
    val importance = NotificationManager.IMPORTANCE_HIGH
    val channel = NotificationChannel(channelID, name, importance)
    channel.description = desc
    val notificationManager = context.getSystemService(ComponentActivity.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(channel)
}

@RequiresApi(Build.VERSION_CODES.M)
private fun cancelAlarm(context: Context){
    alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, AlarmReceiver::class.java)
    pendingIntent = PendingIntent.getBroadcast(
        context,
        111,
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

    alarmManager.cancel(pendingIntent)
    Toast.makeText(context,
        "Your alarm has been canceled",
        Toast.LENGTH_LONG).show()
}

@RequiresApi(Build.VERSION_CODES.M)
private fun scheduleAlarm(myTime : Calendar, context: Context){
    alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, AlarmReceiver::class.java)
    pendingIntent = PendingIntent.getBroadcast(
        context,
        111,
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

    val alarmClockInfo = AlarmManager.AlarmClockInfo(
        myTime.timeInMillis,
        pendingIntent
    )
    alarmManager.setAlarmClock(
        alarmClockInfo,
        pendingIntent
    )
    Toast.makeText(context,
        "Your alarm has been set",
        Toast.LENGTH_LONG).show()
}


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun SelectTime(context: Context, navController: NavController, userState: MutableState<UserState>){
    var timeSet by remember { mutableStateOf(false) }
    var alarmSet by remember { mutableStateOf(false) }
    //Get alarm status from database
    val calendar = Calendar.getInstance()
    val hour = calendar[Calendar.HOUR_OF_DAY]
    val minute = calendar[Calendar.MINUTE]
    val timeReceived = remember { mutableStateOf(Calendar.getInstance()) }
    val time = remember { mutableStateOf("00:00") }
    //Get time from database and remember
    val timePicker = TimePickerDialog(
        context,
        { _, hour: Int, minute: Int ->
            time.value = "$hour:$minute"
            if (minute<10){
                time.value = "$hour:0$minute"
            }
            timeReceived.value.apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, 0)
                if (timeReceived.value.before(Calendar.getInstance())){
                    timeReceived.value.add(Calendar.DATE,1)
                }
                // Save to database
            }
            Toast.makeText(context,
                "Time has been selected", Toast.LENGTH_LONG).show()
        }, hour, minute, false
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
        })
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height((60.dp)))
        Text(
            textAlign = TextAlign.Center,
            text = "You can select and set a daily reminder time " +
                    "to get notification to register your mood."
        )
        Spacer(modifier = Modifier.padding(5.dp))
        Text(
            textAlign = TextAlign.Center,
            //Pass time from database
            text = if (alarmSet) {
                "Alarm for ${time.value} is ON."
            } else {
                "Alarm for ${time.value} is OFF"
            },
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.padding(5.dp))
        Button(onClick = {
            timePicker.show()
            timeSet = true
        }) {
            Text(text = "Select Time")
        }
        Button(onClick = {
            if (notificationPermissionGranted) {
                //Pass time from database
                if(timeSet){
                    scheduleAlarm(timeReceived.value, context)
                    alarmSet = true
                    //Put to alarmSet database
                }else Toast.makeText(context, "Please select a time first.", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Please make sure 'Notification' Permissions are allowed first.", Toast.LENGTH_LONG).show()
                myPermission.launch(Manifest.permission.POST_NOTIFICATIONS)}
        }) {
            Text(text = "Set Reminder")
        }
        Button(onClick = {
            cancelAlarm(context)
            alarmSet = false
            //Put to alarmSet database
        }) {
            Text(text = "Cancel Reminder")
        }
        Spacer(modifier = Modifier.padding(30.dp))
        TrackDays()
        Button(onClick = {
            // set userState to default values
            userState.value = UserState(isLoggedIn = false)
            navController.navigate(NavRoutes.Login.route)
        }) {
            Text("Logout")
        }
    }
}