package com.dsckiet.covid_tracker_android_app.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dsckiet.covid_tracker_android_app.R
import com.dsckiet.covid_tracker_android_app.ui.global.DashboardActivity
import dagger.android.support.DaggerAppCompatActivity
import java.lang.Exception

class SplashScreen : DaggerAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val background = object : Thread() {
            override fun run() {
                try {
                    sleep(1500)
                    val intent = Intent(applicationContext, DashboardActivity::class.java)
                    startActivity(intent)
                    finish()

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        background.start()
    }
}