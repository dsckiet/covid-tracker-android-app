package com.dsckiet.covid_tracker_android_app.repository

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.dsckiet.covid_tracker_android_app.models.DistrictWiseData
import com.dsckiet.covid_tracker_android_app.network.CoronaIndiaTrackerClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DistrictWiseTrackerRepository(val application: Application) {
    val showDistrictWiseDetails = MutableLiveData<List<DistrictWiseData>>()

    fun getCoronaDistrictDetails() {
        val retrofitService = CoronaIndiaTrackerClient.getClient(application)
        val callApi = retrofitService.getDistrictWiseCoronaDetails()
        callApi.enqueue(object : Callback<List<DistrictWiseData>> {
            override fun onFailure(call: Call<List<DistrictWiseData>>, t: Throwable) {
                Toast.makeText(application, "Error ${t.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<List<DistrictWiseData>>,
                response: Response<List<DistrictWiseData>>
            ) {
                showDistrictWiseDetails.value = response.body()
            }

        })
    }
}