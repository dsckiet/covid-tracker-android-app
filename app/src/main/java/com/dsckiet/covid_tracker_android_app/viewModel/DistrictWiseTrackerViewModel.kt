package com.dsckiet.covid_tracker_android_app.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dsckiet.covid_tracker_android_app.models.DistrictWiseData
import com.dsckiet.covid_tracker_android_app.repository.DistrictWiseTrackerRepository

class DistrictWiseTrackerViewModel(application: Application) : AndroidViewModel(application) {
    val showDistrictWiseDetails: LiveData<List<DistrictWiseData>>
    private val repository = DistrictWiseTrackerRepository(application)

    init {
        this.showDistrictWiseDetails = repository.showDistrictWiseDetails
    }

    fun getCoronaDistrictDetails() {
        repository.getCoronaDistrictDetails()
    }
}