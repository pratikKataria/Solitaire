package com.stetig.solitaire.fragment

import ApprovalRecyclerAdapter
import CampaignApprovalRecyclerAdapter
import CpCreationRecyclerAdapter
import SourceChangeRecyclerAdapter
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.MaterialSharedAxis
import com.salesforce.androidsdk.app.SalesforceSDKManager
import com.stetig.callsync.base.BaseFragment
import com.stetig.solitaire.R
import com.stetig.solitaire.activity.MainActivity
import com.stetig.solitaire.api.CommonClassForApi
import com.stetig.solitaire.api.CommonClassForQuery
import com.stetig.solitaire.api.Keys
import com.stetig.solitaire.api.Query
import com.stetig.solitaire.data.CpCreationApproval
import com.stetig.solitaire.data.CpCreationApprovalResponse
import com.stetig.solitaire.data.SendCpCreaitonApprovalRequest
import com.stetig.solitaire.data.SourceChangeApproval
import com.stetig.solitaire.databinding.FragmentCCRApprovalRequestBinding
import com.stetig.solitaire.databinding.FragmentCCRDetailBinding
import com.stetig.solitaire.databinding.FragmentCpCreationApprovalBinding
import com.stetig.solitaire.databinding.FragmentCpCreationApprovalSubdetailBinding
import com.stetig.solitaire.utils.Utils
import io.reactivex.observers.DisposableObserver
import org.acra.ACRA.log

class CCRDetailFragment : BaseFragment() {
    lateinit var activity: MainActivity
    lateinit var binding: FragmentCCRDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_c_c_r_detail, container, false)
        initView(binding.root)
        return binding.root
    }

    override fun initView(rootView: View?) {

    }

    override fun onResume() {
        super.onResume()

        try {
            val commonClassForQuery = CommonClassForQuery.getInstance(activity, activity.getRestClient())!!
            if (arguments != null) {
                val id = arguments?.getString(Keys.CAM_ID, "")
                commonClassForQuery.getCampaignTableDetails(
                    Query.CCR_APPROVAL_LIST_DETAIL + Utils.buildQueryParameter(id), onDataReceiveListener
                )
            }
        } catch (e: Exception) {
        }
    }

    private var onDataReceiveListener = object : CommonClassForQuery.OnDataReceiveListener {
        override fun onDataReceive(data: Any) {
            if (data is CpCreationApproval && data.records.isNotEmpty()) {

                val record = data.records[0]

                binding.name.text = Utils.checkValueOrGiveEmpty(record?.name)
                binding.budget.text = Utils.checkValueOrGiveEmpty(record?.Updated_Budgeted_Cost_in_Campaign__c)
                binding.status.text = Utils.checkValueOrGiveEmpty(record?.status)
                binding.sDate.text = Utils.getFormattedDateYYYYMMDD(record?.Updated_Campaign_Start_Date__c)
                binding.eDate.text = Utils.getFormattedDateYYYYMMDD(record?.Updated_Campaign_End_Date__c)

                binding.approveBtn.setOnClickListener {
                    val userAccount = SalesforceSDKManager.getInstance().userAccountManager.currentUser
                    val commonClassForApi: CommonClassForApi = CommonClassForApi.getInstance(activity)!!
                    val auth = "Bearer " + userAccount.authToken
                    val data = SendCpCreaitonApprovalRequest(
                        BilkEmailSMSId = data.records[0]?.id.toString(),
                        status = "Approve",
                        comment = binding.commentBox.text.toString()
                    )
                    commonClassForApi.CCRApprovalRequest(disposableObserver, data, auth)
                }

                binding.rejectBtn.setOnClickListener {
                    val userAccount =
                        SalesforceSDKManager.getInstance().userAccountManager.currentUser
                    val commonClassForApi: CommonClassForApi =
                        CommonClassForApi.getInstance(activity)!!
                    val auth = "Bearer " + userAccount.authToken
                    val data = SendCpCreaitonApprovalRequest(
                        BilkEmailSMSId = data.records[0]?.id.toString(),
                        status = "Reject",
                        comment = binding.commentBox.text.toString()
                    )
                    commonClassForApi.CpCreationApprovalRequest(disposableObserver, data, auth)

                }
            }
        }

        override fun onError(obj: String) {
            Log.e(javaClass.name, "onError: $obj")
        }

    }
    private var disposableObserver: DisposableObserver<CpCreationApprovalResponse> =
        object : DisposableObserver<CpCreationApprovalResponse>() {
            override fun onNext(callStatusResponse: CpCreationApprovalResponse) {
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