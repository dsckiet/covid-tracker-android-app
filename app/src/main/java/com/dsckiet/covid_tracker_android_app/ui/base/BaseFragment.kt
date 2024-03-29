package com.dsckiet.covid_tracker_android_app.ui.base

import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment<V : ViewModel> : DaggerFragment() {

    protected lateinit var mViewModel: V

    abstract fun getViewModel(): V

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    fun showProgress(progressBar: ProgressBar) {
        progressBar.visibility = View.VISIBLE
    }

    fun hideProgress(progressBar: ProgressBar) {
        progressBar.visibility = View.GONE
    }

    fun toast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

}