package com.stetig.solitaire.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.stetig.callsync.base.BaseFragment
import com.stetig.solitaire.R
import com.stetig.solitaire.activity.MainActivity
import com.stetig.solitaire.adapter.TimeLineRecyclerAdapter
import com.stetig.solitaire.api.CommonClassForQuery
import com.stetig.solitaire.api.Keys
import com.stetig.solitaire.api.Query
import com.stetig.solitaire.data.Timeline
import com.stetig.solitaire.databinding.FragmentTimeLineBinding
import com.stetig.solitaire.utils.Utils
import com.google.android.material.transition.MaterialSharedAxis


class TimeLineFragment : BaseFragment() {
    lateinit var binding: FragmentTimeLineBinding
    lateinit var commonClassForQuery: CommonClassForQuery
    lateinit var activity: MainActivity
    private lateinit var opportunityId: String
    private var projectList = mutableListOf<Timeline.Record>()
    private lateinit var adapter: TimeLineRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        opportunityId = arguments?.getString(Keys.OPP_ID, "") ?: ""
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_time_line, container, false)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        initView(rootView = binding.root)
        return binding.root
    }

    override fun initView(rootView: View?) {
        initRecycler()
    }

    override fun onResume() {
        super.onResume()
        commonClassForQuery = CommonClassForQuery.getInstance(activity, activity.getRestClient())!!
        val query = "${Query.TIME_LINE_QUERY} ${Query.OPPORTUNITY_ID_FILTER} ${Utils.buildQueryParameter(opportunityId)} ${Query.ORDER_BY_CREATED_DATE}"
        commonClassForQuery.getTimeLine(query, onDataCommonClassForQuery)
    }

    private val onDataCommonClassForQuery = object : CommonClassForQuery.OnDataReceiveListener {
        override fun onDataReceive(data: Any) {
            if (data is Timeline) {
                projectList.clear()
                projectList.addAll(data.records)
                adapter.notifyDataSetChanged()
            }
        }

        override fun onError(obj: String) {}

    }

    private fun initRecycler() {
        adapter = TimeLineRecyclerAdapter(activity, projectList)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = if (context is MainActivity) context else getActivity() as MainActivity
    }
}