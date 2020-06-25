package com.dsckiet.covid_tracker_android_app.network


import com.dsckiet.covid_tracker_android_app.models.StateWiseData
import retrofit2.Call
import retrofit2.http.GET

interface CoronaIndiaTrackerNetwork {
    @GET("data.json")
    fun getStateWiseCoronaDetails(): Call<StateWiseData>


}