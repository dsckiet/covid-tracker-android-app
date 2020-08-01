package com.dsckiet.covid_tracker_android_app.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.dsckiet.covid_tracker_android_app.adapter.StateAdapter
import com.dsckiet.covid_tracker_android_app.utils.*
import com.dsckiet.covid_tracker_android_app.viewModel.StateWiseTrackerViewModel
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.LargeValueFormatter
import kotlinx.android.synthetic.main.frag_india.*


class IndiaFragment : Fragment() {

    private lateinit var viewModel: StateWiseTrackerViewModel
    private lateinit var adapter: StateAdapter
    lateinit var navController: NavController
    private val entries = ArrayList<Entry>()
    private val xAxisLabel: ArrayList<String> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(
            com.dsckiet.covid_tracker_android_app.R.layout.frag_india,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        radiobutton.setOnCheckedChangeListener { group: RadioGroup, checkedId: Int ->
            if (checkedId == com.dsckiet.covid_tracker_android_app.R.id.rb_globe)
                navController.navigate(com.dsckiet.covid_tracker_android_app.R.id.action_frag_India_to_frag_Globe)
        }
        next_screen_btn.setOnClickListener {
            navController.navigate(com.dsckiet.covid_tracker_android_app.R.id.action_frag_India_to_stateListFragment)
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
        viewModel = ViewModelProvider(this).get(StateWiseTrackerViewModel::class.java)
        adapter = StateAdapter(requireContext())
        if (!InternetConnectivity.isNetworkAvailable(requireContext())!!)
            Toast.makeText(requireContext(), "Internet Unavailable", Toast.LENGTH_SHORT).show()
        viewModel.getCoronaStateDetails()
        recycler_view.adapter = adapter




        viewModel.showCoronaStateDetails.observe(viewLifecycleOwner, Observer {
            if (!swipeRefreshLayout.isRefreshing) {
                val lastUpdatedTime =
                    "Last Updated " + getPeriod(it[0].lastupdatedtime.toDateFormat()!!)
                last_updated.text = lastUpdatedTime
                confirmed_count.text = stringToNumberFormat(it[0].confirmed)
                recover_count.text = stringToNumberFormat(it[0].recovered)
                rip_count.text = stringToNumberFormat(it[0].deaths)
                active_count.text = stringToNumberFormat(it[0].active)
                confirmed_arrow_number.text = stringToNumberFormat(it[0].deltarecovered)
                recover_arrow_number.text = stringToNumberFormat(it[0].deltarecovered)
                rip_arrow_number.text = stringToNumberFormat(it[0].deltadeaths)
                adapter.setStateWiseTracker(it)
            }
        })
        viewModel.showCoronaIndiaLineChart.observe(viewLifecycleOwner, Observer {
            if (!swipeRefreshLayout.isRefreshing) {
                entries.clear()
                xAxisLabel.clear()
                current_date.text = it[it.size - 1].date
                confirmed_case.text = stringToNumberFormat(it[it.size - 1].totalconfirmed)
                confirmed_case_delta.text =
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
                    com.dsckiet.covid_tracker_android_app.R.layout.custom_marker_view
                )

                vl.color = Color.parseColor("#FFE0E6")
                vl.fillColor = Color.parseColor("#FFE0E6")
                vl.fillAlpha = Color.RED
                vl.setCircleColor(Color.parseColor("#FF063A"))
                vl.circleHoleColor = Color.parseColor("#FF063A")
                line_chart.description.text = ""
                line_chart.legend.isEnabled = false
                line_chart.axisRight.isEnabled = true
                line_chart.axisRight.setDrawGridLines(false)
                line_chart.axisRight.valueFormatter = LargeValueFormatter()
                line_chart.xAxis.setDrawGridLines(false)

                line_chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
                line_chart.axisLeft.isEnabled = false
                line_chart.axisRight.gridColor =
                    (Color.parseColor("#FF063A"))
                line_chart.xAxis.gridColor =
                    (Color.parseColor("#FF063A"))
                line_chart.xAxis.axisLineColor = (Color.parseColor("#FF063A"))
                line_chart.axisRight.axisLineColor = (Color.parseColor("#FF063A"))

                vl.setDrawCircles(true)

                line_chart.setDrawMarkers(true)
                line_chart.animateX(2000)
                line_chart.setDrawGridBackground(false)
                line_chart.isHighlightPerDragEnabled = true

                line_chart.data = LineData(vl)
                line_chart.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisLabel)
                line_chart.xAxis.setLabelCount(20, false)
                line_chart.xAxis.textSize = 6f
                line_chart.xAxis.textColor =
                    (Color.parseColor("#E86985"))
                line_chart.axisRight.textColor =
                    (Color.parseColor("#E86985"))
                line_chart.isDoubleTapToZoomEnabled = false
                line_chart.setScaleEnabled(false)

                line_chart.setTouchEnabled(true)
                line_chart.setPinchZoom(false)
                line_chart.markerView = mv

            }
        })

        viewModel.showProgress.observe(viewLifecycleOwner, Observer {
            swipeRefreshLayout.isRefreshing = it
        })
    }
}