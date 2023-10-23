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
import com.stetig.callsync.base.BaseFragment
import com.stetig.solitaire.R
import com.stetig.solitaire.activity.MainActivity
import com.stetig.solitaire.adapter.CalenderDetailRecyclerViewAdapter
import com.stetig.solitaire.data.Event
import com.stetig.solitaire.databinding.FragmentCalenderDetailBinding
import com.google.android.material.transition.MaterialSharedAxis
import java.util.*


class CalendarDetailFragment : BaseFragment() {

    lateinit var binding: FragmentCalenderDetailBinding
    lateinit var activity: MainActivity
    var events: ArrayList<Event> = ArrayList<Event>()
    private var date: String? = null
    private var day: String? = null
    private var months: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            events = arguments?.getParcelableArrayList<Event>("key") as ArrayList<Event>
        } catch (e: Exception) {
            Log.e(javaClass.name, "onCreate: "+e.message)
        }

        date = arguments?.getString("date", "") ?: ""
        day = arguments?.getString("week", "")
        months = arguments?.getString("month", "")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calender_detail, container, false)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        initView(rootView = binding.root)
        initRecyclerView()
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun initView(rootView: View?) {
        binding.textViewMonth.text = months
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = if (context is MainActivity) context else getActivity() as MainActivity
    }

    fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        val eventDetailsRecyclerViewAdapter = CalenderDetailRecyclerViewAdapter(activity, events, date, day)
        binding.recyclerView.adapter = eventDetailsRecyclerViewAdapter
    }
}