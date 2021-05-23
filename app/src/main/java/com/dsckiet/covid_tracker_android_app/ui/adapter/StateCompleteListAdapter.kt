package com.dsckiet.covid_tracker_android_app.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.dsckiet.covid_tracker_android_app.R
import com.dsckiet.covid_tracker_android_app.datastore.models.Statewise
import com.dsckiet.covid_tracker_android_app.utils.getPeriod
import com.dsckiet.covid_tracker_android_app.utils.stringToNumberFormat
import com.dsckiet.covid_tracker_android_app.utils.toDateFormat
import kotlinx.android.synthetic.main.listitem_state_list.view.*

class StateCompleteListAdapter(private val context: Context) :
    RecyclerView.Adapter<StateCompleteListAdapter.ViewHolder>() {

    private var list: List<Statewise> = ArrayList()
    fun setStateWiseTracker(list: List<Statewise>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.listitem_state_list,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        if (position < list.size - 2) {
            val item = list[position + 1]
            holder.active.text = stringToNumberFormat(item!!.active)
            holder.death.text = stringToNumberFormat(item.deaths)
            holder.confirmed.text = stringToNumberFormat(item.confirmed)
            holder.recovered.text = stringToNumberFormat(item.recovered)
            holder.stateName.text = item.state
            holder.confirmedDelta.text = stringToNumberFormat(item.deltaconfirmed)
            holder.deathDelta.text = stringToNumberFormat(item.deltadeaths)
            holder.recoveredDelta.text = stringToNumberFormat(item.deltarecovered)
            val lastUpdatedTime =
                "Last Updated " + getPeriod(item.lastupdatedtime.toDateFormat()!!)
            holder.lastUpdatedTime.text = lastUpdatedTime
            val stateCode = item.statecode
            val bundle = bundleOf("stateCode" to stateCode, "lastUpdatedTime" to lastUpdatedTime)
            holder.nextButton.setOnClickListener {
                it.findNavController()
                    .navigate(R.id.action_stateListFragment_to_districtListFragment, bundle)

            }

        }
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val active: TextView = view.active_case_list
        val recovered: TextView = view.recovered_case_list
        val death: TextView = view.death_case_list
        val stateName: TextView = view.country_name
        val recoveredDelta: TextView = view.recovered_case_delta_list
        val deathDelta: TextView = view.death_case_delta_list
        val confirmed: TextView = view.confirmed_case_list
        val confirmedDelta: TextView = view.confirmed_case_delta_list

        val lastUpdatedTime: TextView = view.last_updated_time_state
        val nextButton: CardView = view.card_view_state

    }
}