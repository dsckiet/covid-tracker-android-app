package com.dsckiet.covid_tracker_android_app.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.dsckiet.covid_tracker_android_app.adapter.CountriesAdapter
import com.dsckiet.covid_tracker_android_app.utils.InternetConnectivity
import com.dsckiet.covid_tracker_android_app.utils.getPeriod
import com.dsckiet.covid_tracker_android_app.utils.globalTimeDateFormat
import com.dsckiet.covid_tracker_android_app.utils.stringToNumberFormat
import com.dsckiet.covid_tracker_android_app.viewModel.CountryWiseTrackerViewModel
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.android.synthetic.main.frag_globe.*
import kotlinx.android.synthetic.main.frag_globe.last_updated
import kotlinx.android.synthetic.main.frag_globe.swipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_country_list.*
import kotlin.math.roundToLong

class GlobalFragment : Fragment() {

    private lateinit var viewModel: CountryWiseTrackerViewModel
    private lateinit var adapter: CountriesAdapter
    private lateinit var navController: NavController

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
        next_screen_btn.setOnClickListener {
            navController.navigate(com.dsckiet.covid_tracker_android_app.R.id.action_frag_Globe_to_countryListFragment)
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
        viewModel = ViewModelProvider(this).get(CountryWiseTrackerViewModel::class.java)
        adapter = CountriesAdapter(requireContext())
        if (!InternetConnectivity.isNetworkAvailable(requireContext())!!)
            Toast.makeText(requireContext(), "Internet Unavailable", Toast.LENGTH_SHORT).show()
        viewModel.getCoronaCountryDetails()
        recycler_view.adapter = adapter
        viewModel.showProgress.observe(viewLifecycleOwner, Observer {
            swipeRefreshLayout.isRefreshing = it
        })
        viewModel.showCoronaCountryDetails.observe(viewLifecycleOwner, Observer {

            pieChart.setUsePercentValues(true)
            pieChart.setExtraOffsets(2f, 3f, 3f, 2f)
            pieChart.dragDecelerationFrictionCoef = 0.95f
            pieChart.transparentCircleRadius = 61f
            pieChart.description.isEnabled = false
            pieChart.legend.isEnabled = false

            val a: ArrayList<PieEntry> = ArrayList()
            val activeCases =
                it.global.totalConfirmed - it.global.totalDeaths - it.global.totalRecovered
            val colorFirst = this.let {
                ContextCompat.getColor(
                    requireContext(),
                    com.dsckiet.covid_tracker_android_app.R.color.blue
                )
            }
            val colorSecond = this.let {
                ContextCompat.getColor(
                    requireContext(),
                    com.dsckiet.covid_tracker_android_app.R.color.red
                )
            }
            val colorThird = this.let {
                ContextCompat.getColor(
                    requireContext(),
                    com.dsckiet.covid_tracker_android_app.R.color.grey
                )
            }


            a.add(PieEntry(it.global.totalRecovered.toFloat(), "Recovered"))
            a.add(
                PieEntry(
                    (activeCases).toFloat(),
                    "Active"
                )
            )
            a.add(PieEntry(it.global.totalDeaths.toFloat(), "Deceased"))


            val data = PieDataSet(a, "Stats")
            data.formSize = 2f
            data.valueTextSize = 0.2f
            data.colors = mutableListOf(colorFirst, colorSecond, colorThird)
            data.sliceSpace = 3f
            data.selectionShift = 5f
            data.color = Color.CYAN

            val pdata = PieData((data))
            data.colors = mutableListOf(colorFirst, colorSecond, colorThird)
            pdata.setValueTextSize(10f)
            pieChart.centerText =
                stringToNumberFormat(it.global.totalConfirmed.toString()) + "\nTotal Cases"

            pieChart.setCenterTextColor(com.dsckiet.covid_tracker_android_app.R.color.grey)
            pieChart.setCenterTextSize(12f)
            pdata.setValueTextColor(Color.YELLOW)

            pieChart.data = pdata
            pieChart.animateY(2000)

            recovered_percent.text =
                ((it.global.totalRecovered.toDouble() / it.global.totalConfirmed.toDouble()) * 100).roundToLong()
                    .toString() + "%"
            deceased_percent.text =
                ((it.global.totalDeaths.toDouble() / it.global.totalConfirmed.toDouble()) * 100).roundToLong()
                    .toString() + "%"
            active_percent.text =
                ((activeCases.toDouble() / it.global.totalConfirmed.toDouble()) * 100).roundToLong()
                    .toString() + "%"

            val lastUpdatedTime = "Last Updated " + getPeriod(globalTimeDateFormat(it.date)!!)
            last_updated.text = lastUpdatedTime
            confirmed_count.text = stringToNumberFormat(it.global.totalConfirmed.toString())
            recovered_count.text = stringToNumberFormat(it.global.totalRecovered.toString())
            rip_count.text = stringToNumberFormat(it.global.totalDeaths.toString())
            active_count.text = stringToNumberFormat(activeCases.toString())
            confirmed_arrow_number.text = stringToNumberFormat(it.global.newConfirmed.toString())
            rip_arrow_number.text = stringToNumberFormat(it.global.newDeaths.toString())
            recover_arrow_number.text = stringToNumberFormat(it.global.newRecovered.toString())
            adapter.setCountryWiseTracker(it.countries)
        })
    }
}