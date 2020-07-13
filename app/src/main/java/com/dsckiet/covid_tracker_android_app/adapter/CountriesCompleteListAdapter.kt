package com.dsckiet.covid_tracker_android_app.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dsckiet.covid_tracker_android_app.R
import com.dsckiet.covid_tracker_android_app.models.Country
import com.dsckiet.covid_tracker_android_app.utils.getPeriod
import com.dsckiet.covid_tracker_android_app.utils.globalTimeDateFormat
import com.dsckiet.covid_tracker_android_app.utils.stringToNumberFormat
import kotlinx.android.synthetic.main.listitem_complete_list.view.*

class CountriesCompleteListAdapter(private val context: Context) :
    RecyclerView.Adapter<CountriesCompleteListAdapter.ViewHolder>() {

    private var list: List<Country> = ArrayList()
    private var sortedList: List<Country> = ArrayList()
    fun setCountryWiseTracker(list: List<Country>) {
        this.list = list
        if (list.isNotEmpty()) {
            sortedList = list.sortedByDescending {
                it.totalConfirmed
            }
        }
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
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
        val totalActive = item.totalConfirmed - item.totalDeaths - item.totalRecovered
        holder.active.text = stringToNumberFormat(totalActive.toString())
        holder.recovered.text = stringToNumberFormat(item.totalRecovered.toString())
        holder.death.text = stringToNumberFormat(item.totalDeaths.toString())
        holder.countryName.text = item.country
        holder.confirmedDelta.text = stringToNumberFormat(item.newConfirmed.toString())
        holder.deathDelta.text = stringToNumberFormat(item.newDeaths.toString())
        holder.recoveredDelta.text = stringToNumberFormat(item.newRecovered.toString())
        holder.confirmed.text = stringToNumberFormat(item.totalConfirmed.toString())


    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val countryName: TextView = view.country_name
        val active: TextView = view.active_case_list
        val confirmed: TextView = view.confirmed_case_list
        val confirmedDelta: TextView = view.confirmed_case_delta_list
        val recoveredDelta: TextView = view.recovered_case_delta_list
        val deathDelta: TextView = view.death_case_delta_list
        val recovered: TextView = view.recovered_case_list
        val death: TextView = view.death_case_list


    }
}