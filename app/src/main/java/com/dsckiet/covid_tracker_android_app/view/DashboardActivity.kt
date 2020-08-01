package com.dsckiet.covid_tracker_android_app.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.dsckiet.covid_tracker_android_app.R
import com.dsckiet.covid_tracker_android_app.utils.InternetConnectivity
import com.dsckiet.covid_tracker_android_app.worker.NotificationWorker
import java.util.concurrent.TimeUnit

class DashboardActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        if (!InternetConnectivity.isNetworkAvailable(this)!!)
            Toast.makeText(this, "Internet Unavailable", Toast.LENGTH_SHORT).show()
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = PeriodicWorkRequestBuilder<NotificationWorker>(5, TimeUnit.HOURS)
            .addTag("Notification")
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork("Notification", ExistingPeriodicWorkPolicy.KEEP, request)
    }
}