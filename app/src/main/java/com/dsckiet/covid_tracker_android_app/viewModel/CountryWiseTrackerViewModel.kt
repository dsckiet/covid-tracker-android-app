package com.dsckiet.covid_tracker_android_app.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dsckiet.covid_tracker_android_app.models.CountryWiseData

import com.dsckiet.covid_tracker_android_app.repository.CountryWiseTrackerRepository

class CountryWiseTrackerViewModel(application: Application) : AndroidViewModel(application) {
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