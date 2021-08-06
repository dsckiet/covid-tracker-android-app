package com.dsckiet.covid_tracker_android_app.ui.india

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dsckiet.covid_tracker_android_app.datastore.models.CasesTimeSery
import com.dsckiet.covid_tracker_android_app.datastore.models.Statewise
import com.dsckiet.covid_tracker_android_app.datastore.repositories.StateWiseTrackerRepository
import javax.inject.Inject

class StateWiseTrackerViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {
    val showCoronaStateDetails: LiveData<List<Statewise>>
    val showProgress: LiveData<Boolean>
    val showCoronaIndiaLineChart: LiveData<List<CasesTimeSery>>
    private val repository = StateWiseTrackerRepository(application)

    init {
        this.showProgress = repository.showProgress
        this.showCoronaStateDetails = repository.showCoronaStateDetails
        this.showCoronaIndiaLineChart = repository.showCoronaIndiaLineChart
    }

    fun getCoronaStateDetails() {
        repository.getCoronaStateDetails()
    }
}