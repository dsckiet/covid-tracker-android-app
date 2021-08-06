package com.dsckiet.covid_tracker_android_app.ui.mapview

import com.dsckiet.covid_tracker_android_app.di.scope.FragmentScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MapViewModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun contributeIndiaMapFragment(): IndiaMapFragment
}