package com.stetig.solitaire.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.MaterialSharedAxis
import com.stetig.callsync.base.BaseFragment
import com.stetig.solitaire.R
import com.stetig.solitaire.activity.MainActivity
import com.stetig.solitaire.adapter.OpportunityRecyclerAdapter
import com.stetig.solitaire.api.CommonClassForQuery
import com.stetig.solitaire.api.Keys
import com.stetig.solitaire.api.Query.Companion.BOOKED_OPPORTUNITY_LIST
import com.stetig.solitaire.api.Query.Companion.EXPIRING_OPPORTUNITY_IN_7_DAYS
import com.stetig.solitaire.api.Query.Companion.OPEN_OPPORTUNITY_LIST
import com.stetig.solitaire.api.Query.Companion.OPPORTUNITY_LIST
import com.stetig.solitaire.api.Query.Companion.TODAY_OPPORTUNITY
import com.stetig.solitaire.data.Opportunity
import com.stetig.solitaire.databinding.FragmentOpportunityBinding
import com.stetig.solitaire.utils.Utils

class OpportunityFragment : BaseFragment() {
    private lateinit var binding: FragmentOpportunityBinding
    private lateinit var activity: MainActivity
    private lateinit var commonClassForQuery: CommonClassForQuery
    private lateinit var adapter: OpportunityRecyclerAdapter
    private var projectList = mutableListOf<Opportunity.Record>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_opportunity, container, false)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        initView(rootView = binding.root)
        return binding.root
    }

    override fun initView(rootView: View?) {
        initRecycler()
    }

    private fun initRecycler() {
        adapter = OpportunityRecyclerAdapter(activity, projectList)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onResume() {
        super.onResume()
        commonClassForQuery = CommonClassForQuery.getInstance(activity, activity.getRestClient())!!
        if (arguments != null) {
            val type = arguments?.getString(Keys.OPP_TYPE, "not found")
            binding.title.text = type

            val query = when {
                type.equals(getString(R.string.expiring_opportunities_for_next_7_days)) -> {
                    EXPIRING_OPPORTUNITY_IN_7_DAYS
                }
                type.equals(getString(R.string.action_opportunity_for_today)) -> {
                    TODAY_OPPORTUNITY
                }
                type.equals(getString(R.string.qualification)) -> {
                    OPEN_OPPORTUNITY_LIST
                }
                type.equals(getString(R.string.bookingconfirmed)) -> {
                    BOOKED_OPPORTUNITY_LIST
                }
                else -> {
                    OPPORTUNITY_LIST + Utils.buildQueryParameter(type)
                }
            }
            Log.e("", "onResume: $type")
            commonClassForQuery.getOpportunity(query, onOpportunityListListener)

        }
    }

    private val onOpportunityListListener = object : CommonClassForQuery.OnDataReceiveListener {
        override fun onDataReceive(data: Any) {
            if (data is Opportunity) {
                projectList.clear()
                projectList.addAll(data.records)
                adapter.notifyDataSetChanged()

                activity.checkListIsEmpty(data.records)
            }
        }

        override fun onError(obj: String) {

        }


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = if (context is MainActivity) context else getActivity() as MainActivity
    }
}