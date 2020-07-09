package com.dsckiet.covid_tracker_android_app.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.dsckiet.covid_tracker_android_app.R
import com.dsckiet.covid_tracker_android_app.utils.InternetConnectivity
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {
    private var pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        if (!InternetConnectivity.isNetworkAvailable(this)!!)
            Toast.makeText(this, "Internet Unavailable", Toast.LENGTH_SHORT).show()


    }
}