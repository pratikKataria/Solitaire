
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
import com.stetig.solitaire.databinding.FragmentCpCreationApprovalSubdetailBinding
import com.stetig.solitaire.utils.Utils
import io.reactivex.observers.DisposableObserver


class CpCreationApprovalSubDetailFragment : BaseFragment() {
    lateinit var activity: MainActivity
    lateinit var binding: FragmentCpCreationApprovalSubdetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_cp_creation_approval_subdetail,
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
                    Query.CP_CREATTION_TABLE_FIELDS + Utils.buildQueryParameter(
                        id
                    ), onDataReceiveListener
                )
            }
        } catch (e: Exception) {
        }
    }

    private var onDataReceiveListener = object : CommonClassForQuery.OnDataReceiveListener {
        override fun onDataReceive(data: Any) {
            if (data is CpCreationApproval && data.records.isNotEmpty()) {

                val record = data.records[0]

                binding.reraNo.text = Utils.checkValueOrGiveEmpty(record?.mahaRERAno)
                binding.reraExpDate.text =
                    Utils.checkValueOrGiveEmpty(record?.RERAexpData)
                binding.cpType.text =
                    Utils.checkValueOrGiveEmpty(record?.cpType)
                binding.spSubType.text  =Utils.checkValueOrGiveEmpty(record?.cpSubType)
                binding.zone.text = Utils.checkValueOrGiveEmpty(record?.zone)
                binding.activitiesPlanned.text =
                    Utils.checkValueOrGiveEmpty(record?.level1submissiondatetime)
                binding.officeLocation.text =
                    Utils.checkValueOrGiveEmpty(record?.officelocation)
                binding.expertise.text  =Utils.checkValueOrGiveEmpty(record?.expertise)


                binding.approveBtn.setOnClickListener {
                    val userAccount =
                        SalesforceSDKManager.getInstance().userAccountManager.currentUser
                    val commonClassForApi: CommonClassForApi =
                        CommonClassForApi.getInstance(activity)!!
                    val auth = "Bearer " + userAccount.authToken
                    val data = SendCpCreaitonApprovalRequest(
                        cpId = data.records[0]?.id.toString(),
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
                    val data = SendCpCreaitonApprovalRequest(
                        cpId = data.records[0]?.id.toString(),
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

