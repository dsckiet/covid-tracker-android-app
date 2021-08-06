package com.dsckiet.covid_tracker_android_app.ui.mapview

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.dsckiet.covid_tracker_android_app.R
import com.dsckiet.covid_tracker_android_app.databinding.FragmentIndiaMapBinding
import com.dsckiet.covid_tracker_android_app.datastore.models.Statewise
import com.dsckiet.covid_tracker_android_app.ui.adapter.StateAdapter
import com.dsckiet.covid_tracker_android_app.ui.base.BaseFragment
import com.dsckiet.covid_tracker_android_app.utils.*
import com.dsckiet.covid_tracker_android_app.ui.india.StateWiseTrackerViewModel
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.LargeValueFormatter
import com.richpath.RichPath
import kotlinx.android.synthetic.main.fragment_india.*
import kotlinx.android.synthetic.main.fragment_india_map.*


class IndiaMapFragment : BaseFragment<StateWiseTrackerViewModel>() {

    private lateinit var adapter: StateAdapter
    private lateinit var navController: NavController
    private val entries = ArrayList<Entry>()
    private val xAxisLabel: ArrayList<String> = ArrayList()
    private lateinit var binding: FragmentIndiaMapBinding
    private val stateDataConfirmed = HashMap<String, Long>()
    private val stateDataActive = HashMap<String, Long>()
    private val stateDataDeaths = HashMap<String, Long>()
    private val stateDataRecovered = HashMap<String, Long>()
    private var boxSelectionConstant = 0

    private val bgColorList = listOf<Int>(
        com.dsckiet.covid_tracker_android_app.R.color.light_blue,
        com.dsckiet.covid_tracker_android_app.R.color.light_red,
        com.dsckiet.covid_tracker_android_app.R.color.light_green,
        com.dsckiet.covid_tracker_android_app.R.color.light_grey
    )
    private val baseColorList = listOf<Int>(
        com.dsckiet.covid_tracker_android_app.R.color.blue,
        com.dsckiet.covid_tracker_android_app.R.color.red,
        com.dsckiet.covid_tracker_android_app.R.color.green,
        com.dsckiet.covid_tracker_android_app.R.color.grey
    )


    private val bgColorGraph = bgColorList[0]
    private val baseColorGraph = baseColorList[0]

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_india_map, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = getViewModel()
        navController = Navigation.findNavController(view)
        unColorRest()
        val defaultSelectionColor = ContextCompat.getColor(requireContext(), baseColorGraph)
        binding.icIndiaMap.findRichPathByName("Haryana")?.fillColor = defaultSelectionColor
        binding.stateName.setTextColor(defaultSelectionColor)
        binding.stateName.text = "Haryana"
        val data: List<Statewise>? = mViewModel.showCoronaStateDetails.value
        for(value in data!!) {
            val confirmed: Long = value.confirmed.toLong()
            val active: Long = value.active.toLong()
            val deaths: Long = value.deaths.toLong()
            val recovered: Long = value.recovered.toLong()
            val state = value.state
            stateDataActive[state] = active
            stateDataConfirmed[state] = confirmed
            stateDataDeaths[state] = deaths
            stateDataRecovered[state] = recovered
        }
        binding.stateCount.text = stringToNumberFormat(stateDataConfirmed[binding.stateName.text].toString())
        binding.categoryConfirmed.setOnClickListener {
            binding.confirmedShadow.visibility = View.VISIBLE
            binding.activeShadow.visibility = View.INVISIBLE
            binding.recoveredShadow.visibility = View.INVISIBLE
            binding.deathShadow.visibility = View.INVISIBLE
            boxSelectionConstant = 0
            val defaultSelectionColor = ContextCompat.getColor(requireContext(), baseColorGraph)
            binding.icIndiaMap.findRichPathByName("Haryana")?.fillColor = defaultSelectionColor
            binding.stateName.setTextColor(defaultSelectionColor)
            binding.stateName.text = "Haryana"
        }
        binding.categoryActive.setOnClickListener {
            binding.confirmedShadow.visibility = View.INVISIBLE
            binding.activeShadow.visibility = View.VISIBLE
            binding.recoveredShadow.visibility = View.INVISIBLE
            binding.deathShadow.visibility = View.INVISIBLE
            boxSelectionConstant = 1
        }
        binding.categoryRecovered.setOnClickListener {
            binding.confirmedShadow.visibility = View.INVISIBLE
            binding.activeShadow.visibility = View.INVISIBLE
            binding.recoveredShadow.visibility = View.VISIBLE
            binding.deathShadow.visibility = View.INVISIBLE
            boxSelectionConstant = 2
        }
        binding.categoryDeaths.setOnClickListener {
            binding.confirmedShadow.visibility = View.INVISIBLE
            binding.activeShadow.visibility = View.INVISIBLE
            binding.recoveredShadow.visibility = View.INVISIBLE
            binding.deathShadow.visibility = View.VISIBLE
            boxSelectionConstant = 3
        }
        stateAreaClickEventListener()
    }

    private fun stateAreaClickEventListener() {
        val confirmedColor = ContextCompat.getColor(requireContext(), bgColorList[0])
        val activeColor = ContextCompat.getColor(requireContext(), bgColorList[1])
        val recoveredColor = ContextCompat.getColor(requireContext(), bgColorList[2])
        val deathColor = ContextCompat.getColor(requireContext(), bgColorList[3])
        ic_india_map.setOnPathClickListener(RichPath.OnPathClickListener { richPath ->
            unColorRest()
            when(boxSelectionConstant) {
                0 -> {
                    richPath.fillColor = confirmedColor
                    binding.stateName.setTextColor(confirmedColor)
                }
                1 -> {
                    richPath.fillColor = activeColor
                    binding.stateName.setTextColor(activeColor)
                }
                2 -> {
                    richPath.fillColor = recoveredColor
                    binding.stateName.setTextColor(recoveredColor)
                }
                3 -> {
                    richPath.fillColor = deathColor
                    binding.stateName.setTextColor(deathColor)
                }
                else -> {
                    richPath.fillColor = confirmedColor
                    binding.stateName.setTextColor(confirmedColor)
                }
            }

            when (richPath.name) {
                "Andhra Pradesh" -> {
                    state_name.text = "Andhra Pradhesh"
                }
                "Tamil Nadu" -> {
                    state_name.text = "Tamil Nadu"
                }
                "Rajasthan" -> {
                    state_name.text = "Rajasthan"
                }
                "Andaman and Nicobar" -> {
                    state_name.text = "Andaman and Nicobar"
                }
                "Arunachal Pradesh" -> {
                    state_name.text = "Arunachal Pradesh"
                }
                "Assam" -> {
                    state_name.text = "Assam"
                }
                "Bihar" -> {
                    state_name.text = "Bihar"

                }
                "Chhattisgarh" -> {
                    state_name.text = "Chhattisgarh"
                }
                "Goa" -> {
                    state_name.text = "Goa"
                }
                "Gujarat" -> {
                    state_name.text = "Gujarat"
                }
                "Himachal Pradesh" -> {
                    state_name.text = "Himachal Pradesh"
                }
                "Haryana" -> {
                    state_name.text = "Haryana"
                }
                "Jharkhand" -> {
                    state_name.text = "Jharkhand"
                }
                "Jammu and Kashmir" -> {
                    state_name.text = "Jammu and Kashmir"
                }
                "Karnataka" -> {
                    state_name.text = "Karnataka"
                }
                "Kerala" -> {
                    state_name.text = "Kerala"
                }
                "Maharashtra" -> {
                    state_name.text = "Maharashtra"
                }
                "Meghalaya" -> {
                    state_name.text = "Meghalaya"
                }
                "Manipur" -> {
                    state_name.text = "Manipur"
                }
                "Madhya Pradesh" -> {
                    state_name.text = "Madhya Pradesh"
                }
                "Mizoram" -> {
                    state_name.text = "Mizoram"
                }
                "Odisha" -> {
                    state_name.text = "Odisha"
                }
                "Punjab" -> {
                    state_name.text = "Punjab"
                }
                "Sikkim" -> {
                    state_name.text = "Sikkim"
                }
                "Telangana" -> {
                    state_name.text = "Telangana"
                }
                "Tamil Nadu" -> {
                    state_name.text = "Tamil Nadu"
                }
                "Tripura" -> {
                    state_name.text = "Tripura"
                }
                "Uttar Pradesh" -> {
                    state_name.text = "Uttar Pradesh"
                }
                "Uttarakhand" -> {
                    state_name.text = "Uttarakhand"
                }
                "West Bengal" -> {
                    state_name.text = "West Bengal"
                }
                "Nagaland" -> {
                    state_name.text = "Nagaland"
                }
            }
            when(boxSelectionConstant) {
                0 -> binding.stateCount.text = stringToNumberFormat(stateDataConfirmed[binding.stateName.text].toString())
                1 -> binding.stateCount.text = stringToNumberFormat(stateDataActive[binding.stateName.text].toString())
                2 -> binding.stateCount.text = stringToNumberFormat(stateDataRecovered[binding.stateName.text].toString())
                3 -> binding.stateCount.text = stringToNumberFormat(stateDataDeaths[binding.stateName.text].toString())
                else -> binding.stateCount.text = stringToNumberFormat(stateDataConfirmed[binding.stateName.text].toString())
            }
        })
    }

    private fun unColorRest() {
        when(boxSelectionConstant) {
            0 -> {
                for (richPath: RichPath in ic_india_map.findAllRichPaths()) {
                    richPath.fillColor = ContextCompat.getColor(requireContext(), bgColorList[0])
                }
            }
            1 -> {
                for (richPath: RichPath in ic_india_map.findAllRichPaths()) {
                    richPath.fillColor = ContextCompat.getColor(requireContext(), bgColorList[1])
                }
            }
            2 -> {
                for (richPath: RichPath in ic_india_map.findAllRichPaths()) {
                    richPath.fillColor = ContextCompat.getColor(requireContext(), bgColorList[2])
                }
            }
            3 -> {
                for (richPath: RichPath in ic_india_map.findAllRichPaths()) {
                    richPath.fillColor = ContextCompat.getColor(requireContext(), bgColorList[3])
                }
            }
            else -> {
                for (richPath: RichPath in ic_india_map.findAllRichPaths()) {
                    richPath.fillColor = ContextCompat.getColor(requireContext(), bgColorList[0])
                }
            }
        }
    }

    private fun getData() {
        mViewModel = ViewModelProvider(this).get(StateWiseTrackerViewModel::class.java)
        adapter = StateAdapter(requireContext())
        if (!InternetConnectivity.isNetworkAvailable(requireContext())!!)
            Toast.makeText(requireContext(), "Internet Unavailable", Toast.LENGTH_SHORT).show()
        mViewModel.getCoronaStateDetails()
        recycler_view.adapter = adapter




        mViewModel.showCoronaStateDetails.observe(viewLifecycleOwner, Observer {
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
        mViewModel.showCoronaIndiaLineChart.observe(viewLifecycleOwner, Observer {
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

        mViewModel.showProgress.observe(viewLifecycleOwner, Observer {
            swipeRefreshLayout.isRefreshing = it
        })
    }

    override fun getViewModel(): StateWiseTrackerViewModel {
        return ViewModelProvider(this.requireActivity(), viewModelFactory).get(StateWiseTrackerViewModel::class.java)
    }
}