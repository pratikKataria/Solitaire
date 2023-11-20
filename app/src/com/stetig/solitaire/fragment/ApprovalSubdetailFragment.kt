package com.stetig.solitaire.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.salesforce.androidsdk.app.SalesforceSDKManager
import com.stetig.callsync.base.BaseFragment
import com.stetig.solitaire.R
import com.stetig.solitaire.activity.MainActivity
import com.stetig.solitaire.api.CommonClassForApi
import com.stetig.solitaire.api.CommonClassForQuery
import com.stetig.solitaire.api.Keys
import com.stetig.solitaire.api.Query
import com.stetig.solitaire.data.*
import com.stetig.solitaire.databinding.FragmentApprovalSubdetailBinding
import com.stetig.solitaire.utils.Utils
import io.reactivex.observers.DisposableObserver


class ApprovalSubdetailFragment : BaseFragment() {
    private lateinit var oppid: String
    lateinit var activity: MainActivity
    lateinit var binding: FragmentApprovalSubdetailBinding
    private var commonClassForApi: CommonClassForApi? = null

//    lateinit var binding2 : FragmentApprovalBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, com.stetig.solitaire.R.layout.fragment_approval_subdetail, container, false)
        initView(binding.root)
        return binding.root
    }

    override fun initView(rootView: View?) {

        val csId = arguments?.getString(Keys.CS_ID)
        val ppId = arguments?.getString(Keys.PP_ID)

        if (csId == null)
            binding.tblCostSheet.visibility = View.GONE
        if (ppId == null)
            binding.llPaymentPlan.visibility = View.GONE

        try {
            if (arguments != null) {
                val id = arguments?.getString(Keys.OPP_ID, "")
                commonClassForApi = CommonClassForApi.getInstance(activity)
                commonClassForApi?.getPaymentPlan(updateTokenResDisposableObserver, CallTaskRequest(opportunityId = id))
            }
        } catch (e: Exception) {

        }
    }

    override fun onResume() {
        super.onResume()

        try {
            val commonClassForQuery = CommonClassForQuery.getInstance(activity, activity.getRestClient())!!
            if (arguments != null) {
                val id = arguments?.getString(Keys.OPP_ID, "")
                commonClassForQuery.getApprovalTableDetails(Query.APPROVAL_TABLE_FIELDS + Utils.buildQueryParameter(id), onDataReceiveListener)
            }
        } catch (e: Exception) {
        }
    }

    private var onDataReceiveListener = object : CommonClassForQuery.OnDataReceiveListener {
        override fun onDataReceive(data: Any) {
            if (data is ApprovalTable && data.records.isNotEmpty()) {

                binding.baserateCurrent.text = Utils.convertToIndianCurrency(data.records[0].baseRate)
                binding.baseratePrevious.text = Utils.convertToIndianCurrency(data.records[0].baseRateOriginal)
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
                binding.amenitiesPrevious.text = Utils.convertToIndianCurrency((data.records[0].amenities_original))
                binding.legalchargesCurrent.text = Utils.convertToIndianCurrency((data.records[0].legalCharges))
                binding.legalchargesPrevious.text = Utils.convertToIndianCurrency((data.records[0].legalCharges_original))
                binding.considerationvalueCurrent.text = Utils.convertToIndianCurrency((data.records[0].x1_totalConsideration_value))
                binding.considerationvaluePrevious.text = Utils.convertToIndianCurrency((data.records[0].totalConsideration_value_original))
                binding.considerationvaluediffCurrent.text = Utils.convertToIndianCurrency((data.records[0].totalConsideration_value_diff))

                binding.stampdutyPrevious.text = Utils.convertToIndianCurrency((data.records[0].stampDuty_waivedOff))
                binding.registrationPrevious.text = Utils.convertToIndianCurrency((data.records[0].registration_Charges_WaivedOff))
                binding.gstPrevious.text = Utils.convertToIndianCurrency((data.records[0].GST_waivedOff))

                binding.approveBtn.setOnClickListener {

                    if (binding.commentBox.text.toString().isEmpty()) {
                        Utils.showToast(context, "Please Enter Comments")
                        return@setOnClickListener;
                    }


                    val csId = arguments?.getString(Keys.CS_ID)
                    val ppId = arguments?.getString(Keys.PP_ID)
                    var approvalType = ""

                    if (csId != null && ppId != null) approvalType = "Both"
                    else if (ppId != null) approvalType = "Payment Plan"
                    else approvalType = "Cost sheet"


                    val userAccount = SalesforceSDKManager.getInstance().userAccountManager.currentUser
                    val commonClassForApi: CommonClassForApi = CommonClassForApi.getInstance(activity)!!
                    val auth = "Bearer " + userAccount.authToken
                    val data = SendApprovalRequest(
                        costsheetId = csId,
                        paymentplanId = ppId,
                        status = "Approve",
                        type = approvalType,
                        comment = binding.commentBox.text.toString(),
                    )
                    commonClassForApi.ApprovalRequest(disposableObserver, data, auth)
                }


                binding.rejectBtn.setOnClickListener {
                    val userAccount = SalesforceSDKManager.getInstance().userAccountManager.currentUser
                    val commonClassForApi: CommonClassForApi = CommonClassForApi.getInstance(activity)!!
                    val auth = "Bearer " + userAccount.authToken

                    val csId = arguments?.getString(Keys.CS_ID)
                    val ppId = arguments?.getString(Keys.PP_ID)
                    var approvalType = ""

                    if (csId != null) approvalType = "Cost sheet"
                    else if (ppId != null) approvalType = "Payment Plan"
                    else approvalType = "Both"


                    val data = SendApprovalRequest(
                        costsheetId = data.records[0].id,
                        paymentplanId = ppId,
                        status = "Reject",
                        type = approvalType,
                        comment = binding.commentBox.text.toString(),
                    )
                    commonClassForApi.ApprovalRequest(disposableObserver, data, auth)

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

    var updateTokenResDisposableObserver: DisposableObserver<PaymentPlanResponse> =
        object : DisposableObserver<PaymentPlanResponse>() {
            override fun onNext(updateTokenRes: PaymentPlanResponse) {
                if (updateTokenRes != null) {
                    val projects: PaymentPlanList = Gson().fromJson<PaymentPlanList>(updateTokenRes.payPlanList, PaymentPlanList::class.java)
                    Log.e("On ApprovalSubDetailFragmen t projects", "onNext: $projects")


                    // Get the TableLayout defined in the XML layout

                    // Define the number of rows and columns for your table

                    // Define the number of rows and columns for your table
                    val numRows = 3
                    val numCols = 5

                    // Loop through rows to create rows

                    // Loop through rows to create rows
                    for (i in projects) {
                        val tableRow = TableRow(activity)
                        tableRow.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT)
                        tableRow.setBackgroundResource(R.drawable.border_horizontal); // Apply horizontal border

                        // Loop through columns to create columns
                        for (j in 0 until numCols) {


                            val textView = TextView(activity)
                            textView.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT)
                            textView.gravity = Gravity.LEFT
                            textView.setPadding(25, 16, 25, 16)
                            textView.setBackgroundResource(R.drawable.border_vertical); // Apply vertical border

                            // Set text for the TextView (you can set dynamic content here)
                            if (j == 0) textView.text = "${i.projectConstructionStagesR?.name ?: "Not Available"}"
                            if (j == 1) textView.text = "${i.amountValueC}"
                            if (j == 2) textView.text = "${i.daysMonthsValueC}"
                            if (j == 3) textView.text = "${i.daysMonthsValueC}"
                            if (j == 4) textView.text = "${i.daysMonthsValueC}"


                            tableRow.addView(textView)
                        }

                        // Add the row to the TableLayout
                        binding.paymentPlanTableLayout.addView(tableRow)
                    }
                }

            }

            override fun onError(e: Throwable) {

            }

            override fun onComplete() {}
        }

}

