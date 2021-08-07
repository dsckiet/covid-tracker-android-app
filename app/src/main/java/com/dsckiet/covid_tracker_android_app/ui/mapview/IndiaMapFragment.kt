package com.dsckiet.covid_tracker_android_app.ui.mapview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.dsckiet.covid_tracker_android_app.R
import com.dsckiet.covid_tracker_android_app.databinding.FragmentIndiaMapBinding
import com.dsckiet.covid_tracker_android_app.datastore.models.Statewise
import com.dsckiet.covid_tracker_android_app.ui.base.BaseFragment
import com.dsckiet.covid_tracker_android_app.ui.india.StateWiseTrackerViewModel
import com.dsckiet.covid_tracker_android_app.utils.stringToNumberFormat
import com.richpath.RichPath
import kotlinx.android.synthetic.main.fragment_india_map.*


class IndiaMapFragment : BaseFragment<StateWiseTrackerViewModel>() {

    private lateinit var navController: NavController
    private lateinit var binding: FragmentIndiaMapBinding
    private val stateDataConfirmed = HashMap<String, Long>()
    private val stateDataActive = HashMap<String, Long>()
    private val stateDataDeaths = HashMap<String, Long>()
    private val stateDataRecovered = HashMap<String, Long>()
    private var boxSelectionConstant = 0

    private val bgColorList = listOf<Int>(
        R.color.light_blue,
        R.color.light_red,
        R.color.light_green,
        R.color.light_grey
    )
    private val baseColorList = listOf<Int>(
        R.color.blue,
        R.color.red,
        R.color.green,
        R.color.grey
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_india_map, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = getViewModel()
        navController = Navigation.findNavController(view)
        binding.icSearch.setOnClickListener{
            val bottomSheet = StateSearchBottomSheet()
            bottomSheet.show(parentFragmentManager, "bottom-sheet")
        }
        unColorRest()
        val defaultSelectionColor = ContextCompat.getColor(requireContext(), baseColorList[0])
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
        binding.stateCount.text = stringToNumberFormat(stateDataConfirmed[binding.stateName.text].toString()).plus(" confirmed cases")
        binding.categoryConfirmed.setOnClickListener {
            binding.confirmedShadow.visibility = View.VISIBLE
            binding.activeShadow.visibility = View.INVISIBLE
            binding.recoveredShadow.visibility = View.INVISIBLE
            binding.deathShadow.visibility = View.INVISIBLE
            boxSelectionConstant = 0
            unColorRest()
            val confirmedSelectionColor = ContextCompat.getColor(requireContext(), baseColorList[0])
            binding.icIndiaMap.findRichPathByName(binding.stateName.text.toString())?.fillColor = confirmedSelectionColor
            binding.stateName.setTextColor(confirmedSelectionColor)
            binding.stateName.text = binding.stateName.text.toString()
            setCaseCountData()
        }
        binding.categoryActive.setOnClickListener {
            binding.confirmedShadow.visibility = View.INVISIBLE
            binding.activeShadow.visibility = View.VISIBLE
            binding.recoveredShadow.visibility = View.INVISIBLE
            binding.deathShadow.visibility = View.INVISIBLE
            boxSelectionConstant = 1
            unColorRest()
            val activeSelectionColor = ContextCompat.getColor(requireContext(), baseColorList[1])
            binding.icIndiaMap.findRichPathByName(binding.stateName.text.toString())?.fillColor = activeSelectionColor
            binding.stateName.setTextColor(activeSelectionColor)
            binding.stateName.text = binding.stateName.text.toString()
            setCaseCountData()
        }
        binding.categoryRecovered.setOnClickListener {
            binding.confirmedShadow.visibility = View.INVISIBLE
            binding.activeShadow.visibility = View.INVISIBLE
            binding.recoveredShadow.visibility = View.VISIBLE
            binding.deathShadow.visibility = View.INVISIBLE
            boxSelectionConstant = 2
            unColorRest()
            val recoveredSelectionColor = ContextCompat.getColor(requireContext(), baseColorList[2])
            binding.icIndiaMap.findRichPathByName(binding.stateName.text.toString())?.fillColor = recoveredSelectionColor
            binding.stateName.setTextColor(recoveredSelectionColor)
            binding.stateName.text = binding.stateName.text.toString()
            setCaseCountData()
        }
        binding.categoryDeaths.setOnClickListener {
            binding.confirmedShadow.visibility = View.INVISIBLE
            binding.activeShadow.visibility = View.INVISIBLE
            binding.recoveredShadow.visibility = View.INVISIBLE
            binding.deathShadow.visibility = View.VISIBLE
            boxSelectionConstant = 3
            unColorRest()
            val deathsSelectionColor = ContextCompat.getColor(requireContext(), baseColorList[3])
            binding.icIndiaMap.findRichPathByName(binding.stateName.text.toString())?.fillColor = deathsSelectionColor
            binding.stateName.setTextColor(deathsSelectionColor)
            binding.stateName.text = binding.stateName.text.toString()
            setCaseCountData()
        }
        stateAreaClickEventListener()
    }

    private fun stateAreaClickEventListener() {
        val confirmedColor = ContextCompat.getColor(requireContext(), baseColorList[0])
        val activeColor = ContextCompat.getColor(requireContext(), baseColorList[1])
        val recoveredColor = ContextCompat.getColor(requireContext(), baseColorList[2])
        val deathColor = ContextCompat.getColor(requireContext(), baseColorList[3])
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
            setCaseCountData()
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

    private fun setCaseCountData() {
        when(boxSelectionConstant) {
            0 -> binding.stateCount.text = stringToNumberFormat(stateDataConfirmed[binding.stateName.text].toString()).plus(" confirmed cases")
            1 -> binding.stateCount.text = stringToNumberFormat(stateDataActive[binding.stateName.text].toString()).plus(" active cases")
            2 -> binding.stateCount.text = stringToNumberFormat(stateDataRecovered[binding.stateName.text].toString()).plus(" recovered cases")
            3 -> binding.stateCount.text = stringToNumberFormat(stateDataDeaths[binding.stateName.text].toString()).plus(" deceased cases")
            else -> binding.stateCount.text = stringToNumberFormat(stateDataConfirmed[binding.stateName.text].toString()).plus(" confirmed cases")
        }
    }

    override fun getViewModel(): StateWiseTrackerViewModel {
        return ViewModelProvider(this.requireActivity(), viewModelFactory).get(StateWiseTrackerViewModel::class.java)
    }
}