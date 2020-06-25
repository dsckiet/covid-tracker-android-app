package com.dsckiet.covid_tracker_android_app.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dsckiet.covid_tracker_android_app.models.Statewise

import com.dsckiet.covid_tracker_android_app.repository.StateWiseTrackerRepository

class StateWiseTrackerViewModel(application: Application) : AndroidViewModel(application) {
    val showCoronaStateDetails: LiveData<List<Statewise>>
    private val repository = StateWiseTrackerRepository(application)

    init {
        this.showCoronaStateDetails = repository.showCoronaStateDetails
    }

    fun getCoronaStateDetails() {
        repository.getCoronaStateDetails()
    }
}