package com.dsckiet.covid_tracker_android_app.ui.mapview

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.dsckiet.covid_tracker_android_app.R
import com.dsckiet.covid_tracker_android_app.datastore.models.StateItemResponse
import com.dsckiet.covid_tracker_android_app.utils.logger
import org.greenrobot.eventbus.EventBus
import java.lang.ClassCastException
import java.util.ArrayList

class StateItemAdapter(
    private val list: ArrayList<String>,
    private val context: Context
) : RecyclerView.Adapter<StateItemAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.state_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.stringItem.text = list[position]
        val clickResponse = StateItemResponse(holder.stringItem.text.toString())
        holder.stringItem.setOnClickListener {
            EventBus.getDefault().post(clickResponse)
        }
    }

    override fun getItemCount(): Int = list.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val stringItem: AppCompatTextView = itemView.findViewById(R.id.state_item)
    }
}
