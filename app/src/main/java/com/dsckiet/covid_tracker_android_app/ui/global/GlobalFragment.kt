package com.dsckiet.covid_tracker_android_app.ui.global

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.dsckiet.covid_tracker_android_app.R
import com.dsckiet.covid_tracker_android_app.ui.adapter.CountriesAdapter
import com.dsckiet.covid_tracker_android_app.databinding.FragmentGlobalBinding
import com.dsckiet.covid_tracker_android_app.ui.base.BaseFragment
import com.dsckiet.covid_tracker_android_app.utils.*
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import java.util.*
import kotlin.math.roundToLong


class GlobalFragment : BaseFragment<CountryWiseTrackerViewModel>() {

    private lateinit var viewModel: CountryWiseTrackerViewModel
    private lateinit var adapter: CountriesAdapter
    private lateinit var navController: NavController
    private lateinit var binding: FragmentGlobalBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_global, container, false)
        return binding.root
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = getViewModel()
        navController = Navigation.findNavController(view)
        requireActivity().window.statusBarColor = Color.parseColor("#5baeff")

        binding.radiobutton.setOnCheckedChangeListener { group: RadioGroup, checkedId: Int ->
            if (checkedId == R.id.rb_india)
                navController.navigate(R.id.action_frag_Globe_to_frag_India)
        }
        binding.nextScreenBtn.setOnClickListener {
            navController.navigate(R.id.action_frag_Globe_to_countryListFragment)
        }


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getData()
        binding.swipeRefreshLayout.setOnRefreshListener {
            getData()
        }

    }

    private fun getData() {
        adapter = CountriesAdapter(requireContext())
        if (!InternetConnectivity.isNetworkAvailable(requireContext())!!)
            Toast.makeText(requireContext(), "Internet Unavailable", Toast.LENGTH_SHORT).show()
        viewModel.getCoronaCountryDetails()
        binding.recyclerView.adapter = adapter

        viewModel.showProgress.observe(viewLifecycleOwner, Observer {
            binding.swipeRefreshLayout.isRefreshing = it
        })
        viewModel.showCoronaCountryDetails.observe(viewLifecycleOwner, Observer {

            val mv = CustomMarkerView(
                requireContext(),
                com.dsckiet.covid_tracker_android_app.R.layout.custom_marker_view
            )

// set the marker to the chart

// set the marker to the chart
            binding.pieChart.setDrawMarkers(true)
            binding.pieChart.setDrawEntryLabels(true)
            // pieChart.markerView = mv
            binding.pieChart.setUsePercentValues(true)
            binding.pieChart.setExtraOffsets(2f, 3f, 3f, 2f)
            binding.pieChart.dragDecelerationFrictionCoef = 0.95f
            binding.pieChart.transparentCircleRadius = 61f
            binding.pieChart.description.isEnabled = false
            binding.pieChart.legend.isEnabled = false

            val a: ArrayList<PieEntry> = ArrayList()
            val activeCases =
                it.global.totalConfirmed - it.global.totalDeaths - it.global.totalRecovered
            val colorFirst = this.let {
                ContextCompat.getColor(
                    requireContext(),
                    com.dsckiet.covid_tracker_android_app.R.color.green
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

            if (!binding.swipeRefreshLayout.isRefreshing) {
                val data = PieDataSet(a, "Stats")
                data.formSize = 2f
                data.valueTextSize = 0.2f
                data.colors = mutableListOf(colorFirst, colorSecond, colorThird)
                data.sliceSpace = 3f
                data.selectionShift = 5f
                data.color = Color.CYAN
                data.isHighlightEnabled = true

                val pdata = PieData((data))
                data.colors = mutableListOf(colorFirst, colorSecond, colorThird)
                pdata.setValueTextSize(10f)
                binding.pieChart.centerText =
                    stringToNumberFormat(it.global.totalConfirmed.toString()) + "\nTotal Cases"
                binding.pieChart.setCenterTextSizePixels(30f)
                binding.pieChart.setCenterTextColor(com.dsckiet.covid_tracker_android_app.R.color.grey)
                binding.pieChart.setCenterTextSize(12f)
                pdata.setValueTextColor(Color.YELLOW)
                binding.pieChart.animateY(2000)
                binding.pieChart.data = pdata
            }
            binding.recoveredPercent.text =
                ((it.global.totalRecovered.toDouble() / it.global.totalConfirmed.toDouble()) * 100).roundToLong()
                    .toString() + "%"
            binding.deceasedPercent.text =
                ((it.global.totalDeaths.toDouble() / it.global.totalConfirmed.toDouble()) * 100).roundToLong()
                    .toString() + "%"
            binding.activePercent.text =
                ((activeCases.toDouble() / it.global.totalConfirmed.toDouble()) * 100).roundToLong()
                    .toString() + "%"

            val lastUpdatedTime = "Last Updated " + getPeriod(globalTimeDateFormat(it.date)!!)
            binding.lastUpdated.text = lastUpdatedTime
            binding.confirmedCount.text = stringToNumberFormat(it.global.totalConfirmed.toString())
            binding.recoverCount.text = stringToNumberFormat(it.global.totalRecovered.toString())
            binding.ripCount.text = stringToNumberFormat(it.global.totalDeaths.toString())
            binding.activeCount.text = stringToNumberFormat(activeCases.toString())
            binding.confirmedArrowNumber.text = stringToNumberFormat(it.global.newConfirmed.toString())
            binding.ripArrowNumber.text = stringToNumberFormat(it.global.newDeaths.toString())
            binding.recoverArrowNumber.text = stringToNumberFormat(it.global.newRecovered.toString())
            adapter.setCountryWiseTracker(it.countries)
        })
    }

    override fun getViewModel(): CountryWiseTrackerViewModel {
        return ViewModelProvider(this.requireActivity(), viewModelFactory).get(CountryWiseTrackerViewModel::class.java)
    }
}