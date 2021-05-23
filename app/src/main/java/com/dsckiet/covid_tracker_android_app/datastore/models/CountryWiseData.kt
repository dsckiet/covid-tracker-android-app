package com.dsckiet.covid_tracker_android_app.datastore.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CountryWiseData(
    @Json(name = "Countries")
    val countries: List<Country>,
    @Json(name = "Date")
    val date: String,
    @Json(name = "Global")
    val global: Global
) : Parcelable

@Parcelize
data class Country(
    @Json(name = "Country")
    val country: String,
    @Json(name = "CountryCode")
    val countryCode: String,
    @Json(name = "Date")
    val date: String,
    @Json(name = "NewConfirmed")
    val newConfirmed: Int,
    @Json(name = "NewDeaths")
    val newDeaths: Int,
    @Json(name = "NewRecovered")
    val newRecovered: Int,
    @Json(name = "TotalConfirmed")
    val totalConfirmed: Int,
    @Json(name = "TotalDeaths")
    val totalDeaths: Int,
    @Json(name = "TotalRecovered")
    val totalRecovered: Int
) : Parcelable

@Parcelize
data class Global(
    @Json(name = "NewConfirmed")
    val newConfirmed: Int,
    @Json(name = "NewDeaths")
    val newDeaths: Int,
    @Json(name = "NewRecovered")
    val newRecovered: Int,
    @Json(name = "TotalConfirmed")
    val totalConfirmed: Int,
    @Json(name = "TotalDeaths")
    val totalDeaths: Int,
    @Json(name = "TotalRecovered")
    val totalRecovered: Int
) : Parcelable