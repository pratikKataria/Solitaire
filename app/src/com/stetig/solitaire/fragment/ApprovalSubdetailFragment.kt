package com.stetig.solitaire.fragment
import android.annotation.SuppressLint
import android.content.Context
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
import com.stetig.solitaire.adapter.TaskRecyclerAdapter
import com.stetig.solitaire.api.CommonClassForApi
import com.stetig.solitaire.api.CommonClassForQuery
import com.stetig.solitaire.api.Keys
import com.stetig.solitaire.api.Query
import com.stetig.solitaire.data.*
import com.stetig.solitaire.databinding.FragmentApprovalBinding
import com.stetig.solitaire.utils.Utils
import com.stetig.solitaire.databinding.FragmentApprovalSubdetailBinding
import io.reactivex.observers.DisposableObserver


class ApprovalSubdetailFragment : BaseFragment() {
    private lateinit var oppid: String
    lateinit var activity: MainActivity
    lateinit var binding: FragmentApprovalSubdetailBinding
//    lateinit var binding2 : FragmentApprovalBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_approval_subdetail, container, false)
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
                val id = arguments?.getString(Keys.OPP_ID, "")
                commonClassForQuery.getApprovalTableDetails(Query.APPROVAL_TABLE_FIELDS+Utils.buildQueryParameter(id), onDataReceiveListener)
            }
        } catch (e: Exception) {
        }
    }

    private var onDataReceiveListener = object : CommonClassForQuery.OnDataReceiveListener {
        override fun onDataReceive(data: Any) {
            if (data is ApprovalTable && data.records.isNotEmpty()) {

                binding.baserateCurrent.text =
                    Utils.convertToIndianCurrency(data.records[0].baseRate)
                binding.baseratePrevious.text =
                    Utils.convertToIndianCurrency(data.records[0].baseRateOriginal)
                binding.totalamountCurrent.text = Utils.convertToIndianCurrency((data.records[0].totalAmountforUnit))
                binding.infrastructureCurrent.text = Utils.convertToIndianCurrency((data.records[0].infrastructureCharges))
                binding.infrastructurePrevious.text = Utils.convertToIndianCurrency((data.records[0].infrastructureChargesOriginal))
                binding.msebDevelopmentCurrent.text = Utils.convertToIndianCurrency((data.records[0].eIBE_MSEB_development_charges))
                binding.msebDevelopmentPrevious.text = Utils.convertToIndianCurrency((data.records[0].IBE_MSEM_development_charges_original))
                binding.premiumCurrent.text = Utils.convertToIndianCurrency((data.records[0].premium_charges))
                binding.premiumPrevious.text = Utils.convertToIndianCurrency((data.records[0].premium_charges_original))
                binding.floorCurrent.text = Utils.convertToIndianCurrency((data.records[0].floorRise))
                binding.floorPrevious.text = Utils.convertToIndianCurrency((data.records[0].floorRise_original))
                binding.amenitiesCurrent.text = Utils.convertToIndianCurrency((data.records[0].amenities))
                binding.amenitiesPrevious.text =  Utils.convertToIndianCurrency((data.records[0].amenities_original))
                binding.legalchargesCurrent.text =  Utils.convertToIndianCurrency((data.records[0].legalCharges))
                binding.legalchargesPrevious.text =  Utils.convertToIndianCurrency((data.records[0].legalCharges_original))
                binding.considerationvalueCurrent.text =  Utils.convertToIndianCurrency((data.records[0].x1_totalConsideration_value))
                binding.considerationvaluePrevious.text =  Utils.convertToIndianCurrency((data.records[0].totalConsideration_value_original))
                binding.considerationvaluediffCurrent.text =  Utils.convertToIndianCurrency((data.records[0].totalConsideration_value_diff))

                binding.stampdutyPrevious.text =  Utils.convertToIndianCurrency((data.records[0].stampDuty_waivedOff))
                binding.registrationPrevious.text =  Utils.convertToIndianCurrency((data.records[0].registration_Charges_WaivedOff))
                binding.gstPrevious.text =  Utils.convertToIndianCurrency((data.records[0].GST_waivedOff))

                binding.approveBtn.setOnClickListener{

                    if (binding.commentBox.text.toString().isEmpty()){
                        Utils.showToast(context, "Please Enter Comments")
                        return@setOnClickListener;
                    }


                    val userAccount = SalesforceSDKManager.getInstance().userAccountManager.currentUser
                    val commonClassForApi: CommonClassForApi = CommonClassForApi.getInstance(activity)!!
                    val auth = "Bearer " + userAccount.authToken
                    val data = SendApprovalRequest(
                        costsheetId = data.records[0]?.id,
                        paymentplanId = " ",
                        status = "Approve",
                        type = "Cost sheet",
                        comment = binding.commentBox.text.toString(),
                    )
                    commonClassForApi.ApprovalRequest(disposableObserver,data,auth)

                }
                binding.rejectBtn.setOnClickListener{
                    val userAccount = SalesforceSDKManager.getInstance().userAccountManager.currentUser
                    val commonClassForApi: CommonClassForApi = CommonClassForApi.getInstance(activity)!!
                    val auth = "Bearer " + userAccount.authToken
                    val data = SendApprovalRequest(
                        costsheetId = data.records[0].id,
                        paymentplanId = " ",
                        status = "Reject",
                        type = "Cost sheet",
                        comment = binding.commentBox.text.toString(),
                    )
                    commonClassForApi.ApprovalRequest(disposableObserver,data,auth)

                }
            }
        }

        override fun onError(obj: String) {
            Log.e(javaClass.name, "onError: $obj")
        }

    }
    private var disposableObserver: DisposableObserver<SendApprocalRequestResponse> = object : DisposableObserver<SendApprocalRequestResponse>() {
        override fun onNext(callStatusResponse: SendApprocalRequestResponse) {
            Utils.setToast(activity, callStatusResponse.message)
            activity.navHostFragment.findNavController().popBackStack()
        }
        override fun onError(e: Throwable) {
            Log.e(javaClass.name, "onError: 33`7 " + e.message)
        }
        override fun onComplete() {}
    }

    override fun onAttach(context: Context) {
        if (context is MainActivity) activity = context
        super.onAttach(context)
    }
}