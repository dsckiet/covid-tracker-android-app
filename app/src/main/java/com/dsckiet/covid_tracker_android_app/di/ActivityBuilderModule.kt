package com.dsckiet.covid_tracker_android_app.di

import com.dsckiet.covid_tracker_android_app.di.scope.ActivityScoped
import com.dsckiet.covid_tracker_android_app.di.scope.FragmentScoped
import com.dsckiet.covid_tracker_android_app.ui.global.CountryListFragment
import com.dsckiet.covid_tracker_android_app.ui.global.DashboardActivity
import com.dsckiet.covid_tracker_android_app.ui.global.GlobalFragment
import com.dsckiet.covid_tracker_android_app.ui.india.DistrictListFragment
import com.dsckiet.covid_tracker_android_app.ui.india.IndiaFragment
import com.dsckiet.covid_tracker_android_app.ui.india.StateListFragment
import com.dsckiet.covid_tracker_android_app.ui.mapview.IndiaMapFragment
import com.dsckiet.covid_tracker_android_app.ui.mapview.MapViewModule
import com.dsckiet.covid_tracker_android_app.ui.splash.SplashScreen
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ActivityScoped
    @ContributesAndroidInjector
    abstract fun contributeDashboardActivity(): DashboardActivity

    @ActivityScoped
    @ContributesAndroidInjector
    abstract fun contributeSplashScreen(): SplashScreen

    /* Remove this later; Fragment scope context must not directly
    bind with activity scope context; Use sub-module inclusions instead */
    @ContributesAndroidInjector
    abstract fun contributeIndiaMapFragment(): IndiaMapFragment

    @ContributesAndroidInjector
    abstract fun contributeCountryListFragment(): CountryListFragment

    @ContributesAndroidInjector
    abstract fun contributeGlobalFragment(): GlobalFragment

    @ContributesAndroidInjector
    abstract fun contributeDistrictListFragment(): DistrictListFragment

    @ContributesAndroidInjector
    abstract fun contributeIndiaFragment(): IndiaFragment

    @ContributesAndroidInjector
    abstract fun contributeStateListFragment(): StateListFragment
}