package com.stetig.solitaire.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stetig.solitaire.R
import com.stetig.solitaire.activity.MainActivity
import com.stetig.solitaire.api.Keys
import com.stetig.solitaire.data.Opportunity
import com.stetig.solitaire.databinding.CardViewOpportuntiyBinding
import com.stetig.solitaire.utils.Utils

/**
 * Created by Pratik Kataria on 27-11-2020.
 */
class AllActiveOpportunityRecyclerAdapter(private var context: Context, private var projectList: List<Opportunity.Record>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val cardViewProjectsBinding = CardViewOpportuntiyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProjectDetailCardViewHolder(cardViewProjectsBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val projectDetailCardViewHolder = holder as ProjectDetailCardViewHolder
        val data = projectList[position]
        projectDetailCardViewHolder.cardViewProjectsBinding.name.text = Utils.checkValueOrGiveEmpty(data.name)
        projectDetailCardViewHolder.cardViewProjectsBinding.projectName.text = Utils.checkValueOrGiveEmpty(data.projectC)
        projectDetailCardViewHolder.cardViewProjectsBinding.date.text = Utils.getFormattedDateSF_OpportunityCard(data.createdDate)

        projectDetailCardViewHolder.cardViewProjectsBinding.include.call.setOnClickListener { OpportunityRecyclerAdapter.callNumber(data.accountMobileNumberC, context) }
        projectDetailCardViewHolder.cardViewProjectsBinding.include.whatsapp.setOnClickListener { OpportunityRecyclerAdapter.sendWhatsAppToSpecificNumber(data.accountMobileNumberC, context) }
        projectDetailCardViewHolder.cardViewProjectsBinding.include.share.setOnClickListener {
            OpportunityRecyclerAdapter.smsNumber(data.accountMobileNumberC, context)
        }
//        projectDetailCardViewHolder.cardViewProjectsBinding.include.timer.setOnClickListener {
//            val bundle = Bundle()
//            bundle.putString(Keys.OPP_ID, data.id)
//            (context as MainActivity).navHostFragment.navController.navigate(R.id.action_allActiveOpportunityFragment_to_timeLineFragment, bundle)
//        }
        projectDetailCardViewHolder.cardViewProjectsBinding.linearLayout.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(Keys.OPP_ID, data.id)
            (context as MainActivity).navHostFragment.navController.navigate(R.id.action_allActiveOpportunityFragment_to_opportunityDetailFragment, bundle)
        }
//        projectDetailCardViewHolder.cardViewProjectsBinding.include.share.setOnClickListener { OpportunityRecyclerAdapter.sendProjectDetails(data.projectR.name?:"", data.accountMobileNumberC?:"", data.accountEmailIdC?:"", context, R.id.action_allActiveOpportunityFragment_to_projectDetailFragment) }

    }

    override fun getItemCount() = projectList.size

    class ProjectDetailCardViewHolder(var cardViewProjectsBinding: CardViewOpportuntiyBinding) : RecyclerView.ViewHolder(cardViewProjectsBinding.root)
}