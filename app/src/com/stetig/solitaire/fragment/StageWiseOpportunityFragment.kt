package com.stetig.solitaire.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.stetig.callsync.base.BaseFragment
import com.stetig.solitaire.R
import com.stetig.solitaire.activity.MainActivity
import com.stetig.solitaire.api.CommonClassForQuery
import com.stetig.solitaire.api.Keys
import com.stetig.solitaire.api.Query
import com.stetig.solitaire.data.TaskCount
import com.stetig.solitaire.databinding.FragmentStageWiseOpportunityBinding
import com.google.android.material.transition.MaterialSharedAxis

class StageWiseOpportunityFragment : BaseFragment() {
    lateinit var binding: FragmentStageWiseOpportunityBinding
    lateinit var activity: MainActivity
    lateinit var commonClassForQuery: CommonClassForQuery

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_stage_wise_opportunity, container, false)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        initView(binding.root)
        return binding.root
    }

    override fun initView(rootView: View?) {
//        binding.llQualification.setOnClickListener { moveToOpportunity(Query.QUALIFICATION) }
        binding.llNeedAnalysis.setOnClickListener { moveToOpportunity(Query.NEED_ANALYSIS) }
        binding.llProposal.setOnClickListener { moveToOpportunity(Query.PROPOSAL) }
        binding.llNegotiation.setOnClickListener { moveToOpportunity(Query.NEGOTIATION) }
//        binding.llBip.setOnClickListener { moveToOpportunity(Query.BIP) }
    }


    private fun moveToOpportunity(type: String) {
        val bundle = Bundle()
        bundle.putString(Keys.OPP_TYPE, type)
        navigateTo(R.id.action_stageWiseOpportunityFragment_to_opportunityFragment, bundle)
    }

    override fun onClick(view: View) {
        super.onClick(view)

    }

    override fun onResume() {
        super.onResume()
        commonClassForQuery = CommonClassForQuery.getInstance(activity, activity.getRestClient())!!
//        commonClassForQuery.getCount(Query.NEGOTIATION_COUNT, Query.NEGOTIATION, opportunityOnDataReceiveListener)
//        commonClassForQuery.getCount(Query.NEED_ANALYSIS_COUNT, Query.NEED_ANALYSIS, opportunityOnDataReceiveListener)
//        commonClassForQuery.getCount(Query.PROPOSAL_COUNT, Query.PROPOSAL, opportunityOnDataReceiveListener)
//        commonClassForQuery.getCount(Query.BIP_COUNT, Query.BIP, opportunityOnDataReceiveListener)
//        commonClassForQuery.getCount(Query.QUALIFICATION_COUNT, Query.QUALIFICATION, opportunityOnDataReceiveListener)
    }

    private var opportunityOnDataReceiveListener = object : CommonClassForQuery.OnDataReceiveListener {
        override fun onDataReceive(data: Any) {
            if (data is TaskCount) {
                when (data.type) {
                    Query.NEGOTIATION -> {
                        binding.llNegotiationCount.text = data.records[0].expr0.toString()
                    }
                    Query.NEED_ANALYSIS -> {
                        binding.llNeedAnalysisCount.text = data.records[0].expr0.toString()
                    }
                    Query.PROPOSAL -> {
                        binding.llProposalCount.text = data.records[0].expr0.toString()
                    }
                    Query.BIP -> {
                        binding.llBipCount.text = data.records[0].expr0.toString()
                    }
                    Query.QUALIFICATION -> {
                        binding.llQualificationCount.text = data.records[0].expr0.toString()
                    }
                }
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