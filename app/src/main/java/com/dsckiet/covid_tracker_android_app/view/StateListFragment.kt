package com.dsckiet.covid_tracker_android_app.view

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
import com.dsckiet.covid_tracker_android_app.adapter.StateAdapter
import com.dsckiet.covid_tracker_android_app.adapter.StateCompleteListAdapter
import com.dsckiet.covid_tracker_android_app.viewModel.StateWiseTrackerViewModel
import kotlinx.android.synthetic.main.fragment_country_list.*
import kotlinx.android.synthetic.main.fragment_country_list.back_button
import kotlinx.android.synthetic.main.fragment_state_list.*


class StateListFragment : Fragment() {
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
        navController = Navigation.findNavController(view)
        back_button.setOnClickListener {
            navController.popBackStack()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(StateWiseTrackerViewModel::class.java)
        adapter = StateCompleteListAdapter(requireContext())
        viewModel.getCoronaStateDetails()
        recycler_view_state_list.adapter = adapter
        viewModel.showCoronaStateDetails.observe(viewLifecycleOwner, Observer {
            adapter.setStateWiseTracker(it)
        })

    }

}