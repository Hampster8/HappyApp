package com.veberod.happyapp.Reminder

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.veberod.happyapp.MainActivity
import com.veberod.happyapp.R
import com.veberod.happyapp.screens.Smilies

class ReminderBroadcast: BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("PrivateResource", "UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent?) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val myIntent = Intent(context, MainActivity::class.java)
        val myPendingIntent = PendingIntent.getActivity(
            context,
            2,
            myIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(context, ReminderService.channelID)
            .setContentTitle("Mood Log Reminder")
            .setContentText("How are you feeling? Don't forget to log your mood today.")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setAutoCancel(true)
            .setContentIntent(myPendingIntent)
            .addAction(
                androidx.core.R.drawable.notification_bg,
                "Go To App",
                myPendingIntent
            )
            .build()
        notificationManager.notify(200, notification)
    }
}