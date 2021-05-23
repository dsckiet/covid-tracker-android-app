package com.dsckiet.covid_tracker_android_app.datastore.repositories

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.dsckiet.covid_tracker_android_app.datastore.models.DistrictWiseData
import com.dsckiet.covid_tracker_android_app.datastore.remote.CoronaIndiaTrackerClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DistrictWiseTrackerRepository(val application: Application) {
    val showDistrictWiseDetails = MutableLiveData<List<DistrictWiseData>>()
    val showProgress = MutableLiveData<Boolean>()

    fun getCoronaDistrictDetails() {
        val retrofitService = CoronaIndiaTrackerClient.getClient(application)
        val callApi = retrofitService.getDistrictWiseCoronaDetails()
        showProgress.value = true
        callApi.enqueue(object : Callback<List<DistrictWiseData>> {
            override fun onFailure(call: Call<List<DistrictWiseData>>, t: Throwable) {
                showProgress.value = false
                Toast.makeText(application, "Error ${t.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<List<DistrictWiseData>>,
                response: Response<List<DistrictWiseData>>
            ) {
                showProgress.value = false
                showDistrictWiseDetails.value = response.body()

            }

        })
    }
}