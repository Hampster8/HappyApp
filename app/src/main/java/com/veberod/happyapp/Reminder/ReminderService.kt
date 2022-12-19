package com.veberod.happyapp.Reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.SystemClock
import android.widget.Toast
import androidx.annotation.RequiresApi

class ReminderService(private val context: Context) {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    companion object {
        const val channelID = "reminderChannel"
    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun scheduleNotification(timeReceived: Long, time: String) {
        val thisIntent = Intent(context, ReminderBroadcast::class.java)
        val thisPendingIntent = PendingIntent.getBroadcast(
            context,
            1,
            thisIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            SystemClock.elapsedRealtime(),
            thisPendingIntent
        )
        Toast.makeText(
            context,
            "Your reminder has been scheduled: $time", Toast.LENGTH_LONG
        ).show()
    }
}