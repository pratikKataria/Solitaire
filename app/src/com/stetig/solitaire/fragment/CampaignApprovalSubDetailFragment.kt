package com.stetig.solitaire.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.salesforce.androidsdk.app.SalesforceSDKManager
import com.stetig.callsync.base.BaseFragment
import com.stetig.solitaire.R
import com.stetig.solitaire.activity.MainActivity
import com.stetig.solitaire.api.CommonClassForApi
import com.stetig.solitaire.api.CommonClassForQuery
import com.stetig.solitaire.api.Keys
import com.stetig.solitaire.api.Query
import com.stetig.solitaire.data.*
import com.stetig.solitaire.utils.Utils
import com.stetig.solitaire.databinding.FragmentCampaignApprovalSubdetailBinding
import io.reactivex.observers.DisposableObserver


class CampaignApprovalSubDetailFragment : BaseFragment() {
    lateinit var activity: MainActivity
    lateinit var binding: FragmentCampaignApprovalSubdetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_campaign_approval_subdetail,
            container,
            false
        )
        initView(binding.root)
        return binding.root
    }

    override fun initView(rootView: View?) {


    }

    override fun onResume() {
        super.onResume()

        try {
            val commonClassForQuery =
                CommonClassForQuery.getInstance(activity, activity.getRestClient())!!
            if (arguments != null) {
                val id = arguments?.getString(Keys.CAM_ID, "")
                commonClassForQuery.getCampaignTableDetailsCampaignApproval(Query.CAMPAIGN_TABLE_FIELDS + Utils.buildQueryParameter(id), onDataReceiveListener)
            }
        } catch (e: Exception) {
        }
    }

    private var onDataReceiveListener = object : CommonClassForQuery.OnDataReceiveListener {
        override fun onDataReceive(data: Any) {
            if (data is  CampaignApproval && data.records.isNotEmpty()) {

                val record = data.records[0]

                binding.parentCampaignNameC.text = Utils.checkValueOrGiveEmpty(record.name)
                binding.childCampaignNameC.text = Utils.checkValueOrGiveEmpty(record.primaryProjectR?.name)
                binding.projectNameC.text = Utils.checkValueOrGiveEmpty(record.primaryProjectR?.name)
                binding.startDate.text = record.startDate
                binding.endDate.text = record.endDate
                binding.budgetCostInHeirachy.text = record.budgetedCost.toString()
                binding.budgetCostInChildCampaign.text = record.budgetedCost.toString()


                if (record.approvalStatus == "Level 1 Approved" && record.approvalStatus == "Level 2 Approved") {
                    binding.approveBtn.visibility = View.GONE
                    binding.rejectBtn.visibility = View.GONE
                }



                binding.approveBtn.setOnClickListener {
                    val userAccount =
                        SalesforceSDKManager.getInstance().userAccountManager.currentUser
                    val commonClassForApi: CommonClassForApi = CommonClassForApi.getInstance(activity)!!
                    val auth = "Bearer " + userAccount.authToken
                    val data = SendCampaignApprovalRequest(
                        campaignId = data.records[0]?.id.toString(),
                        status = "Approve"
                    )
                    commonClassForApi.CampaignApprovalRequest(disposableObserver, data, auth)

                }


                binding.rejectBtn.setOnClickListener {
                    val userAccount =
                        SalesforceSDKManager.getInstance().userAccountManager.currentUser
                    val commonClassForApi: CommonClassForApi =
                        CommonClassForApi.getInstance(activity)!!
                    val auth = "Bearer " + userAccount.authToken
                    val data = SendCampaignApprovalRequest(
                        campaignId = data.records[0]?.id.toString(),
                        status = "Reject"
                    )
                    commonClassForApi.CampaignApprovalRequest(disposableObserver, data, auth)

                }
            }
        }

        override fun onError(obj: String) {
            Log.e(javaClass.name, "onError: $obj")
        }

    }
    private var disposableObserver: DisposableObserver<SendCampaignApprovalRequestResponse> =
        object : DisposableObserver<SendCampaignApprovalRequestResponse>() {
            override fun onNext(callStatusResponse: SendCampaignApprovalRequestResponse) {
                Utils.setToast(activity, callStatusResponse.message)
                activity.navHostFragment.findNavController().popBackStack()
            }

            override fun onError(e: Throwable) {
                Log.e(javaClass.name, "onError: 337 " + e.message)
            }

            override fun onComplete() {}
        }

    override fun onAttach(context: Context) {
        if (context is MainActivity) activity = context
        super.onAttach(context)
    }
}

