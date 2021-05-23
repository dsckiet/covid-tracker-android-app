package com.dsckiet.covid_tracker_android_app.datastore.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DistrictWiseData(
    val districtData: List<DistrictData>,
    val statecode: String,
    val state: String
) : Parcelable

@Parcelize

data class DistrictData(
    val confirmed: Int = 0,
    val active: Int = 0,
    val deceased: Int = 0,
    val recovered: Int = 0,
    val delta: Delta,
    val notes: String = "",
    val district: String = ""
) : Parcelable

@Parcelize

data class Delta(
    val confirmed: Int = 0,
    val deceased: Int = 0,
    val recovered: Int = 0
) : Parcelable