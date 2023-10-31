package com.stetig.solitaire.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stetig.solitaire.R
import com.stetig.solitaire.activity.MainActivity
import com.stetig.solitaire.api.Keys
import com.stetig.solitaire.data.Sitevisit
import com.stetig.solitaire.databinding.CardViewSitevisitBinding
import com.stetig.solitaire.utils.Utils

/**
 * Created by Pratik Kataria on 27-11-2020.
 */
class sitevisitRecyclerAdapter(
    private var context: Context,
    private var projectList: List<Sitevisit.Record>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val cardViewProjectsBinding =
            CardViewSitevisitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProjectDetailCardViewHolder(cardViewProjectsBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val projectDetailCardViewHolder = holder as ProjectDetailCardViewHolder
        val data = projectList[position]
        projectDetailCardViewHolder.cardViewProjectsBinding.siteVisitName.text =
            Utils.checkValueOrGiveEmpty(data.name)
        projectDetailCardViewHolder.cardViewProjectsBinding.siteVisitProjectName.text =
            Utils.checkValueOrGiveEmpty(data.oppproject)
        projectDetailCardViewHolder.cardViewProjectsBinding.siteVisitDate.text =
            Utils.getFormattedDateWithTimeSF(data.createdDate)
        projectDetailCardViewHolder.cardViewProjectsBinding.siteVisitType.text =
            Utils.checkValueOrGiveEmpty(data.typeofvisit)
        projectDetailCardViewHolder.cardViewProjectsBinding.siteVisitEnquiryType.text =
            Utils.checkValueOrGiveEmpty(data.typeofenquiry)
        projectDetailCardViewHolder.cardViewProjectsBinding.siteVisitSourcingManager.text =
            Utils.checkValueOrGiveEmpty(data.oppsourcingmanager)

        projectDetailCardViewHolder.cardViewProjectsBinding.feedbackBtn.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(Keys.TYPE_ENQUIRY, data?.typeofenquiry)
            bundle.putString(Keys.SITE_VISIT_ID, data?.id)
            if (context is MainActivity) (context as MainActivity).navHostFragment.navController.navigate(R.id.action_sitevisitFragment_to_Feedbackform, bundle)
        }

        projectDetailCardViewHolder.cardViewProjectsBinding.viewDetailsBtn.setOnClickListener {

            val bundle = Bundle()
            val id = data.customerNameId
            bundle.putString(Keys.OPP_ID, id)


            if (context is MainActivity) (context as MainActivity).navHostFragment.navController.navigate(
                R.id.action_sitevisitFragment_to_opportunityDetailFragment,
                bundle
            )
        }

//        projectDetailCardViewHolder.cardViewProjectsBinding.include.call.setOnClickListener { callNumber(data.accountMobileNumberC, context) }
//        projectDetailCardViewHolder.cardViewProjectsBinding.include.whatsapp.setOnClickListener { sendWhatsAppToSpecificNumber(data.accountMobileNumberC, context) }
//        projectDetailCardViewHolder.cardViewProjectsBinding.include.task.setOnClickListener {
//            (context as MainActivity).showPopUp(true, data.id, data.accountMobileNumberC)
//        }
//        projectDetailCardViewHolder.cardViewProjectsBinding.include.timer.setOnClickListener {
//            val bundle = Bundle()
//            bundle.putString(Keys.OPP_ID, data.id)
//            (context as MainActivity).navHostFragment.navController.navigate(R.id.action_opportunityFragment_to_timeLineFragment, bundle)
//        }
//        projectDetailCardViewHolder.cardViewProjectsBinding.linearLayout.setOnClickListener {
//            val bundle = Bundle()
//            bundle.putString(Keys.OPP_ID, data.id)
//            (context as MainActivity).navHostFragment.navController.navigate(R.id.action_opportunityFragment_to_opportunityDetailFragment, bundle)
//        }
//        projectDetailCardViewHolder.cardViewProjectsBinding.include.share.setOnClickListener {
//            sendProjectDetails(data.projectC ?: "", data.accountMobileNumberC
//                    ?: "", data.accountEmailIdC
//                    ?: "", context, R.id.action_opportunityFragment_to_projectDetailFragment)
//        }

    }

    override fun getItemCount() = projectList.size

    class ProjectDetailCardViewHolder(var cardViewProjectsBinding: CardViewSitevisitBinding) :
        RecyclerView.ViewHolder(cardViewProjectsBinding.root)
}