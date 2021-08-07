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
        itemList.add("[AD] - Andhra Pradesh")
        itemList.add("[AN] - Andaman and Nicobar Islands")
        itemList.add("[AR] - Arunachal Pradesh")
        itemList.add("[AS] - Assam")
        itemList.add("[BR] - Bihar")
        itemList.add("[CG] - Chattisgarh")
        itemList.add("[CH] - Chandigarh")
        itemList.add("[DD] - Daman and Diu")
        itemList.add("[DL] - Delhi")
        itemList.add("[DN] - Dadra and Nagar Haveli")
        itemList.add("[GA] - Goa")
        itemList.add("[GJ] - Gujarat")
        itemList.add("[HP] - Himachal Pradesh")
        itemList.add("[HR] - Haryana")
        itemList.add("[JH] - Jharkhand")
        itemList.add("[JK] - Jammu and Kashmir")
        itemList.add("[KA] - Karnataka")
        itemList.add("[KL] - Kerala")
        itemList.add("[LA] - Ladakh")
        itemList.add("[LD] - Lakshadweep")
        itemList.add("[MH] - Maharashtra")
        itemList.add("[ML] - Meghalaya")
        itemList.add("[MN] - Manipur")
        itemList.add("[MP] - Madhya Pradesh")
        itemList.add("[MZ] - Mizoram")
        itemList.add("[NL] - Nagaland")
        itemList.add("[OD] - Odisha")
        itemList.add("[OT] - Other Territory")
        itemList.add("[PB] - Punjab")
        itemList.add("[PY] - Puducherry")
        itemList.add("[RJ] - Rajasthan")
        itemList.add("[SK] - Sikkim")
        itemList.add("[TN] - Tamil Nadu")
        itemList.add("[TR] - Tripura")
        itemList.add("[TS] - Telangana")
        itemList.add("[UK] - Uttarakhand")
        itemList.add("[UP] - Uttar Pradesh")
        itemList.add("[WB] - West Bengal")
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