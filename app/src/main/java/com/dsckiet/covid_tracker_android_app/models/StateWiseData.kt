package com.dsckiet.covid_tracker_android_app.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StateWiseData
    (
    @Json(name = "cases_time_series")
    val casesTimeSeries: List<CasesTimeSery>,
    val statewise: List<Statewise>
) : Parcelable

@Parcelize
data class Statewise(
    val active: String,
    val confirmed: String,
    val deaths: String,
    val deltaconfirmed: String,
    val deltadeaths: String,
    val deltarecovered: String,
    val lastupdatedtime: String,
    val recovered: String,
    val state: String,
    val statecode: String
) : Parcelable

@Parcelize
data class CasesTimeSery(
    val dailyconfirmed: String,
    val dailydeceased: String,
    val dailyrecovered: String,
    val date: String,
    val totalconfirmed: String,
    val totaldeceased: String,
    val totalrecovered: String
) : Parcelable