package com.dsckiet.covid_tracker_android_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation

import com.dsckiet.covid_tracker_android_app.adapter.StateAdapter
import com.dsckiet.covid_tracker_android_app.utils.InternetConnectivity
import com.dsckiet.covid_tracker_android_app.utils.getPeriod
import com.dsckiet.covid_tracker_android_app.utils.toDateFormat
import com.dsckiet.covid_tracker_android_app.viewModel.StateWiseTrackerViewModel
import kotlinx.android.synthetic.main.frag_globe.*
import kotlinx.android.synthetic.main.frag_india.*
import kotlinx.android.synthetic.main.frag_india.active_count
import kotlinx.android.synthetic.main.frag_india.confirmed_count
import kotlinx.android.synthetic.main.frag_india.last_updated
import kotlinx.android.synthetic.main.frag_india.radiobutton
import kotlinx.android.synthetic.main.frag_india.recycler_view
import kotlinx.android.synthetic.main.frag_india.rip_count


class IndiaFragment : Fragment() {

    private lateinit var viewModel: StateWiseTrackerViewModel
    private lateinit var adapter: StateAdapter
    lateinit var navController: NavController
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
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(StateWiseTrackerViewModel::class.java)
        adapter = StateAdapter(requireContext())
        if (!InternetConnectivity.isNetworkAvailable(requireContext())!!)
            Toast.makeText(requireContext(), "Internet Unavailable", Toast.LENGTH_SHORT).show()
        viewModel.getCoronaStateDetails()
        recycler_view.adapter = adapter
        viewModel.showCoronaStateDetails.observe(viewLifecycleOwner, Observer {
            val lastUpdatedTime =
                "Last Updated " + getPeriod(it[0].lastupdatedtime?.toDateFormat()!!)
            last_updated.text = lastUpdatedTime
            confirmed_count.text = it[0].confirmed
            recover_count.text = it[0].recovered
            rip_count.text = it[0].deaths
            active_count.text = it[0].active
            adapter.setStateWiseTracker(it)
        })
    }
}