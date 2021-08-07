package com.dsckiet.covid_tracker_android_app.ui.base

import com.dsckiet.covid_tracker_android_app.di.DaggerAppComponent
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

open class BaseApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this.applicationContext)
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
    }
}