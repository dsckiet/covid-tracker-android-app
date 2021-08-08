package com.dsckiet.covid_tracker_android_app.ui.mapview

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.DialogInterface.OnShowListener
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.dsckiet.covid_tracker_android_app.R
import com.dsckiet.covid_tracker_android_app.constants.Constants
import com.dsckiet.covid_tracker_android_app.databinding.BottomSheetStateSearchBinding
import com.dsckiet.covid_tracker_android_app.utils.setupFullHeight
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.ArrayList

class StateSearchBottomSheet : BottomSheetDialogFragment() {
    private var itemList = ArrayList<String>()
    private val stateListData = ArrayList<String>()
    private lateinit var binding: BottomSheetStateSearchBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_state_search, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stateListData.clear()
        addListData()
        val adapter = StateItemAdapter(itemList, requireContext())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)
        stateListData.addAll(itemList)
        searchFieldObserver(binding.etSearchBar)
    }

    private fun addListData() {
        itemList.clear()
        itemList.add("Andhra Pradesh")
        itemList.add("Andaman and Nicobar")
        itemList.add("Arunachal Pradesh")
        itemList.add("Assam")
        itemList.add("Bihar")
        itemList.add("Chattisgarh")
        itemList.add("Goa")
        itemList.add("Gujarat")
        itemList.add("Himachal Pradesh")
        itemList.add("Haryana")
        itemList.add("Jharkhand")
        itemList.add("Jammu and Kashmir")
        itemList.add("Karnataka")
        itemList.add("Kerala")
        itemList.add("Maharashtra")
        itemList.add("Meghalaya")
        itemList.add("Manipur")
        itemList.add("Madhya Pradesh")
        itemList.add("Mizoram")
        itemList.add("Nagaland")
        itemList.add("Odisha")
        itemList.add("Punjab")
        itemList.add("Rajasthan")
        itemList.add("Sikkim")
        itemList.add("Tamil Nadu")
        itemList.add("Tripura")
        itemList.add("Telangana")
        itemList.add("Uttarakhand")
        itemList.add("Uttar Pradesh")
        itemList.add("West Bengal")
    }

    private fun searchFieldObserver(etSearch: EditText) {
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                binding.searchProgress.visibility = View.VISIBLE
                binding.noDataFound.visibility = View.GONE
                Handler().postDelayed({
                    filter(s.toString())
                    binding.searchProgress.visibility = View.GONE
                    if (itemList.size == 0) binding.noDataFound.visibility = View.VISIBLE
                }, Constants.SEARCH_DELAY.toLong())
            }
        })
    }

    fun filter(text: String) {
        val temp = ArrayList<String>()
        for (it in stateListData) {
            if (it.toLowerCase().contains(text.toLowerCase())) temp.add(it)
        }
        itemList = temp
        binding.recyclerView.adapter = StateItemAdapter(itemList, context!!)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener { dialogInterface: DialogInterface? ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            setupFullHeight(bottomSheetDialog, context as Activity)
        }
        return dialog
    }
}