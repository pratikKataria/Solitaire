package com.stetig.solitaire.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.stetig.callsync.base.BaseFragment
import com.stetig.solitaire.R
import com.stetig.solitaire.api.Keys
import com.stetig.solitaire.databinding.FragmentMenuBinding

class MenuFragment : BaseFragment() {
    private lateinit var binding: FragmentMenuBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_menu, container, false)
        initView(binding.root)
        return binding.root
    }

    override fun initView(rootView: View?) {
        binding.icMenuOne.setOnClickListener {
            val bundle = Bundle()
            bundle.putBoolean(Keys.IS_TODAY, true)
            bundle.putBoolean(Keys.FROM_MENU, true)
            navigateTo(R.id.action_menuFragment_to_taskFragment, bundle)
        }
        binding.icMenuTwo.setOnClickListener { navigateTo(R.id.action_menuFragment_to_allActiveOpportunityFragment) }
        binding.icMenuThree.setOnClickListener {
            val bundle = Bundle()
            bundle.putBoolean(Keys.EXP_OPP, true)
            bundle.putString(Keys.OPP_TYPE, getString(R.string.expiring_opportunities_for_next_7_days))
            navigateTo(R.id.action_menuFragment_to_opportunityFragment, bundle)
        }
        binding.icMenuSix.setOnClickListener{
            val bundle = Bundle()
            navigateTo(R.id.action_menuFragment_to_approvalFragment, bundle)
        }
//        binding.icMenuFour.setOnClickListener { navigateTo(R.id.action_menuFragment_to_stageWiseOpportunityFragment) }
        binding.icMenuFive.setOnClickListener {
            val bundle = Bundle()
            bundle.putBoolean(Keys.EXP_OPP, true)
            bundle.putString(Keys.OPP_TYPE, getString(R.string.action_opportunity_for_today))
            navigateTo(R.id.action_menuFragment_to_opportunityFragment, bundle)
        }
    }

    override fun onClick(view: View) {
        super.onClick(view)
    }

}