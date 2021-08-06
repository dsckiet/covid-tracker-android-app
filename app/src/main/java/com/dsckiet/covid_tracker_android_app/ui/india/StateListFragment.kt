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
import com.dsckiet.covid_tracker_android_app.ui.adapter.StateCompleteListAdapter
import com.dsckiet.covid_tracker_android_app.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_country_list.back_button
import kotlinx.android.synthetic.main.fragment_state_list.*


class StateListFragment : BaseFragment<StateWiseTrackerViewModel>() {
    private lateinit var viewModel: StateWiseTrackerViewModel
    private lateinit var adapter: StateCompleteListAdapter
    lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_state_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = getViewModel()
        navController = Navigation.findNavController(view)
        back_button.setOnClickListener {
            navController.popBackStack()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getData()
        swipeRefreshLayoutState.setOnRefreshListener {
            getData()
        }


    }

    private fun getData() {
        adapter = StateCompleteListAdapter(requireContext())


        viewModel.getCoronaStateDetails()

        recycler_view_state_list.adapter = adapter
        viewModel.showCoronaStateDetails.observe(viewLifecycleOwner, Observer {
            adapter.setStateWiseTracker(it)
        })
        swipeRefreshLayoutState.isRefreshing = false

        viewModel.showProgress.observe(viewLifecycleOwner, Observer {
            swipeRefreshLayoutState.isRefreshing = it

        })
        viewModel.getCoronaStateDetails()

        recycler_view_state_list.adapter = adapter
        viewModel.showCoronaStateDetails.observe(viewLifecycleOwner, Observer {
            adapter.setStateWiseTracker(it)
        })
    }

    override fun getViewModel(): StateWiseTrackerViewModel {
        return ViewModelProvider(this.requireActivity(), viewModelFactory).get(StateWiseTrackerViewModel::class.java)
    }
}