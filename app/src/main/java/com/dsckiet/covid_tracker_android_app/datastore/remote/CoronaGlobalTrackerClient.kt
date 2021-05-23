package com.dsckiet.covid_tracker_android_app.datastore.remote


import android.content.Context
import com.dsckiet.covid_tracker_android_app.utils.InternetConnectivity
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object CoronaGlobalTrackerClient {
    const val BASE_URL_GLOBAL = "https://api.covid19api.com/"
    const val cacheSize = (5 * 1024 * 1024).toLong()
    fun getClient(context: Context): CoronaGlobalTrackerNetwork {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val myCache = Cache(context.cacheDir, cacheSize)
        val httpClient = OkHttpClient.Builder()
            .cache(myCache)
            .addInterceptor { chain ->
                var request = chain.request()
                request = if (InternetConnectivity.isNetworkAvailable(context)!!)
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                else
                    request.newBuilder().header(
                        "Cache-Control",
                        "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                    ).build()
                chain.proceed(request)
            }
            .build()


        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL_GLOBAL)
            .client(httpClient)
            .build()


        val retrofitService: CoronaGlobalTrackerNetwork by lazy {
            retrofit.create(CoronaGlobalTrackerNetwork::class.java)
        }
        return retrofitService

    }
}