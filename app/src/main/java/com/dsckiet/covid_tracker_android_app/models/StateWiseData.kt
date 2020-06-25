package com.dsckiet.covid_tracker_android_app.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StateWiseData
    (

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