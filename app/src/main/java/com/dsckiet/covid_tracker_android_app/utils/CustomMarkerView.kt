package com.dsckiet.covid_tracker_android_app.utils

import android.content.Context
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import kotlinx.android.synthetic.main.custom_marker_view.view.*


class CustomMarkerView(context: Context, layoutResource: Int) :
    MarkerView(context, layoutResource) {
    override fun refreshContent(
        e: Entry,
        highlight: Highlight?
    ) {
        tvContent.text = "" + e.y

        // this will perform necessary layouting
        super.refreshContent(e, highlight)
    }

    private var mOffset: MPPointF? = null

    override fun getOffset(): MPPointF? {
        if (mOffset == null) {
            // center the marker horizontally and vertically
            mOffset = MPPointF((-(width / 2)).toFloat(), (-height).toFloat())
        }
        return mOffset
    }

}