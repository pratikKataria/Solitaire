package com.stetig.solitaire.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.stetig.callsync.base.BaseFragment
import com.stetig.solitaire.R
import com.stetig.solitaire.activity.MainActivity
import com.stetig.solitaire.api.CommonClassForApi
import com.stetig.solitaire.api.Keys
import com.stetig.solitaire.data.UpdateOpportunityReq
import com.stetig.solitaire.data.UpdateOpportunityRes
import com.stetig.solitaire.databinding.FragmentUpdateOpportunityBinding
import com.stetig.solitaire.utils.Utils
import com.google.android.material.transition.MaterialSharedAxis
import com.salesforce.androidsdk.app.SalesforceSDKManager
import io.reactivex.observers.DisposableObserver


class UpdateOpportunityFragment : BaseFragment(), OnItemSelectedListener {

    lateinit var binding: FragmentUpdateOpportunityBinding
    lateinit var activity: MainActivity
    private lateinit var projectName: String
    private var projectID: String? = null
    private var oppID: String? = null
    private var oppName: String? = null
    private var stage = ""
    private var currentStage = ""
    private var closedLostReason: String? = ""
    var isCloseLost = true
    private var commonClassForApi: CommonClassForApi? = null
    private val stageList = arrayOf("Qualification",
            "Needs Analysis",
            "Proposal",
            "Negotiation",
            "Closed lost"
    )

    private val closedLostReasons = arrayOf(
        "Choose close lost reason",
        "Interested in other solitaire property",//1
        "Lost to Competitor",//2
        "Not in Budget",//3
        "Not interested in location",//4
        "Not responding",//5
        "Required inventory not available",//6
        "Not enquired with solitaire"//7
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        projectName = arguments?.getString(Keys.PROJECT, "") ?: ""
        projectID = arguments?.getString(Keys.PROJECT_ID, "") ?: ""
        currentStage = arguments?.getString(Keys.CURRENT_STAGE, "") ?: ""
        oppName = arguments?.getString(Keys.OPP_NAME, "") ?: ""
        oppID = arguments?.getString(Keys.OPP_ID, null)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_update_opportunity, container, false)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        initView(rootView = binding.root)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun initView(rootView: View?) {

        binding.name.text = oppName
        binding.projectName.text = projectName

        val aa2: ArrayAdapter<*> = ArrayAdapter<Any?>(activity, android.R.layout.simple_spinner_item, stageList)
        aa2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerStage.onItemSelectedListener = this
        binding.spinnerStage.adapter = aa2

        val aa: ArrayAdapter<*> = ArrayAdapter<Any?>(activity, android.R.layout.simple_spinner_item, closedLostReasons)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCloseLost.onItemSelectedListener = this
        binding.spinnerCloseLost.adapter = aa


        binding.spinnerStage.setSelection(if (stageList.indexOf(currentStage) != -1) stageList.indexOf(currentStage) else 0)

        commonClassForApi = CommonClassForApi.getInstance(activity)
        binding.submit.setOnClickListener {

            if (isCloseLost) {
                if (closedLostReason == null) {
                    Utils.setToast(activity, "select close lost reason")
                    return@setOnClickListener
                }
                if (closedLostReason.equals("Choose close lost reason", ignoreCase = true)) {
                    Utils.setToast(activity, "select close lost reason")
                    return@setOnClickListener
                }
            }


            val account = SalesforceSDKManager.getInstance().userAccountManager.currentUser
            val salesUserId = account.username

            val data = UpdateOpportunityReq(
                    address = "",
                    closedlostreason = closedLostReason!!,
                    oppId = oppID ?: "",
                    project = projectID ?: "",
                    salesuserid = salesUserId,
                    stage = stage)

            val auth = "Bearer " + account.authToken

            commonClassForApi!!.updateOpportunity(disposableObserver, data, auth)
        }
    }

    private var disposableObserver: DisposableObserver<UpdateOpportunityRes> = object : DisposableObserver<UpdateOpportunityRes>() {
        override fun onNext(callStatusResponse: UpdateOpportunityRes) {
            activity.onBackPressed()

            Utils.setToast(activity, callStatusResponse.message)
        }

        override fun onError(e: Throwable) {
            Log.e(javaClass.name, "onError: 337 " + e.message)
        }

        override fun onComplete() {}
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = if (context is MainActivity) context else getActivity() as MainActivity
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, p3: Long) {
        view!!.setPadding(0, view.paddingTop, view.paddingRight, view.paddingBottom)

        when (parent?.id) {
            R.id.spinner_stage -> {
                stage = stageList[position]
                if (stage == "Closed lost") {
                    binding.updateOpportunityLayoutClosedLost.visibility = View.VISIBLE
                    closedLostReason = closedLostReasons[0]
                    isCloseLost = true
                } else {
                    binding.updateOpportunityLayoutClosedLost.visibility = View.GONE
                    closedLostReason = ""
                    isCloseLost = false
                }
            }
            R.id.spinner_close_lost -> closedLostReason = closedLostReasons[position]
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }


}