package com.dsckiet.covid_tracker_android_app.di

import com.dsckiet.covid_tracker_android_app.datastore.repositories.CountryWiseTrackerRepository
import com.dsckiet.covid_tracker_android_app.datastore.repositories.DistrictWiseTrackerRepository
import com.dsckiet.covid_tracker_android_app.datastore.repositories.StateWiseTrackerRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideCountryWiseTrackerRepository(repository: CountryWiseTrackerRepository): CountryWiseTrackerRepository

    @Binds
    @Singleton
    abstract fun provideDistrictWiseTrackerRepository(repository: DistrictWiseTrackerRepository): DistrictWiseTrackerRepository

    @Binds
    @Singleton
    abstract fun provideStateWiseTrackerRepository(repository: StateWiseTrackerRepository): StateWiseTrackerRepository
}