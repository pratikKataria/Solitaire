package com.stetig.solitaire.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.stetig.solitaire.data.Approval
import com.stetig.solitaire.data.CampaignApproval
import com.stetig.solitaire.databinding.FragmentApprovalBinding
import com.stetig.solitaire.utils.Utils

class ApprovalFragment : BaseFragment() {

    lateinit var activity: MainActivity
    lateinit var binding: FragmentApprovalBinding
    private lateinit var commonClassForQuery: CommonClassForQuery
    private var projectList = mutableListOf<CampaignApproval.Record>()
    private var projectListRecords = mutableListOf<Approval.Record>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_approval, container, false)
        initView(binding.root)
        return binding.root
    }

    override fun initView(rootView: View?) {
        binding.icApprovalMenuOne.setOnClickListener {
            moveToFragmentDetials(getString(R.string.approvalpending))
        }
        binding.icApprovalMenuTwo.setOnClickListener {
            moveToCampaignDetials("Submitted for Approval")
        }
        binding.icApprovalMenuThree.setOnClickListener{
            moveToSourceChangeDetials("Pending For Approval")
        }
        binding.icApprovalMenuFour.setOnClickListener{
            moveToCpCreationDetials("CP CREATION APPROVAL")
        }
    }

    private fun moveToFragmentDetials(type: String) {
        val bundle = Bundle()
        bundle.putString(Keys.APP_TYPE, type)
        navigateTo(R.id.action_approvalFragment_to_approvalDetailFragment, bundle)
    }

    private fun moveToCampaignDetials(type: String) {
        val bundle = Bundle()
        bundle.putString(Keys.APP_TYPE, type)
        navigateTo(R.id.action_approvalFragment_to_campaignApprovalDetailFragment, bundle)
    }

    private  fun moveToSourceChangeDetials(type: String){
        val bundle = Bundle()
        bundle.putString(Keys.APP_TYPE, type)
        navigateTo(R.id.action_approvalFragment_to_sourceChangeApprovalDetailFragment, bundle)
    }

    private fun moveToCpCreationDetials(type: String){
        val bundle = Bundle()
        bundle.putString(Keys.APP_TYPE, type)
        navigateTo(R.id.action_approvalFragment_to_cpcreationApprovalDetailFragment, bundle)
    }


    @SuppressLint("UseRequireInsteadOfGet")
    override fun onResume() {
        super.onResume()
        commonClassForQuery = CommonClassForQuery.getInstance(activity, activity.getRestClient())!!

        commonClassForQuery.getCampaign(Query.CAMPAIGN_APPROVAL_LIST, campaignapprovalListListener)
        commonClassForQuery.getApprovalWithoutLoader(Query.APPROVAL_LIST +Utils.buildQueryParameter("Approval Pending"), approvalListListener)

    }

    private val campaignapprovalListListener = object : CommonClassForQuery.OnDataReceiveListener {
        override fun onDataReceive(data: Any) {
            if (data is CampaignApproval) {
                projectList.clear()
                projectList.addAll(data.records)
                binding.campaignApprovalCount.text = projectList.size.toString()
            }
        }

        override fun onError(obj: String) {}
    }

    private val approvalListListener = object : CommonClassForQuery.OnDataReceiveListener {
        override fun onDataReceive(data: Any) {
            if (data is Approval) {
                projectListRecords.clear()
                projectListRecords.addAll(data.records)
                binding.newBookingApprovalCount.text = projectListRecords.size.toString()
            }
        }

        override fun onError(obj: String) {}
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = if (context is MainActivity) context else getActivity() as MainActivity
    }

}