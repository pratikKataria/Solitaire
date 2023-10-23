package com.stetig.solitaire.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.stetig.callsync.base.BaseFragment
import com.stetig.solitaire.R
import com.stetig.solitaire.activity.MainActivity
import com.stetig.solitaire.adapter.SearchRecyclerAdapter
import com.stetig.solitaire.api.CommonClassForQuery
import com.stetig.solitaire.api.Query
import com.stetig.solitaire.data.Search
import com.stetig.solitaire.databinding.FragmentSearchBinding
import com.google.android.material.transition.MaterialSharedAxis


class SearchFragment : BaseFragment() {

    lateinit var binding: FragmentSearchBinding
    lateinit var activity: MainActivity
    lateinit var commonClassForQuery: CommonClassForQuery
    val OPEN_BRACKET = "("
    val CLOSE_BRACKET = ")"
    private var projectList = mutableListOf<Search.Record>()
    private lateinit var adapter: SearchRecyclerAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        initView(rootView = binding.root)
        return binding.root
    }

    override fun initView(rootView: View?) {
        initRecycler()
        binding.editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(char: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val query = Query.SEARCH + OPEN_BRACKET + "Project__r.Name " + buildLikeQuery(char.toString()) + "OR" + " Account_Mobile_Number__c " + buildLikeQuery(char.toString()) + "OR" + " NAME " + buildLikeQuery(char.toString()) + CLOSE_BRACKET
                commonClassForQuery.getSearchDetail(query, onDataCommonClassForQuery)
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    private fun initRecycler() {
        adapter = SearchRecyclerAdapter(activity, projectList)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter
    }

    val onDataCommonClassForQuery = object : CommonClassForQuery.OnDataReceiveListener {
        override fun onDataReceive(data: Any) {
            if (data is Search) {
                projectList.clear()
                projectList.addAll(data.records)
                adapter.notifyDataSetChanged()
            }
        }

        override fun onError(obj: String) {}

    }

    override fun onResume() {
        super.onResume()
        commonClassForQuery = CommonClassForQuery.getInstance(activity, activity.getRestClient())!!
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = if (context is MainActivity) context else getActivity() as MainActivity
    }

    fun buildLikeQuery(string: String) = "LIKE '%$string%'"
}