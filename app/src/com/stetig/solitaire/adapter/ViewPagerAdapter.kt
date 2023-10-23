package com.stetig.solitaire.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.stetig.solitaire.api.Query
import com.stetig.solitaire.fragment.PageFragment

/**
 * Created by Pratik Kataria on 01-12-2020.
 */
class ViewPagerAdapter(fragmentActivity: FragmentActivity, var today: Boolean) : FragmentStateAdapter(fragmentActivity) {
    var tab = arrayOf(Query.SITE_VISIT, Query.FOLLOW_UP, Query.FACE_TO_FACE)
    var listOfFragment = arrayOfNulls<Fragment>(3)
    override fun getItemCount() = tab.size

    override fun createFragment(position: Int): PageFragment {
        listOfFragment[position] = PageFragment.newInstance(isToday = today, tab[position])
        return listOfFragment[position] as PageFragment
    }

    public fun onItemSelected(orderBY: String, position: Int) {
        val frag = listOfFragment[position]
        Log.e(javaClass.name, "onItemSelected: $frag")
        if (frag is PageFragment) (frag as PageFragment).onItemSelected(orderBY)
    }


}