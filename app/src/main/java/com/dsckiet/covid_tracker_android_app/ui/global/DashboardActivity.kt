package com.dsckiet.covid_tracker_android_app.ui.global

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.work.*
import com.dsckiet.covid_tracker_android_app.R
import com.dsckiet.covid_tracker_android_app.databinding.ActivityDashboardBinding
import com.dsckiet.covid_tracker_android_app.utils.InternetConnectivity
import com.dsckiet.covid_tracker_android_app.worker.NotificationWorker
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerAppCompatActivity
import java.util.concurrent.TimeUnit

class DashboardActivity : DaggerAppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)

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