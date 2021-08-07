package com.dsckiet.covid_tracker_android_app.utils

import android.app.Activity
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.dsckiet.covid_tracker_android_app.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

private const val TAG = "Debugger"
fun logger(msg: String) = Log.d(TAG, msg)

fun setupFullHeight(bottomSheetDialog: BottomSheetDialog, activity: Activity) {
    val bottomSheet = bottomSheetDialog.findViewById<FrameLayout>(R.id.design_bottom_sheet)
    val behavior: BottomSheetBehavior<View> = BottomSheetBehavior.from(bottomSheet!!)
    val layoutParams = bottomSheet.layoutParams
    val windowHeight = getWindowHeight(activity)
    if (layoutParams != null) {
        layoutParams.height = windowHeight
    }
    bottomSheet.layoutParams = layoutParams
    behavior.state = BottomSheetBehavior.STATE_EXPANDED
}

private fun getWindowHeight(activity: Activity): Int {
    val displayMetrics = DisplayMetrics()
    activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics.heightPixels
}