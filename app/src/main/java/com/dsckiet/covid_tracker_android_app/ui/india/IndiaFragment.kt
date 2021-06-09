package com.dsckiet.covid_tracker_android_app.ui.india

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.dsckiet.covid_tracker_android_app.R
import com.dsckiet.covid_tracker_android_app.ui.adapter.StateAdapter
import com.dsckiet.covid_tracker_android_app.databinding.FragmentIndiaBinding
import com.dsckiet.covid_tracker_android_app.utils.*
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.LargeValueFormatter


class IndiaFragment : Fragment() {

    private lateinit var viewModel: StateWiseTrackerViewModel
    private lateinit var adapter: StateAdapter
    lateinit var navController: NavController
    private val entries = ArrayList<Entry>()
    private val xAxisLabel: ArrayList<String> = ArrayList()
    private lateinit var binding: FragmentIndiaBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_india, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        binding.radiobutton.setOnCheckedChangeListener { group: RadioGroup, checkedId: Int ->
            if (checkedId == com.dsckiet.covid_tracker_android_app.R.id.rb_globe)
                navController.navigate(com.dsckiet.covid_tracker_android_app.R.id.action_frag_India_to_frag_Globe)
        }
        binding.nextScreenBtn.setOnClickListener {
            navController.navigate(com.dsckiet.covid_tracker_android_app.R.id.action_frag_India_to_stateListFragment)
        }
//        binding.indiaMapBtn.setOnClickListener{
//            navController.navigate(com.dsckiet.covid_tracker_android_app.R.id.action_frag_India_to_indiaMapFragment)
//        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getData()
        binding.swipeRefreshLayout.setOnRefreshListener {
            getData()
        }
    }


    private fun getData() {
        viewModel = ViewModelProvider(this).get(StateWiseTrackerViewModel::class.java)
        adapter = StateAdapter(requireContext())
        if (!InternetConnectivity.isNetworkAvailable(requireContext())!!)
            Toast.makeText(requireContext(), "Internet Unavailable", Toast.LENGTH_SHORT).show()
        viewModel.getCoronaStateDetails()
        binding.recyclerView.adapter = adapter




        viewModel.showCoronaStateDetails.observe(viewLifecycleOwner, Observer {
            if (!binding.swipeRefreshLayout.isRefreshing) {
                val lastUpdatedTime =
                    "Last Updated " + getPeriod(it[0].lastupdatedtime.toDateFormat()!!)
                binding.lastUpdated.text = lastUpdatedTime
                binding.confirmedCount.text = stringToNumberFormat(it[0].confirmed)
                binding.recoverCount.text = stringToNumberFormat(it[0].recovered)
                binding.ripCount.text = stringToNumberFormat(it[0].deaths)
                binding.activeCount.text = stringToNumberFormat(it[0].active)
                binding.confirmedArrowNumber.text = stringToNumberFormat(it[0].deltarecovered)
                binding.recoverArrowNumber.text = stringToNumberFormat(it[0].deltarecovered)
                binding.ripArrowNumber.text = stringToNumberFormat(it[0].deltadeaths)
                adapter.setStateWiseTracker(it)
            }
        })
        viewModel.showCoronaIndiaLineChart.observe(viewLifecycleOwner, Observer {
            if (!binding.swipeRefreshLayout.isRefreshing) {
                entries.clear()
                xAxisLabel.clear()
                binding.currentDate.text = it[it.size - 1].date
                binding.confirmedCase.text = stringToNumberFormat(it[it.size - 1].totalconfirmed)
                binding.confirmedCaseDelta.text =
                    "+" + stringToNumberFormat(it[it.size - 1].dailyconfirmed)
                for (i in 30 until it.size) {
                    if (i % 2 == 0)
                        entries.add(
                            Entry(
                                (i + 1 - 30).toFloat(),
                                (it[i].totalconfirmed).toFloat()
                            )
                        )
                    xAxisLabel.add(it[i].date.substring(0, 6))
                }

                val vl = LineDataSet(entries, "Corona Positive")
                vl.setDrawCircleHole(true)
                vl.setDrawValues(false)
                vl.setDrawFilled(true)
                val mv = CustomMarkerView(
                    requireContext(),
                    R.layout.custom_marker_view
                )

                vl.color = Color.parseColor("#FFE0E6")
                vl.fillColor = Color.parseColor("#FFE0E6")
                vl.fillAlpha = Color.RED
                vl.setCircleColor(Color.parseColor("#FF063A"))
                vl.circleHoleColor = Color.parseColor("#FF063A")
                binding.lineChart.description.text = ""
                binding.lineChart.legend.isEnabled = false
                binding.lineChart.axisRight.isEnabled = true
                binding.lineChart.axisRight.setDrawGridLines(false)
                binding.lineChart.axisRight.valueFormatter = LargeValueFormatter()
                binding.lineChart.xAxis.setDrawGridLines(false)

                binding.lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
                binding.lineChart.axisLeft.isEnabled = false
                binding.lineChart.axisRight.gridColor =
                    (Color.parseColor("#FF063A"))
                binding.lineChart.xAxis.gridColor =
                    (Color.parseColor("#FF063A"))
                binding.lineChart.xAxis.axisLineColor = (Color.parseColor("#FF063A"))
                binding.lineChart.axisRight.axisLineColor = (Color.parseColor("#FF063A"))

                vl.setDrawCircles(true)

                binding.lineChart.setDrawMarkers(true)
                binding.lineChart.animateX(2000)
                binding.lineChart.setDrawGridBackground(false)
                binding.lineChart.isHighlightPerDragEnabled = true

                binding.lineChart.data = LineData(vl)
                binding.lineChart.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisLabel)
                binding.lineChart.xAxis.setLabelCount(20, false)
                binding.lineChart.xAxis.textSize = 6f
                binding.lineChart.xAxis.textColor =
                    (Color.parseColor("#E86985"))
                binding.lineChart.axisRight.textColor =
                    (Color.parseColor("#E86985"))
                binding.lineChart.isDoubleTapToZoomEnabled = false
                binding.lineChart.setScaleEnabled(false)

                binding.lineChart.setTouchEnabled(true)
                binding.lineChart.setPinchZoom(false)
                binding.lineChart.markerView = mv

            }
        })

        viewModel.showProgress.observe(viewLifecycleOwner, Observer {
            binding.swipeRefreshLayout.isRefreshing = it
        })
    }
}