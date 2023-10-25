package com.stetig.solitaire.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.stetig.callsync.base.BaseFragment
import com.stetig.solitaire.R
import com.stetig.solitaire.activity.MainActivity
import com.stetig.solitaire.adapter.OpportunityRecyclerAdapter
import com.stetig.solitaire.adapter.OpportunityRecyclerAdapter.Companion.callNumber
import com.stetig.solitaire.adapter.OpportunityRecyclerAdapter.Companion.sendWhatsAppToSpecificNumber
import com.stetig.solitaire.api.CommonClassForQuery
import com.stetig.solitaire.api.Keys
import com.stetig.solitaire.api.Query.Companion.OPPORTUNITY_DETAIL
import com.stetig.solitaire.data.Opportunity
import com.stetig.solitaire.databinding.FragmentOpportunityDetailBinding
import com.stetig.solitaire.utils.Utils
import com.google.android.material.transition.MaterialSharedAxis

class OpportunityDetailFragment : BaseFragment() {
    private lateinit var binding: FragmentOpportunityDetailBinding
    private lateinit var activity: MainActivity
    private lateinit var commonClassForQuery: CommonClassForQuery
    private lateinit var adapter: OpportunityRecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_opportunity_detail, container, false)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        initView(rootView = binding.root)
        return binding.root
    }

    override fun initView(rootView: View?) {

    }

    override fun onResume() {
        super.onResume()
        commonClassForQuery = CommonClassForQuery.getInstance(activity, activity.getRestClient())!!
        if (arguments != null) {
            val id = arguments?.getString(Keys.OPP_ID, "")
            commonClassForQuery.getOptyDetail(OPPORTUNITY_DETAIL + Utils.buildQueryParameter(id), onOpportunityListListener)
        }
    }

    private val onOpportunityListListener = object : CommonClassForQuery.OnDataReceiveListener {
        override fun onDataReceive(data: Any) {
            if (data is Opportunity) {

                if (data.records.isEmpty()) {
                    Utils.setToast(activity, "No Opportunity Found")
                    activity.onBackPressed()
                    return
                }

                val opty = data.records[0]
                binding.optDetailsName.text = Utils.checkValueOrGiveDef(opty.name)
                binding.optDetailsProjectSelected.text = Utils.checkValueOrGiveDef(opty.projectTypeC)
                binding.mobileNumberText.text = Utils.checkValueOrGiveDef(opty.accountMobileNumberC)
                binding.createdDate.text = Utils.getFormattedDateSF(opty.createdDate)
                binding.stage.text = Utils.checkValueOrGiveDef(opty.stageName)
                binding.expDate.text = Utils.checkValueOrGiveDef(opty.closeDate)
                binding.lastCallStatus.text = Utils.checkValueOrGiveDef(opty.salesCallAttemptStatusC)
                binding.lastCallDescription.text = Utils.checkValueOrGiveDef(opty.salesCallDescriptionC)

                binding.include.call.setOnClickListener { callNumber(opty.accountMobileNumberC, activity) }
                binding.include.whatsapp.setOnClickListener { sendWhatsAppToSpecificNumber(opty.accountMobileNumberC, activity) }

//                binding.stage.setOnClickListener {
//                    val bundle = Bundle()
//                    bundle.putString(Keys.OPP_ID, opty.id)
//                    bundle.putString(Keys.PROJECT, opty.projectC?:"")
//                    bundle.putString(Keys.PROJECT_ID, opty.projectC?:"")
//                    bundle.putString(Keys.OPP_NAME, opty.name)
//                    bundle.putString(Keys.CURRENT_STAGE, opty.stageName)
//                    navigateTo(R.id.action_opportunityDetailFragment_to_UpdateOpportunityFragment, bundle)
//                }
            }
        }

        override fun onError(obj: String) {

        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = if (context is MainActivity) context else getActivity() as MainActivity
    }
}