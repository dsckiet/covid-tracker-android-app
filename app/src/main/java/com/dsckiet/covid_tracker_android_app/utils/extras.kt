package com.dsckiet.covid_tracker_android_app.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

@SuppressLint("SimpleDateFormat")
fun getPeriod(past: Date): String {
    val now = Date()
    val seconds = TimeUnit.MILLISECONDS.toSeconds(now.time - past.time)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(now.time - past.time)
    val hours = TimeUnit.MILLISECONDS.toHours(now.time - past.time)

    return when {
        seconds < 60 -> {
            "Recently"
        }
        minutes < 60 -> {
            "$minutes minutes ago"
        }

        hours < 24 -> {
            "$hours hour ${minutes % 60} min ago"
        }
        else -> {
            SimpleDateFormat("dd/MM/yy, hh:mm a").format(past).toString()
        }
    }
}

@SuppressLint("SimpleDateFormat")
fun String.toDateFormat(): Date? {
    return SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        .parse(this)
}

fun globalTimeDateFormat(time: String): Date? {
    val format = SimpleDateFormat(
        "yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US
    )
    format.timeZone = TimeZone.getTimeZone("UTC")
    return format.parse(time)


}