package com.dsckiet.covid_tracker_android_app.ui.india

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
import com.dsckiet.covid_tracker_android_app.ui.adapter.DistrictAdapter
import com.dsckiet.covid_tracker_android_app.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_district_list.*
import kotlinx.android.synthetic.main.fragment_district_list.last_updated
import kotlinx.android.synthetic.main.fragment_district_list.swipeRefreshLayout


class DistrictListFragment : BaseFragment<DistrictWiseTrackerViewModel>() {
    lateinit var navController: NavController
    lateinit var adapter: DistrictAdapter
    private var stateCode: String? = null
    private var lastUpdatedTime: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_district_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = getViewModel()
        navController = Navigation.findNavController(view)
        back_button_district.setOnClickListener {
            navController.popBackStack()
        }
        stateCode = arguments?.getString("stateCode")
        lastUpdatedTime = arguments?.getString("lastUpdatedTime")

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getData()
        swipeRefreshLayout.setOnRefreshListener {
            getData()
        }

    }

    private fun getData() {
        mViewModel.getCoronaDistrictDetails()
        adapter = DistrictAdapter(requireContext())
        mViewModel.showProgress.observe(viewLifecycleOwner, Observer {
            swipeRefreshLayout.isRefreshing = it
        })
        recycler_view_district_list.adapter = adapter
        if (stateCode != null) {
            mViewModel.showDistrictWiseDetails.observe(viewLifecycleOwner, Observer {
                for (i in it.indices)
                    if (it[i].statecode == stateCode) {
                        if (lastUpdatedTime != null)
                            last_updated.text = lastUpdatedTime
                        state_name.text = it[i].state
                        collasping_toolbar.title = it[i].state
                        adapter.setDistrictWiseTracker(it[i].districtData)
                    }
            })
        }
    }

    override fun getViewModel(): DistrictWiseTrackerViewModel {
        return ViewModelProvider(this.requireActivity(), viewModelFactory).get(DistrictWiseTrackerViewModel::class.java)
    }


}