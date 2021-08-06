package com.dsckiet.covid_tracker_android_app.ui.global

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dsckiet.covid_tracker_android_app.datastore.models.CountryWiseData

import com.dsckiet.covid_tracker_android_app.datastore.repositories.CountryWiseTrackerRepository
import javax.inject.Inject

class CountryWiseTrackerViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {
    val showCoronaCountryDetails: LiveData<CountryWiseData>
    val showProgress: LiveData<Boolean>
    private val repository = CountryWiseTrackerRepository(application)

    init {
        this.showProgress = repository.showProgress
        this.showCoronaCountryDetails = repository.showCoronaCountryDetails
    }

    fun getCoronaCountryDetails() {
        repository.getCoronaCountryDetails()
    }
}