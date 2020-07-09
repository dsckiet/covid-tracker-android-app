package com.dsckiet.covid_tracker_android_app.repository

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.dsckiet.covid_tracker_android_app.models.CasesTimeSery
import com.dsckiet.covid_tracker_android_app.models.StateWiseData
import com.dsckiet.covid_tracker_android_app.models.Statewise
import com.dsckiet.covid_tracker_android_app.network.CoronaIndiaTrackerClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class StateWiseTrackerRepository(val application: Application) {
    val showCoronaStateDetails = MutableLiveData<List<Statewise>>()
    val showCoronaIndiaLineChart = MutableLiveData<List<CasesTimeSery>>()
    val showProgress = MutableLiveData<Boolean>()
    fun getCoronaStateDetails() {


        val retrofitService = CoronaIndiaTrackerClient.getClient(application)
        val callApi = retrofitService.getStateWiseCoronaDetails()
        showProgress.value = true
        callApi.enqueue(object : Callback<StateWiseData> {
            override fun onFailure(call: Call<StateWiseData>, t: Throwable) {
                showProgress.value = false
                Toast.makeText(application, "Error : ${t.message}", Toast.LENGTH_SHORT).show()

            }

            override fun onResponse(
                call: Call<StateWiseData>,
                response: Response<StateWiseData>
            ) {
                showProgress.value = false
                if (response.body()?.statewise != null) {
                    showCoronaStateDetails.value = response.body()!!.statewise

                }
                if (response.body()?.casesTimeSeries != null) {
                    showCoronaIndiaLineChart.value = response.body()!!.casesTimeSeries
                }
            }

        })
    }

}