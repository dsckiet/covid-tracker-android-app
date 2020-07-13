package com.dsckiet.covid_tracker_android_app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dsckiet.covid_tracker_android_app.R
import com.dsckiet.covid_tracker_android_app.models.Country
import com.dsckiet.covid_tracker_android_app.utils.stringToNumberFormat
import kotlinx.android.synthetic.main.listitem_countries.view.*

class CountriesAdapter(private val context: Context) :
    RecyclerView.Adapter<CountriesAdapter.ViewHolder>() {

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
                R.layout.listitem_countries,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {

        return if (sortedList.isEmpty())
            0
        else
            5


    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var item = sortedList[position]
        val totalActive = item.totalConfirmed - item.totalDeaths - item.totalRecovered
        holder.active.text = stringToNumberFormat(totalActive.toString())
        holder.recovered.text = stringToNumberFormat(item.totalRecovered.toString())
        holder.death.text = stringToNumberFormat(item.totalDeaths.toString())
        holder.stateName.text = stringToNumberFormat(item.country)

    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val active: TextView = view.A
        val recovered: TextView = view.R
        val death: TextView = view.D
        val stateName: TextView = view.area

    }
}