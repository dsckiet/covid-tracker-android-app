package com.dsckiet.covid_tracker_android_app.datastore.remote


import com.dsckiet.covid_tracker_android_app.datastore.models.DistrictWiseData
import com.dsckiet.covid_tracker_android_app.datastore.models.StateWiseData
import retrofit2.Call
import retrofit2.http.GET

interface CoronaIndiaTrackerNetwork {
    @GET("data.json")
    fun getStateWiseCoronaDetails(): Call<StateWiseData>

    @GET("v2/state_district_wise.json")
    fun getDistrictWiseCoronaDetails(): Call<List<DistrictWiseData>>


}