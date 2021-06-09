package com.dsckiet.covid_tracker_android_app.ui.base

import javax.inject.Inject

class CovidTrackerApplication : BaseApplication() {

//    @Inject
//    lateinit var notificationWorkerFactory: NotificationWorkerFactory

    override fun onCreate() {
        super.onCreate()
//        notificationWorkerFactory.startNotificationWorkerService(this)
    }

//    override fun getWorkManagerConfiguration(): Configuration {
//        return Configuration.Builder()
//            .setMinimumLoggingLevel(android.util.Log.INFO)
//            .setWorkerFactory(notificationWorkerFactory)
//            .build()
//    }

}