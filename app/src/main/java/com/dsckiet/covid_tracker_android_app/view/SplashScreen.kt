package com.dsckiet.covid_tracker_android_app.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.dsckiet.covid_tracker_android_app.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler().postDelayed(
            {
                val intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)
                finish()
            }, 3200
        )
    }
}