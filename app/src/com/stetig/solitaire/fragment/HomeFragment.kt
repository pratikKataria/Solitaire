package com.stetig.solitaire.fragment

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.stetig.callsync.base.BaseFragment
import com.stetig.solitaire.R
import com.stetig.solitaire.activity.MainActivity
import com.stetig.solitaire.api.CommonClassForQuery
import com.stetig.solitaire.api.Keys
import com.stetig.solitaire.api.Query
import com.stetig.solitaire.api.Query.Companion.OPPORTUNITY_LIST
import com.stetig.solitaire.api.Query.Companion.PROPOSAL
import com.stetig.solitaire.data.Opportunity
import com.stetig.solitaire.data.TaskCount
import com.stetig.solitaire.databinding.FragmentHomeBinding
import com.stetig.solitaire.utils.Utils
import org.acra.ACRA.log

class HomeFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {

    //chandan.kokul@stetig.in.devs
    //solitairedev@12
    lateinit var binding: FragmentHomeBinding
    lateinit var activity: MainActivity


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        initView(binding.root)
        return binding.root
    }

    override fun initView(rootView: View?) {

        binding.swipeToRefresh.setOnRefreshListener(this)

        binding.llTodaySv.setOnClickListener { moveToTask(0, true, "Proposed Site Visit") }
        binding.llTodayFu.setOnClickListener { moveToTask(1, true, "Follow Up") }
        binding.llTodayF2f.setOnClickListener { moveToTask(2, true, "Allotted Site Visit") }

        binding.llAllSv.setOnClickListener { moveToTask(0, false, "Proposed Site Visit") }
        binding.llAllFu.setOnClickListener { moveToTask(1, false, "Follow Up") }
        binding.llAllF2f.setOnClickListener { moveToTask(2, false, "Allotted Site Visit") }

//        binding.llQualification.setOnClickListener { moveToOpportunity(Query.QUALIFICATION) }
        binding.llNeedAnalysis.setOnClickListener { moveToOpportunity(Query.NEED_ANALYSIS) }
        binding.llProposal.setOnClickListener { moveToOpportunity(Query.PROPOSAL) }
        binding.llNegotiation.setOnClickListener { moveToOpportunity(Query.NEGOTIATION) }

        binding.llScheduledSitevisit.setOnClickListener { moveToSiteVisit(Query.SCHEDULED_SITE_VISIT) }
        binding.llOngoingSitevisit.setOnClickListener { moveToSiteVisit(Query.ONGOING_SITE_VISIT) }
        binding.llPastSitevisit.setOnClickListener { moveToSiteVisit(Query.PAST_SITE_VISIT) }



        binding.icMenuThree.setOnClickListener {
            val bundle = Bundle()
            bundle.putBoolean(Keys.EXP_OPP, true)
            bundle.putString(Keys.OPP_TYPE, getString(R.string.expiring_opportunities_for_next_7_days))
            navigateTo(R.id.action_homeFragment_to_opportunityFragment, bundle)
        }
    }

    private fun moveToTask(type: Int, isToday: Boolean, queryPara: String) {
        val bundle = Bundle()
        bundle.putBoolean(Keys.IS_TODAY, isToday)
        bundle.putInt(Keys.TASK_TYPE, type)
        bundle.putString(Keys.QUERY_PARA, queryPara)
        log.e("homepage_error", queryPara)
        navigateTo(R.id.action_homeFragment_to_taskFragment, bundle)
    }


    private fun moveToOpportunity(type: String) {
        val bundle = Bundle()
        bundle.putString(Keys.OPP_TYPE, type)
        navigateTo(R.id.action_homeFragment_to_opportunityFragment, bundle)
    }

    private fun moveToSiteVisit(type: String) {
        val bundle = Bundle()
        bundle.putString(Keys.SITE_TYPE, type)
        navigateTo(R.id.action_homeFragment_to_sitevisitFragment, bundle)
    }


    override fun onResume() {
        super.onResume()
        getDataFromSf()
    }


    private fun getDataFromSf() {
        if (activity.getRestClient() != null) {
            val commonClassForQuery = CommonClassForQuery.getInstance(activity, activity.getRestClient())!!
            commonClassForQuery.getCount(Query.TODAY_TASK_SITE_VISIT_COUNT, Query.SITE_VISIT, todayOnDataReceiveListener)
            commonClassForQuery.getCount(Query.TODAY_TASK_FOLLOW_UP_COUNT, Query.FOLLOW_UP, todayOnDataReceiveListener)
            commonClassForQuery.getCount(Query.TODAY_TASK_FACE_TO_FACE_COUNT, Query.FACE_TO_FACE, todayOnDataReceiveListener)
            commonClassForQuery.getCount(Query.ALL_TASK_SITE_VISIT_COUNT, Query.SITE_VISIT, allOnDataReceiveListener)
            commonClassForQuery.getCount(Query.ALL_TASK_FOLLOW_UP_COUNT, Query.FOLLOW_UP, allOnDataReceiveListener)
            commonClassForQuery.getCount(Query.ALL_TASK_FACE_TO_FACE_COUNT, Query.FACE_TO_FACE, allOnDataReceiveListener)

            commonClassForQuery.getOpportunityWithoutLoader(Query.BOOKED_OPPORTUNITY_LIST, "booked", onOpportunityListListener)
            commonClassForQuery.getOpportunityWithoutLoader(Query.OPEN_OPPORTUNITY_LIST, "open", onOpportunityListListener)
            commonClassForQuery.getOpportunityWithoutLoader(Query.OPPORTUNITY_LIST + Utils.buildQueryParameter(PROPOSAL), "lost", onOpportunityListListener)
            commonClassForQuery.getOpportunityWithoutLoader(Query.EXPIRING_OPPORTUNITY_IN_7_DAYS, "exp", onOpportunityListListener)

            commonClassForQuery.getCount(Query.PROPOSED_SITE_VISIT_LIST, Query.PROPOSED_SITE_VISIT_LIST, allOnDataReceiveListener)
            commonClassForQuery.getCount(Query.SITE_VISIT_LIST + Utils.buildQueryParameter(Query.ONGOING_SITE_VISIT), Query.ONGOING_SITE_VISIT, allOnDataReceiveListener)
            commonClassForQuery.getCount(Query.SITE_VISIT_LIST + Utils.buildQueryParameter(Query.PAST_SITE_VISIT), Query.PAST_SITE_VISIT, allOnDataReceiveListener)
        }
    }

    private var todayOnDataReceiveListener = object : CommonClassForQuery.OnDataReceiveListener {
        override fun onDataReceive(data: Any) {
            if (data is TaskCount) {
                when (data.type) {
                    Query.SITE_VISIT -> binding.todaySitevisitCount.text = data.records[0].expr0.toString()

                    Query.FOLLOW_UP -> binding.todayFollowupCount.text = data.records[0].expr0.toString()

                    Query.FACE_TO_FACE -> binding.todayFacetofaceCount.text = data.records[0].expr0.toString()
                }
            }
        }

        override fun onError(obj: String) {
        }

    }

    private var allOnDataReceiveListener = object : CommonClassForQuery.OnDataReceiveListener {
        override fun onDataReceive(data: Any) {
            if (data is TaskCount) {
                println(data.type)
                val count = data.records.size
                when (data.type) {
                    Query.SITE_VISIT -> binding.totalSitevisitCount.text = data.records[0].expr0.toString()

                    Query.PROPOSED_SITE_VISIT_LIST -> binding.tvScheduled.text = "Scheduled ($count)"

                    Query.ONGOING_SITE_VISIT -> binding.tvOngoing.text = "Ongoing ($count)"

                    Query.PAST_SITE_VISIT -> binding.tvPast.text = "Past ($count)"

                    Query.FOLLOW_UP -> binding.totalFollowupCount.text = data.records[0].expr0.toString()

                    Query.FACE_TO_FACE -> binding.totalFacetofaceCount.text = data.records[0].expr0.toString()
                }
            }
        }

        override fun onError(obj: String) {
            Log.e("Home Fragment", "onError Token Expire Check : $obj")
        }

    }

    private var opportunityOnDataReceiveListener = object : CommonClassForQuery.OnDataReceiveListener {
        override fun onDataReceive(data: Any) {
            if (data is TaskCount) {
                when (data.type) {
//                    Query.NEGOTIATION -> binding.tvNego.text = data.records[0].expr0.toString()
//                    Query.NEED_ANALYSIS -> binding.tvAlys.text = data.records[0].expr0.toString()
//                    Query.PROPOSAL -> binding.tvProp.text = data.records[0].expr0.toString()
//                    Query.BIP -> binding.tvBip.text = data.records[0].expr0.toString()
//                    Query.QUALIFICATION -> binding.oppCountText.text = data.records[0].expr0.toString()
                }
            }
        }

        override fun onError(obj: String) {
        }

    }

    override fun onAttach(context: Context) {
        if (context is MainActivity) activity = context
        super.onAttach(context)
    }


    var REFRESH_TIME_OUT = 3000 /*6sec*/
    override fun onRefresh() {
        getDataFromSf()
        Handler().postDelayed(
            { binding.swipeToRefresh.isRefreshing = false }, REFRESH_TIME_OUT.toLong()
        )
    }


    private val onOpportunityListListener = object : CommonClassForQuery.OnDataReceiveListener {
        override fun onDataReceive(data: Any) {
            if (data is Opportunity) {

                if ("booked" == data.type) {
                    binding.optyBooked.text = "Booked (${data.records.size})"
                }

                if ("open" == data.type) {
                    binding.optyOpen.text = "Open (${data.records.size})"
                }

                if ("lost" == data.type) {
                    binding.optyLost.text = "Lost (${data?.records?.size})"
                }

                if ("exp" == data.type) {
                    binding.expOpty.text = "Expiring opportunities for Next 7 days  (${data.records.size})"
                }
            }
        }

        override fun onError(obj: String) {

        }


    }
}