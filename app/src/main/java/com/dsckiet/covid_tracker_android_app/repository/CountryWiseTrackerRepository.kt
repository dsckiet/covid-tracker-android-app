package com.dsckiet.covid_tracker_android_app.repository

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.dsckiet.covid_tracker_android_app.models.CountryWiseData
import com.dsckiet.covid_tracker_android_app.network.CoronaGlobalTrackerClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CountryWiseTrackerRepository(val application: Application) {
    val showCoronaCountryDetails = MutableLiveData<CountryWiseData>()
    val showProgress = MutableLiveData<Boolean>()
    fun getCoronaCountryDetails() {
        val retrofitService = CoronaGlobalTrackerClient.getClient(application)
        val callApi = retrofitService.getCountryWiseCoronaDetails()
        showProgress.value = true
        callApi.enqueue(object : Callback<CountryWiseData> {
            override fun onFailure(call: Call<CountryWiseData>, t: Throwable) {
                showProgress.value = false
                Toast.makeText(application, "Error : ${t.message}", Toast.LENGTH_SHORT).show()

            }

            @SuppressLint("LogNotTimber")
            override fun onResponse(
                call: Call<CountryWiseData>,
                response: Response<CountryWiseData>
            ) {
                showProgress.value = false
                //showCoronaCountryDetails.value = response.body()
                Log.d("Tag", "data = ${response.body()}")

            }

        })
    }

}