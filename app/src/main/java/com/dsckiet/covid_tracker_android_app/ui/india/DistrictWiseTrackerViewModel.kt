package com.dsckiet.covid_tracker_android_app.ui.india

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dsckiet.covid_tracker_android_app.datastore.models.DistrictWiseData
import com.dsckiet.covid_tracker_android_app.datastore.repositories.DistrictWiseTrackerRepository

class DistrictWiseTrackerViewModel(application: Application) : AndroidViewModel(application) {
    val showDistrictWiseDetails: LiveData<List<DistrictWiseData>>
    val showProgress: LiveData<Boolean>
    private val repository = DistrictWiseTrackerRepository(application)

    init {
        this.showProgress = repository.showProgress
        this.showDistrictWiseDetails = repository.showDistrictWiseDetails
    }

    fun getCoronaDistrictDetails() {
        repository.getCoronaDistrictDetails()
    }
}