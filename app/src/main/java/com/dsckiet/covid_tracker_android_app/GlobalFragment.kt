package com.dsckiet.covid_tracker_android_app

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.FragmentController
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.dsckiet.covid_tracker_android_app.R
import com.dsckiet.covid_tracker_android_app.adapter.CountriesAdapter
import com.dsckiet.covid_tracker_android_app.utils.InternetConnectivity
import com.dsckiet.covid_tracker_android_app.utils.getPeriod
import com.dsckiet.covid_tracker_android_app.utils.globalTimeDateFormat
import com.dsckiet.covid_tracker_android_app.viewModel.CountryWiseTrackerViewModel
import kotlinx.android.synthetic.main.frag_globe.*

class GlobalFragment : Fragment() {

    private lateinit var viewModel: CountryWiseTrackerViewModel
    private lateinit var adapter: CountriesAdapter
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            com.dsckiet.covid_tracker_android_app.R.layout.frag_globe,
            container,
            false
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        radiobutton.setOnCheckedChangeListener { group: RadioGroup, checkedId: Int ->
            if (checkedId == com.dsckiet.covid_tracker_android_app.R.id.rb_india)
                navController.navigate(com.dsckiet.covid_tracker_android_app.R.id.action_frag_Globe_to_frag_India)
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(CountryWiseTrackerViewModel::class.java)
        adapter = CountriesAdapter(requireContext())
        if (!InternetConnectivity.isNetworkAvailable(requireContext())!!)
            Toast.makeText(requireContext(), "Internet Unavailable", Toast.LENGTH_SHORT).show()
        viewModel.getCoronaCountryDetails()
        recycler_view.adapter = adapter
        viewModel.showCoronaCountryDetails.observe(viewLifecycleOwner, Observer {
            val lastUpdatedTime = "Last Updated " + getPeriod(globalTimeDateFormat(it.date)!!)
            last_updated.text = lastUpdatedTime
            confirmed_count.text = it.global.totalConfirmed.toString()
            recovered_count.text = it.global.totalRecovered.toString()
            rip_count.text = it.global.totalDeaths.toString()
            val activeCases =
                it.global.totalConfirmed - it.global.totalDeaths - it.global.totalRecovered
            active_count.text = activeCases.toString()
            adapter.setCountryWiseTracker(it.countries)
        })
    }
}