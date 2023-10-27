
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
import com.stetig.solitaire.databinding.FragmentSourceChangeApprovalSubdetailBinding
import io.reactivex.observers.DisposableObserver


class SourceChangeSubDetailFragment : BaseFragment() {
    lateinit var activity: MainActivity
    lateinit var binding: FragmentSourceChangeApprovalSubdetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_source_change_approval_subdetail,
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
                commonClassForQuery.getCampaignTableDetails(
                    Query.SOURCE_CHANGE_TABLE_FIELDS + Utils.buildQueryParameter(
                        id
                    ), onDataReceiveListener
                )
            }
        } catch (e: Exception) {
        }
    }

    private var onDataReceiveListener = object : CommonClassForQuery.OnDataReceiveListener {
        override fun onDataReceive(data: Any) {
            if (data is SourceChangeApproval && data.records.isNotEmpty()) {

                val record = data.records[0]

                binding.sourceNew.text = Utils.checkValueOrGiveEmpty(record.newsource)
                binding.sourceOld.text =
                    Utils.checkValueOrGiveEmpty(record.oldsource)
                binding.subsourceOld.text =
                    Utils.checkValueOrGiveEmpty(record.oldsubsource)
                binding.subsourceNew.text = record.newsubsource
//                binding.sourceDetailOld.text = record.endDate
//                binding.sourceDetailNew.text = record.budgetedCost.toString()
//                binding.partnerNameNew.text = record.budgetedCost.toString()
//
//                binding.partnerNameOld.text = record.budgetedCost.toString()
//                binding.partnerRecordOld.text = record.budgetedCost.toString()
//                binding.partnerRecordNew.text = record.budgetedCost.toString()

                if (record.partnertype == "Channel Partner" || record.partnertype == "Leasing Partner") {
                    binding.partnerRecordNew.visibility = View.VISIBLE
                    binding.partnerRecordOld.visibility = View.VISIBLE
                    binding.partnerNameOld.visibility = View.VISIBLE
                    binding.partnerNameNew.visibility = View.VISIBLE
                }

                binding.approveBtn.setOnClickListener {
                    val userAccount =
                        SalesforceSDKManager.getInstance().userAccountManager.currentUser
                    val commonClassForApi: CommonClassForApi = CommonClassForApi.getInstance(activity)!!
                    val auth = "Bearer " + userAccount.authToken
                    val data = SendSourceChangeApprovalRequest(
                        sitevisitId = data.records[0]?.id.toString(),
                        status = "Approve",
                        comment=binding.commentBox.text.toString()
                    )
                    commonClassForApi.SourceChangeApprovalRequest(disposableObserver, data, auth)
                }

                binding.rejectBtn.setOnClickListener {
                    val userAccount =
                        SalesforceSDKManager.getInstance().userAccountManager.currentUser
                    val commonClassForApi: CommonClassForApi =
                        CommonClassForApi.getInstance(activity)!!
                    val auth = "Bearer " + userAccount.authToken
                    val data = SendSourceChangeApprovalRequest(
                        sitevisitId = data.records[0]?.id.toString(),
                        status = "Reject",
                        comment=binding.commentBox.text.toString()
                    )
                    commonClassForApi.SourceChangeApprovalRequest(disposableObserver, data, auth)

                }
            }
        }

        override fun onError(obj: String) {
            Log.e(javaClass.name, "onError: $obj")
        }

    }
    private var disposableObserver: DisposableObserver<SendSourceChangeApprovalResponse> =
        object : DisposableObserver<SendSourceChangeApprovalResponse>() {
            override fun onNext(callStatusResponse: SendSourceChangeApprovalResponse) {
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

