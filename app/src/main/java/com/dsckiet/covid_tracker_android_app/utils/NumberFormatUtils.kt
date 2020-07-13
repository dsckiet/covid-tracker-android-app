package com.dsckiet.covid_tracker_android_app.utils

fun stringToNumberFormat(str: String): String {
    var string = str
    for (i in string.length - 3 downTo 1 step 3) {
        string = string.substring(0, i) + "," + string.substring(i)
    }
    return string
}