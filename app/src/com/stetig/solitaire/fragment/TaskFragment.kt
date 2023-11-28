package com.stetig.solitaire.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.stetig.callsync.base.BaseFragment
import com.stetig.solitaire.R
import com.stetig.solitaire.activity.MainActivity
import com.stetig.solitaire.adapter.ViewPagerAdapter
import com.stetig.solitaire.api.Keys
import com.stetig.solitaire.api.Query
import com.stetig.solitaire.databinding.FragmentTaskBinding
import com.stetig.solitaire.databinding.TabLayoutBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.transition.MaterialSharedAxis

class TaskFragment : BaseFragment() {

    private lateinit var activity: MainActivity
    private lateinit var binding: FragmentTaskBinding
    var tabs = arrayOf("Proposed Site Visits", "Follow up", "Allotted Site Visits")
    val categories = arrayOf("Sort by \u2193\u2191", "Name", "Activity", "Created")
    var colorRes = intArrayOf(R.color.sv, R.color.fu, R.color.f2f)
    var pos = 0
    var isToday = false
    private var orderBy = "StageName"
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_task, container, false)
        enterTransition = if (arguments?.getBoolean(Keys.FROM_MENU, false) == true) MaterialSharedAxis(MaterialSharedAxis.X, false) else MaterialSharedAxis(MaterialSharedAxis.Z, true)
        initView(binding.root)
        return binding.root
    }

    override fun initView(rootView: View?) {
        isToday = arguments?.getBoolean(Keys.IS_TODAY, false)!!
        binding.headerText.text = if (isToday) "Task for Today" else "All Task"
        pos = arguments?.getInt(Keys.TASK_TYPE, 0) ?: 0
        initTabBar()
        val arrayAdapter = ArrayAdapter(activity, R.layout._layout_spinner_item, categories)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.totalActiveOpportunitiesSpinnerSortType.adapter = arrayAdapter
        binding.totalActiveOpportunitiesSpinnerSortType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                Log.e(javaClass.name, "onItemSelected: $position")
                if (categories[position] != "Sort by \u2193\u2191") {
                    when (categories[position]) {
                        "Name" -> viewPagerAdapter.onItemSelected(Query.ORDER_BY_TASK_NAME, binding.viewPager2.currentItem)
                        "Activity" -> viewPagerAdapter.onItemSelected(Query.ORDER_BY_ACTIVITY_DATE, binding.viewPager2.currentItem)
                        "Created" -> viewPagerAdapter.onItemSelected(Query.ORDER_BY_CREATED_DATE, binding.viewPager2.currentItem)
                    }
                } else {
                    viewPagerAdapter.onItemSelected(Query.ORDER_BY_CREATED_DATE, binding.viewPager2.currentItem)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }


    private fun initTabBar() {
        binding.tabLayout.setSelectedTabIndicatorColor(Color.WHITE)
        viewPagerAdapter = ViewPagerAdapter(activity, isToday)
        binding.viewPager2.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab: TabLayout.Tab, position: Int ->
            tab.text = tabs[position]
            val view = LayoutInflater.from(activity).inflate(R.layout.tab_layout, null)
            val textView = view.findViewById<TextView>(R.id.tabText)
            textView.setTextColor(resources.getColor(R.color.white))
            textView.text = tabs[position]
            tab.customView = view
            view.setBackgroundColor(resources.getColor(colorRes[position]))
            if (position == 0) {
                view.setBackgroundColor(resources.getColor(colorRes[position]))
                textView.setTextColor(resources.getColor(colorRes[position]))
                view.setBackgroundColor(resources.getColor(R.color.white))
                val view1 = view.findViewById<View>(R.id.indicator)
                view1.visibility = VISIBLE
            }
        }.attach()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val view = tab.customView
                view!!.alpha = 1f
                val view1 = view.findViewById<View>(R.id.indicator)
                view1.visibility = VISIBLE
                view1.setBackgroundColor(resources.getColor(colorRes[tab.position]))
                val textView = view.findViewById<TextView>(R.id.tabText)
                textView.setTextColor(resources.getColor(colorRes[tab.position]))
                view.setBackgroundColor(resources.getColor(R.color.white))
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                val view = tab.customView
                view!!.alpha = 0.5f
                val view1 = view.findViewById<View>(R.id.indicator)
                view1.visibility = GONE
                view1.setBackgroundColor(resources.getColor(colorRes[tab.position]))
                val textView = view.findViewById<TextView>(R.id.tabText)
                textView.setTextColor(resources.getColor(R.color.white))
                view.setBackgroundColor(resources.getColor(colorRes[tab.position]))
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        binding.viewPager2.currentItem = pos
        binding.tabLayout.isSelected = true
    }

    private fun tabSelectionStyle(tab: TabLayout.Tab, color: Int, alpha: Float) {
        try {
            val view = tab.customView!!
            view.alpha = alpha
            view.findViewById<View>(R.id.indicator).visibility = GONE
            view.findViewById<View>(R.id.indicator).setBackgroundColor(resources.getColor(color))
            view.findViewById<TextView>(R.id.tabText).setTextColor(resources.getColor(R.color.white))
            view.setBackgroundColor(resources.getColor(color))
        } catch (e: Exception) {
            Log.e("TAG", "tabSelectionStyle: ${e.message} ${e.stackTrace}")
        }
    }

    private fun setTabStyle(tabBinding: TabLayoutBinding, text: String, color: Int) {
        tabBinding.tabText.setTextColor(resources.getColor(color))
        tabBinding.root.setBackgroundColor(resources.getColor(R.color.white))
        tabBinding.indicator.visibility = VISIBLE
    }

    override fun onResume() {
        super.onResume()
    }


    override fun onAttach(context: Context) {
        if (context is MainActivity) activity = context
        super.onAttach(context)
    }
}