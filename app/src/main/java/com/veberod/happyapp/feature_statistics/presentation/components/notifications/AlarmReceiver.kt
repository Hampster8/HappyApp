package com.veberod.happyapp.feature_statistics.presentation.components.notifications


import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.veberod.happyapp.MainActivity
import com.veberod.happyapp.R


const val channelID = "reminder"

class AlarmReceiver: BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceive(context: Context, intent: Intent) {
        val mp = MediaPlayer.create(context, R.raw.song)
        mp.start()
        notifyReminder(context)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun notifyReminder(context: Context){
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val myIntent = Intent(context, MainActivity::class.java)
        val myPendingIntent = PendingIntent.getActivity(
            context,
            2,
            myIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(context, channelID)
            .setContentTitle("Mood Log Reminder")
            .setContentText("How are you feeling? Don't forget to log your mood today.")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setAutoCancel(true)
            .setContentIntent(myPendingIntent)
            .addAction(
                R.drawable.ic_launcher_background,
                "Go To App",
                myPendingIntent
            )
            .build()
        notificationManager.notify(100, notification)
    }
}