package com.dsckiet.covid_tracker_android_app.di

import com.dsckiet.covid_tracker_android_app.di.scope.ActivityScoped
import com.dsckiet.covid_tracker_android_app.ui.global.DashboardActivity
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
}