package com.veberod.happyapp.Reminder

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi

class ReminderApp: Application() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        createChannel()
    }

    fun getContext(): Context{
        return applicationContext
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        val channel = NotificationChannel(
            ReminderService.channelID,
            "Mood Log Reminder",
            NotificationManager.IMPORTANCE_HIGH
        )
        channel.description = "These notifications are set by user as a reminder to log mood"
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
