package com.dsckiet.covid_tracker_android_app.di

import androidx.lifecycle.ViewModel
import com.dsckiet.covid_tracker_android_app.ui.global.CountryWiseTrackerViewModel
import com.dsckiet.covid_tracker_android_app.ui.india.DistrictWiseTrackerViewModel
import com.dsckiet.covid_tracker_android_app.ui.india.StateWiseTrackerViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(CountryWiseTrackerViewModel::class)
    abstract fun bindCountryWiseTrackerViewModel(countryWiseTrackerViewModel: CountryWiseTrackerViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DistrictWiseTrackerViewModel::class)
    abstract fun bindDistrictWiseTrackerViewModel(districtWiseTrackerViewModel: DistrictWiseTrackerViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StateWiseTrackerViewModel::class)
    abstract fun bindStateWiseTrackerViewModel(stateWiseTrackerViewModel: StateWiseTrackerViewModel): ViewModel
}