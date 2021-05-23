package com.dsckiet.covid_tracker_android_app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dsckiet.covid_tracker_android_app.R
import com.dsckiet.covid_tracker_android_app.models.Statewise
import com.dsckiet.covid_tracker_android_app.utils.stringToNumberFormat
import kotlinx.android.synthetic.main.listitem_countries.view.*
import kotlinx.android.synthetic.main.listitem_countries.view.A
import kotlinx.android.synthetic.main.listitem_countries.view.D

class StateAdapter(private val context: Context) :
    RecyclerView.Adapter<StateAdapter.ViewHolder>() {

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
                R.layout.listitem_countries,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return if (list.isEmpty())
            0
        else
            5

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        val item = list[position + 1]
        holder.active.text = stringToNumberFormat(item.active)
        holder.death.text = stringToNumberFormat(item.deaths)
        holder.recovered.text = stringToNumberFormat(item.recovered)
        holder.stateName.text = item.state
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val active: TextView = view.A
        val recovered: TextView = view.tv_r
        val death: TextView = view.D
        val stateName: TextView = view.area

    }
}