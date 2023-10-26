package com.stetig.solitaire.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.*
import com.google.android.material.textfield.TextInputLayout
import com.stetig.callsync.base.BaseFragment
import com.stetig.solitaire.R
import com.stetig.solitaire.activity.MainActivity
import com.stetig.solitaire.api.Keys
import com.stetig.solitaire.databinding.FeedbackFormBinding


class FeedBackFragment : BaseFragment() {

    lateinit var activity: MainActivity
    lateinit var binding: FeedbackFormBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = inflate(inflater, R.layout.feedback_form, container, false)
        initView(binding.root)
        return binding.root
    }

//    val visitedAddMap = mutableMapOf(
//        "Visited At" to ,
//        "Ethnicity" to arrayOf("b"),
//        "Budget" to arrayOf("c"),
//        "Zone" to arrayOf("d"),
//    )

//    val headings = arrayOf("Visited At", "Ethnicity", "Budget", "Zone")
//    val data = arrayOf(
//        arrayOf(),
//        arrayOf(
//            "Bengali", "Christian", "Gujarati", "Marathi", "Marwari", "Muslim",
//            "North Indian Hindu", "Parsi", "Punjabi", "Sindhi", "South Indian Hindu"
//        ),
//        arrayOf("80 Lacs & Below", "80 L - 90 L", "90 L – 01 Cr", "01 Cr – 01.10 Cr", "01.10 Cr – 01.40 Cr", "01.40 Cr +"),
//        arrayOf("A", "B", "C", "D", "E", "Any")
//    )


    override fun initView(rootView: View?) {



        /// For Visit At
        val visitedAddAutoComplete = binding.autovisitedat
        val visitedAutoCompleteAdapter = ArrayAdapter(activity, android.R.layout.simple_spinner_item, arrayOf("Site", "Outbound", "Outbound - Outstation", "E-Visit"))
        visitedAddAutoComplete.threshold = 3
        visitedAddAutoComplete.setAdapter(visitedAutoCompleteAdapter)
        visitedAddAutoComplete.onItemClickListener = OnItemClickListener { parent, view_, position, _ ->

        }


        /// For Ethnicity
        val enthicityAutoCompleteTextView = binding.autoethnicity
        val listOfEthnicity = arrayOf( "Bengali", "Christian", "Gujarati", "Marathi", "Marwari", "Muslim", "North Indian Hindu", "Parsi", "Punjabi", "Sindhi", "South Indian Hindu")
        val ethinicityAdapter = ArrayAdapter(activity, android.R.layout.simple_spinner_item, listOfEthnicity)
        enthicityAutoCompleteTextView.threshold = 3
        enthicityAutoCompleteTextView.setAdapter(ethinicityAdapter)
        enthicityAutoCompleteTextView.onItemClickListener = OnItemClickListener { parent, view_, position, _ ->

        }


//        val textInputLayout = findViewById<TextInputLayout>(R.id.textInputLayout)
//        val autoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.autovisitedat)
//
//        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, headings)
//        autoCompleteTextView.setAdapter(adapter)
//        autoCompleteTextView.OnItemClickListener { parent, view, position, id ->
//            val selectedData = data[position]
//        }
    }


    private fun moveToFragmentDetials(type: String) {
        val bundle = Bundle()
        bundle.putString(Keys.APP_TYPE, type)
        navigateTo(R.id.action_approvalFragment_to_approvalDetailFragment, bundle)
    }

    private fun moveToCampaignDetials(type: String) {
        val bundle = Bundle()
        bundle.putString(Keys.APP_TYPE, type)
//        navigateTo(R.id.action_approvalFragment_to_campaignApprovalDetailFragment, bundle)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = if (context is MainActivity) context else getActivity() as MainActivity
    }
}