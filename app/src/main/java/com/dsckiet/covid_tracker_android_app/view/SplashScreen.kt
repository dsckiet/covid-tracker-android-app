package com.dsckiet.covid_tracker_android_app.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.dsckiet.covid_tracker_android_app.R
import java.lang.Exception

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val background = object : Thread() {
            override fun run() {
                try {
                    sleep(3200)
                    val intent = Intent(baseContext, DashboardActivity::class.java)
                    startActivity(intent)

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        background.start()
    }
}