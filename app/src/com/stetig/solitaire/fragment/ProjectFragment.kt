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
import com.stetig.solitaire.adapter.ProjectRecyclerAdapter
import com.stetig.solitaire.api.CommonClassForQuery
import com.stetig.solitaire.api.Query
import com.stetig.solitaire.data.Projects
import com.stetig.solitaire.databinding.FragmentProjectBinding
import com.stetig.solitaire.utils.Utils
import com.google.android.material.transition.MaterialSharedAxis

class ProjectFragment : BaseFragment() {

    private lateinit var activity: MainActivity
    private lateinit var binding: FragmentProjectBinding
    private lateinit var adapter: ProjectRecyclerAdapter
    private lateinit var commonClassForQuery: CommonClassForQuery
    private var projectList = mutableListOf<Projects.Record>()
    private var cities = mutableListOf("All")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_project, container, false)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
        initView(binding.root)
        return binding.root
    }

    override fun initView(rootView: View?) {
        initRecycler()
    }

    private fun initRecycler() {
        adapter = ProjectRecyclerAdapter(activity, projectList)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter
    }

    private val arrayAdapter: ArrayAdapter<String> get() = ArrayAdapter(activity, android.R.layout.simple_spinner_dropdown_item, cities)
//    private fun initSpinner() {
//        val adapterSpinner = arrayAdapter
//        binding.citiesSpinner.adapter = adapterSpinner
//        binding.citiesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                val query = if (position == 0) Query.PROJECTS_COLLATERAL else Query.PROJECTS_COLLATERAL_CITY_FILTER + Utils.buildQueryParameter(cities[position])
//                projectList.clear()
//                commonClassForQuery.getProjects(query, onDataReceiveListener)
//            }
//
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//            }
//
//        }
//    }

    override fun onResume() {
        super.onResume()
        commonClassForQuery = CommonClassForQuery.getInstance(activity, activity.getRestClient())!!
        commonClassForQuery.getProjects(Query.PROJECTS_COLLATERAL, onDataReceiveListener)
//        initSpinner()
    }

    private var onDataReceiveListener = object : CommonClassForQuery.OnDataReceiveListener {
        override fun onDataReceive(data: Any) {
            if (data is Projects && data.records.isNotEmpty()) {
                projectList.clear()
                projectList.addAll(data.records)
                adapter.notifyDataSetChanged()
                data.records.forEach { (_, cityC) ->
                    if (!cities.contains(cityC))
                        cities.add(cityC)
                }
                adapter.notifyDataSetChanged()
            } else {
                Utils.setToast(activity, "No Projects Found")
            }
        }

        override fun onError(obj: String) {
        }

    }

    override fun onAttach(context: Context) {
        if (context is MainActivity) activity = context
        super.onAttach(context)
    }
}