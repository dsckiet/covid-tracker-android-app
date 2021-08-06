package com.dsckiet.covid_tracker_android_app.ui.global

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.dsckiet.covid_tracker_android_app.R
import com.dsckiet.covid_tracker_android_app.ui.adapter.CountriesCompleteListAdapter
import com.dsckiet.covid_tracker_android_app.ui.base.BaseFragment
import com.dsckiet.covid_tracker_android_app.utils.getPeriod
import com.dsckiet.covid_tracker_android_app.utils.globalTimeDateFormat
import kotlinx.android.synthetic.main.fragment_country_list.*


class CountryListFragment : BaseFragment<CountryWiseTrackerViewModel>() {
    private lateinit var navController: NavController
    private lateinit var adapter: CountriesCompleteListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_country_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = getViewModel()
        navController = Navigation.findNavController(view)
        back_button.setOnClickListener {
            navController.popBackStack()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        getData()
        swipeRefreshLayout.setOnRefreshListener {

            getData()
        }
    }

    private fun getData() {

        recycler_view_country_list.adapter
        adapter = CountriesCompleteListAdapter(requireContext())
        mViewModel.showProgress.observe(viewLifecycleOwner, Observer {
            swipeRefreshLayout.isRefreshing = it
        })
        mViewModel.getCoronaCountryDetails()
        recycler_view_country_list.adapter = adapter
        mViewModel.showCoronaCountryDetails.observe(viewLifecycleOwner, Observer {

            val lastUpdatedTime = "Last Updated " + getPeriod(globalTimeDateFormat(it.date)!!)
            last_updated.text = lastUpdatedTime
            adapter.setCountryWiseTracker(it.countries)
        })
    }

    override fun getViewModel(): CountryWiseTrackerViewModel {
        return ViewModelProvider(this.requireActivity(), viewModelFactory).get(CountryWiseTrackerViewModel::class.java)
    }

}
