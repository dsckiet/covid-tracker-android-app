package com.dsckiet.covid_tracker_android_app.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.listitem_countries.view.*

class Adapter1(con: Context, private val elist: List<Item1>) :
    RecyclerView.Adapter<Adapter1.EVH>() {

    private val c: Context = con
    private val e = elist

    class EVH(i: View) : RecyclerView.ViewHolder(i) {
        val country: TextView = i.country
        val active: TextView = i.A
        val recovered: TextView = i.R
        val death: TextView = i.D
        val res: RelativeLayout = i.item1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EVH {
        val p = LayoutInflater.from(parent.context).inflate(
            com.dsckiet.covid_tracker_android_app.R.layout.listitem_countries,
            parent,
            false
        )
        return EVH(p)
    }

    override fun getItemCount() = 5

    @Suppress("NAME_SHADOWING")
    override fun onBindViewHolder(holder: EVH, position: Int) {
        val currentitem = elist[position]
        holder.country.text = currentitem.country
        holder.active.text = currentitem.active.toString()
        holder.recovered.text = currentitem.recovered.toString()
        holder.death.text = currentitem.death.toString()


    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}