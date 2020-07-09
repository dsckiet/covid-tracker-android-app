package com.dsckiet.covid_tracker_android_app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dsckiet.covid_tracker_android_app.R
import com.dsckiet.covid_tracker_android_app.models.DistrictData
import com.dsckiet.covid_tracker_android_app.utils.stringToNumberFormat
import kotlinx.android.synthetic.main.listitem_complete_list.view.*

class DistrictAdapter(private val context: Context) :
    RecyclerView.Adapter<DistrictAdapter.ViewHolder>() {
    private var list: List<DistrictData> = ArrayList()
    private var sortedList: List<DistrictData> = ArrayList()
    fun setDistrictWiseTracker(list: List<DistrictData>) {
        this.list = list
        if (list.isNotEmpty()) {
            sortedList = list.sortedByDescending {
                it.confirmed
            }
        }

        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.listitem_complete_list,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return sortedList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = sortedList[position]
        holder.cityName.text = item.district
        holder.active.text = stringToNumberFormat(item.active.toString())
        holder.death.text = stringToNumberFormat(item.deceased.toString())
        holder.confirmed.text = stringToNumberFormat(item.confirmed.toString())
        holder.recovered.text = stringToNumberFormat(item.recovered.toString())
        holder.confirmedDelta.text = stringToNumberFormat(item.delta.confirmed.toString())
        holder.recoveredDelta.text = stringToNumberFormat(item.delta.recovered.toString())
        holder.deathDelta.text = stringToNumberFormat(item.delta.deceased.toString())
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cityName: TextView = view.country_name
        val active: TextView = view.active_case_list
        val confirmed: TextView = view.confirmed_case_list
        val confirmedDelta: TextView = view.confirmed_case_delta_list
        val recoveredDelta: TextView = view.recovered_case_delta_list
        val deathDelta: TextView = view.death_case_delta_list
        val recovered: TextView = view.recovered_case_list
        val death: TextView = view.death_case_list
    }
}
