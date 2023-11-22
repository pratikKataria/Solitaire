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
import com.stetig.solitaire.adapter.sitevisitRecyclerAdapter
import com.stetig.solitaire.api.CommonClassForQuery
import com.stetig.solitaire.api.Keys
import com.stetig.solitaire.api.Query.Companion.EXPIRING_OPPORTUNITY_IN_7_DAYS
import com.stetig.solitaire.api.Query.Companion.ORDER_BY
import com.stetig.solitaire.api.Query.Companion.ORDER_BY_CREATED_DATE
import com.stetig.solitaire.api.Query.Companion.PROPOSED_SITE_VISIT_LIST
import com.stetig.solitaire.api.Query.Companion.SITE_VISIT_LIST
import com.stetig.solitaire.api.Query.Companion.TODAY_OPPORTUNITY
import com.stetig.solitaire.data.Sitevisit
import com.stetig.solitaire.databinding.FragmentSitevisitBinding
import com.stetig.solitaire.utils.Utils
import org.acra.ACRA.log

class SitevisitFragment : BaseFragment() {
    private lateinit var binding: FragmentSitevisitBinding
    private lateinit var activity: MainActivity
    private lateinit var commonClassForQuery: CommonClassForQuery
    private lateinit var adapter: sitevisitRecyclerAdapter
    private var projectList = mutableListOf<Sitevisit.Record>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_sitevisit, container, false)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        initView(rootView = binding.root)
        return binding.root
    }

    override fun initView(rootView: View?) {
        initRecycler()
    }

    private fun initRecycler() {
        adapter = sitevisitRecyclerAdapter(activity, projectList)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onResume() {
        super.onResume()
        commonClassForQuery = CommonClassForQuery.getInstance(activity, activity.getRestClient())!!
        if (arguments != null) {
            val type = arguments?.getString(Keys.SITE_TYPE, "not found")

            binding.sitevisittitle.text =  if ( type == "sales Manager Allocated") "Schedule Site Visit" else type

            val query = when {
                type.equals(getString(R.string.expiring_opportunities_for_next_7_days)) -> {
                    EXPIRING_OPPORTUNITY_IN_7_DAYS
                }

                type.equals(getString(R.string.action_opportunity_for_today)) -> {
                    TODAY_OPPORTUNITY
                }

                else -> {
                    if (type == "sales Manager Allocated") {
                        "$PROPOSED_SITE_VISIT_LIST $ORDER_BY_CREATED_DATE"
                    } else {
                        SITE_VISIT_LIST + Utils.buildQueryParameter(type)
                    }
                }
            }
            Log.e("", "onResume: $SITE_VISIT_LIST")
            commonClassForQuery.getSitevisit(query, onSitevisitListListener)

        }
    }

    private val onSitevisitListListener = object : CommonClassForQuery.OnDataReceiveListener {
        override fun onDataReceive(data: Any) {
            if (data is Sitevisit) {
                log.e("Recorder data ", data.records.toString())
                projectList.clear()
                projectList.addAll(data.records)
                adapter.notifyDataSetChanged()

                activity.checkListIsEmpty(data.records)
            }
        }

        override fun onError(obj: String) {   }


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = if (context is MainActivity) context else getActivity() as MainActivity
    }
}