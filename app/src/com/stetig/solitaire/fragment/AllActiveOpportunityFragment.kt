package com.stetig.solitaire.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.stetig.callsync.base.BaseFragment
import com.stetig.solitaire.R
import com.stetig.solitaire.activity.MainActivity
import com.stetig.solitaire.adapter.AllActiveOpportunityRecyclerAdapter
import com.stetig.solitaire.api.CommonClassForQuery
import com.stetig.solitaire.api.Query.Companion.ALL_ACTIVE_OPPORTUNITY_ORDER_BY
import com.stetig.solitaire.api.Query.Companion.ASC
import com.stetig.solitaire.data.Opportunity
import com.stetig.solitaire.databinding.FragmentAllActiveOpportunitiesBinding
import com.stetig.solitaire.utils.Utils
import com.google.android.material.transition.MaterialSharedAxis

class AllActiveOpportunityFragment : BaseFragment() {
    private lateinit var binding: FragmentAllActiveOpportunitiesBinding
    private lateinit var activity: MainActivity
    private lateinit var commonClassForQuery: CommonClassForQuery
    private lateinit var adapter: AllActiveOpportunityRecyclerAdapter
    private var projectList = mutableListOf<Opportunity.Record>()
    private val categories = arrayOf("Sort by \u2193\u2191", "Stage", "Date")
    private var orderBy = "StageName"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_all_active_opportunities, container, false)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        initView(rootView = binding.root)
        return binding.root
    }

    override fun initView(rootView: View?) {
        initRecycler()

        val arrayAdapter = ArrayAdapter(activity, android.R.layout.simple_spinner_item, categories)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.totalActiveOpportunitiesSpinnerSortType.adapter = arrayAdapter

    }

    private fun initRecycler() {
        adapter = AllActiveOpportunityRecyclerAdapter(activity, projectList)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        commonClassForQuery = CommonClassForQuery.getInstance(activity, activity.getRestClient())!!
//        commonClassForQuery.getOpportunity(ALL_ACTIVE_OPPORTUNITY_ORDER_BY, onOpportunityListListener)
    }

    private val onOpportunityListListener = object : CommonClassForQuery.OnDataReceiveListener {
        override fun onDataReceive(data: Any) {
            Utils.hideProgressDialog(activity)
            if (data is Opportunity) {
                binding.totalActiveOpportunitiesCountText.text = "${data.records.size}"
                projectList.clear()
                projectList.addAll(data.records)
                adapter.notifyDataSetChanged()

                binding.totalActiveOpportunitiesSpinnerSortType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                        if (categories[position] != "Sort by \u2193\u2191") {
                            orderBy = categories[position]
                            when (orderBy) {
                                "Stage" -> orderBy = "StageName"
                                "Status" -> orderBy = "Status__c"
                                "Date" -> orderBy = "CloseDate"
                            }
                            onResume()
                        } else {
                            orderBy = "StageName"
                            onResume()
                        }
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {}
                }
            }
        }

        override fun onError(obj: String) {
            Utils.hideProgressDialog(activity)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = if (context is MainActivity) context else getActivity() as MainActivity
    }
}