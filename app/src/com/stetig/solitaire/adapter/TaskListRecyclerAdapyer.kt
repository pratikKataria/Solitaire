package com.stetig.solitaire.adapter

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stetig.solitaire.R
import com.stetig.solitaire.activity.MainActivity
import com.stetig.solitaire.api.Keys
import com.stetig.solitaire.data.ManualTaskListResponse
import com.stetig.solitaire.data.SolitaireCreateTask
import com.stetig.solitaire.data.Task
import com.stetig.solitaire.databinding.CardViewCreatetaskBinding
import com.stetig.solitaire.utils.Utils

/**
 * Created by Pratik Kataria on 27-11-2020.
 */
class TaskListRecyclerAdapyer(private var context: Context, private var projectList: List<Task.Record?>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val cardViewProjectsBinding = CardViewCreatetaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProjectDetailCardViewHolder(cardViewProjectsBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val projectDetailCardViewHolder = holder as ProjectDetailCardViewHolder
        val record = projectList[position]
        projectDetailCardViewHolder.cardViewProjectsBinding.markAsComplete.visibility = View.GONE


        val whatNameOrProjectRName = record?.what?.name ?: record?.customerNameR?.name
//        projectDetailCardViewHolder.cardViewProjectsBinding.projectName.text = Utils.getFormattedDateSF(projectList[position].)
//        projectDetailCardViewHolder.cardViewProjectsBinding.tvComments.text = Utils.checkValueOrGiveEmpty(record?.Description)

        projectDetailCardViewHolder.cardViewProjectsBinding.name.text = Utils.checkValueOrGiveEmpty(whatNameOrProjectRName)
        projectDetailCardViewHolder.cardViewProjectsBinding.dueDate.text = Utils.getFormattedDateWithTimeSF(record?.nextActionDate)
        projectDetailCardViewHolder.cardViewProjectsBinding.visitProposedDate.text = Utils.getFormattedDateWithTimeSF(record?.Call_Proposed_Date_Of_Visit__c)
        projectDetailCardViewHolder.cardViewProjectsBinding.createdDate.text = Utils.getFormattedDateWithTimeSF(record?.createdDate)
        projectDetailCardViewHolder.cardViewProjectsBinding.tvSubject.text = Utils.checkValueOrGiveEmpty(record?.Subject)

        if (record != null) {
            if (record.Call_Proposed_Date_Of_Visit__c != null) {
                projectDetailCardViewHolder.cardViewProjectsBinding.llVisitProposed.visibility = View.VISIBLE
                projectDetailCardViewHolder.cardViewProjectsBinding.llDueDate.visibility = View.GONE
             } else {
                projectDetailCardViewHolder.cardViewProjectsBinding.llVisitProposed.visibility = View.GONE
                projectDetailCardViewHolder.cardViewProjectsBinding.llDueDate.visibility = View.VISIBLE
            }
        }


        if (record?.attributes?.type == "Site_Visit__c") {
            projectDetailCardViewHolder.cardViewProjectsBinding.llSubject.visibility = View.GONE
//            projectDetailCardViewHolder.cardViewProjectsBinding.llComments.visibility = View.GONE
        }

        if (record?.attributes?.type == "Site_Visit__c") {
            projectDetailCardViewHolder.cardViewProjectsBinding.markAsComplete.visibility = View.VISIBLE
        } else {
            projectDetailCardViewHolder.cardViewProjectsBinding.markAsComplete.visibility = View.GONE
        }

        var completed = record?.taskStatus?.equals("Completed") == true || record?.taskStatus?.equals("Complete") == true

        projectDetailCardViewHolder.cardViewProjectsBinding.markAsComplete.text = if (record?.attributes?.type == "Site_Visit__c") "FEEDBACK" else "MARK AS COMPLETE"
//        if (completed)
//            projectDetailCardViewHolder.cardViewProjectsBinding.markAsComplete.backgroundTintList = context?.getResources()?.getColorStateList(R.color.light_grey)
//        projectDetailCardViewHolder.cardViewProjectsBinding.activityDateLayoutLl.visibility = if (record?.attributes?.type == "Site_Visit__c") View.GONE else View.VISIBLE

        projectDetailCardViewHolder.cardViewProjectsBinding.markAsComplete.setOnClickListener {

            if (record?.attributes?.type == "Site_Visit__c") {
                val bundle = Bundle()
                bundle.putString(Keys.TYPE_ENQUIRY, record?.typeofEnquiry)
                bundle.putString(Keys.SITE_VISIT_ID, record?.id)
//                projectList[position].id?.let { it1 -> updateFeedbackForm(it1, bundle) }
            } else if (!completed) {
//                projectList[position].id?.let { completeTask(it) }

            }
        }

        if (record?.attributes?.type != "Site_Visit__c") {
            val params = projectDetailCardViewHolder.cardViewProjectsBinding.llOptyComplete.layoutParams
            projectDetailCardViewHolder.cardViewProjectsBinding.llOptyComplete.gravity = Gravity.CENTER
            params.width = 510

        }

        projectDetailCardViewHolder.cardViewProjectsBinding.opportunityDetail.setOnClickListener {

            val id = if (record?.attributes?.type == "Site_Visit__c") record.customerNameC else record?.what?.id

            val bundle = Bundle()
            bundle.putString(Keys.OPP_ID, id)
            if (context is MainActivity) (context as MainActivity).navHostFragment.navController.navigate(R.id.action_createdtask_to_opportunityDetailFragment, bundle)
        }

    }

    override fun getItemCount() = projectList.size

    class ProjectDetailCardViewHolder(var cardViewProjectsBinding: CardViewCreatetaskBinding) : RecyclerView.ViewHolder(cardViewProjectsBinding.root)
}