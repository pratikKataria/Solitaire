package com.stetig.solitaire.fragment

import android.annotation.SuppressLint
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
import com.stetig.solitaire.data.Approval
import com.stetig.solitaire.data.CampaignApproval
import com.stetig.solitaire.data.CpCreationApproval
import com.stetig.solitaire.data.SourceChangeApproval
import com.stetig.solitaire.databinding.FragmentMenuBinding

class MenuFragment : BaseFragment() {
    private lateinit var binding: FragmentMenuBinding
    private lateinit var commonClassForQuery: CommonClassForQuery
    private var projectList = mutableListOf<CampaignApproval.Record>()
    private var projectListRecords = mutableListOf<Approval.Record>()
    lateinit var activity: MainActivity


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_menu, container, false)
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
        binding.icApprovalMenuThree.setOnClickListener {
            moveToSourceChangeDetials("Pending For Approval")
        }
        binding.icApprovalMenuFour.setOnClickListener {
            moveToCpCreationDetials("CP CREATION APPROVAL")
        }

        binding.icApprovalMenuFour.setOnClickListener {
            moveToCpCreationDetials("CP CREATION APPROVAL")
        }

        binding.menuFive.setOnClickListener {
            moveToCCRFragment("CAMPAIGN CR Approval Request")
        }
    }

    private fun moveToFragmentDetials(type: String) {
        val bundle = Bundle()
        bundle.putString(Keys.APP_TYPE, type)
        navigateTo(R.id.action_menuFragment_to_approvalDetailFragment, bundle)
    }

    private fun moveToCampaignDetials(type: String) {
        val bundle = Bundle()
        bundle.putString(Keys.APP_TYPE, type)
//        navigateTo(R.id.action_approvalFragment_to_campaignApprovalDetailFragment, bundle)
        navigateTo(R.id.action_menuFragment_to_campaignApprovalDetailFragment, bundle)
    }

    private fun moveToSourceChangeDetials(type: String) {
        val bundle = Bundle()
        bundle.putString(Keys.APP_TYPE, type)
//        navigateTo(R.id.action_approvalFragment_to_sourceChangeApprovalDetailFragment, bundle)
        navigateTo(R.id.action_menuFragment_to_sourceChangeApprovalDetailFragment, bundle)
    }

    private fun moveToCpCreationDetials(type: String) {
        val bundle = Bundle()
        bundle.putString(Keys.APP_TYPE, type)
//        navigateTo(R.id.action_approvalFragment_to_cpcreationApprovalDetailFragment, bundle)
        navigateTo(R.id.action_menuFragment_to_cpcreationApprovalFragment, bundle)
    }

    private fun moveToCCRFragment(type: String) {
        val bundle = Bundle()
        bundle.putString(Keys.APP_TYPE, type)
//        navigateTo(R.id.action_approvalFragment_to_CCRApprovalRequestFragment, bundle)
        navigateTo(R.id.action_menuFragment_to_CCRApprovalRequestFragment, bundle)
    }


    @SuppressLint("UseRequireInsteadOfGet")
    override fun onResume() {
        super.onResume()
        commonClassForQuery = CommonClassForQuery.getInstance(activity, activity.getRestClient())!!

        commonClassForQuery.getCampaign(Query.CAMPAIGN_APPROVAL_LIST, campaignapprovalListListener)
        commonClassForQuery.getApprovalWithoutLoader(Query.APPROVAL_LIST, approvalListListenerTwo)
        commonClassForQuery.getSourceChanegApprovalWithoutLoader(Query.SOURCE_CHANGE_APPROVAL_LIST, sourcechangeapprovalListListener)
        commonClassForQuery.getCpCreationApprovalWithoutLoader(Query.CP_CREATION_APPROVAL_LIST, cpcreationapprovalListListener)
        commonClassForQuery.getCpCreationApprovalWithoutLoader(Query.CP_CREATION_APPROVAL_LIST, cpcreationapprovalListListener)
        commonClassForQuery.getCpCreationApprovalWithoutLoader(Query.CCR_APPROVAL_LIST, cpcreationapprovalListListener2)

    }

    override fun onClick(view: View) {
        super.onClick(view)
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

    private val approvalListListenerTwo = object : CommonClassForQuery.OnDataReceiveListener {
        override fun onDataReceive(data: Any) {
            if (data is Approval) {

                projectListRecords.clear()

                var count = 0;
                for (x in data.records)
                    if (x?.costSheets1R != null || x?.paymentPlansR != null) count++

                binding.newBookingApprovalCount.text = count.toString()

            }
        }

        override fun onError(obj: String) {}
    }

    private val sourcechangeapprovalListListener = object : CommonClassForQuery.OnDataReceiveListener {
        override fun onDataReceive(data: Any) {
            if (data is SourceChangeApproval) {
                binding.sourceChangeApprovalCount.text = data.records.size.toString()

            }
        }

        override fun onError(obj: String) {}


    }

    private val cpcreationapprovalListListener = object : CommonClassForQuery.OnDataReceiveListener {
        override fun onDataReceive(data: Any) {
            if (data is CpCreationApproval) {
                binding.channelPartnerCreationApprovalCount.text = data.records.size.toString()
            }
        }

        override fun onError(obj: String) {}


    }

    private val cpcreationapprovalListListener2 = object : CommonClassForQuery.OnDataReceiveListener {
        override fun onDataReceive(data: Any) {
            if (data is CpCreationApproval) {
                binding.ccrApproval.text = data.records.size.toString()
            }
        }

        override fun onError(obj: String) {}


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = if (context is MainActivity) context else getActivity() as MainActivity
    }

}