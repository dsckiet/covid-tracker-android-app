package com.dsckiet.covid_tracker_android_app.datastore.remote

import com.dsckiet.covid_tracker_android_app.datastore.models.CountryWiseData
import retrofit2.Call
import retrofit2.http.GET

interface CoronaGlobalTrackerNetwork {
    @GET("summary")
    fun getCountryWiseCoronaDetails(): Call<CountryWiseData>
}