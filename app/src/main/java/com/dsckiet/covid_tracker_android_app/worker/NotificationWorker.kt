package com.dsckiet.covid_tracker_android_app.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.dsckiet.covid_tracker_android_app.R
import com.dsckiet.covid_tracker_android_app.datastore.repositories.activeIndia
import com.dsckiet.covid_tracker_android_app.datastore.repositories.lastUpdated
import com.dsckiet.covid_tracker_android_app.utils.getPeriod
import com.dsckiet.covid_tracker_android_app.utils.stringToNumberFormat
import com.dsckiet.covid_tracker_android_app.utils.toDateFormat
import com.dsckiet.covid_tracker_android_app.ui.global.DashboardActivity

class NotificationWorker(val context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    override fun doWork(): Result {
        println("Do Work Called >>> ")
        return if (activeIndia != null && lastUpdated != null) {
            showNotification(
                stringToNumberFormat(activeIndia!!),
                getPeriod(lastUpdated!!.toDateFormat()!!)
            )
            Result.success()
        } else
            Result.retry()
    }


    private fun showNotification(activeIndia: String, time: String) {
        val intent = Intent(context, DashboardActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_ONE_SHOT
        )
        val channelId = "default"
        val channelName = "Covid Tracker"
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setColor(ContextCompat.getColor(context, R.color.red_color_line_chart))
            .setSmallIcon(R.drawable.ic_total_patient)
            .setContentTitle("India Active Cases: $activeIndia ")
            .setContentText("Last Updated $time")
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }


}

