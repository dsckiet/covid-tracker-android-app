package com.dsckiet.covid_tracker_android_app.network


import com.dsckiet.covid_tracker_android_app.models.DistrictWiseData
import com.dsckiet.covid_tracker_android_app.models.StateWiseData
import retrofit2.Call
import retrofit2.http.GET

interface CoronaIndiaTrackerNetwork {
    @GET("data.json")
    fun getStateWiseCoronaDetails(): Call<StateWiseData>

    @GET("v2/state_district_wise.json")
    fun getDistrictWiseCoronaDetails(): Call<List<DistrictWiseData>>


}