package com.stetig.solitaire.fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.*
import com.stetig.callsync.base.BaseFragment
import com.stetig.solitaire.R
import com.stetig.solitaire.activity.MainActivity
import com.stetig.solitaire.api.Keys

class FeedBackFragment : BaseFragment() {

    lateinit var activity: MainActivity
    lateinit var binding: FeedBackFragment

    override fun initView(rootView: View?) {
    }

    private fun moveToFragmentDetials(type: String){
        val bundle = Bundle()
        bundle.putString(Keys.APP_TYPE, type)
        navigateTo(R.id.action_approvalFragment_to_approvalDetailFragment, bundle)
    }

    private fun moveToCampaignDetials(type: String){
        val bundle = Bundle()
        bundle.putString(Keys.APP_TYPE, type)
//        navigateTo(R.id.action_approvalFragment_to_campaignApprovalDetailFragment, bundle)
    }
}