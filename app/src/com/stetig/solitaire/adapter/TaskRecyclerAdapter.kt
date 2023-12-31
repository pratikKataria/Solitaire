package com.stetig.solitaire.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stetig.solitaire.R
import com.stetig.solitaire.activity.MainActivity
import com.stetig.solitaire.api.Keys
import com.stetig.solitaire.data.Task
import com.stetig.solitaire.databinding.CardViewTaskBinding
import com.stetig.solitaire.utils.Utils
import org.acra.ACRA.log

/**
 * Created by Pratik Kataria on 27-11-2020.
 */
abstract class TaskRecyclerAdapter(
    private var context: Context?,
    private var projectList: List<Task.Record>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val cardViewProjectsBinding =
            CardViewTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProjectDetailCardViewHolder(cardViewProjectsBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val projectDetailCardViewHolder = holder as ProjectDetailCardViewHolder
        val record = projectList[position]

        val whatNameOrProjectRName = record?.what?.name ?: record.projectR?.name

        projectDetailCardViewHolder.cardViewProjectsBinding.name.text = Utils.checkValueOrGiveEmpty(whatNameOrProjectRName)
//        projectDetailCardViewHolder.cardViewProjectsBinding.projectName.text = Utils.checkValueOrGiveEmpty(projectList[position].)
        projectDetailCardViewHolder.cardViewProjectsBinding.activityDate.text = Utils.getFormattedDateSF(record?.createdDate)
        projectDetailCardViewHolder.cardViewProjectsBinding.createdDate.text = Utils.getFormattedDateSF(record?.createdDate)

        projectDetailCardViewHolder.cardViewProjectsBinding.markAsComplete.setOnClickListener {
            projectList[position].id?.let { completeTask(it) }
        }

        projectDetailCardViewHolder.cardViewProjectsBinding.opportunityDetail.setOnClickListener {

            val id = if (record.attributes?.type == "Site_Visit__c") record.customerNameC else record?.what?.id

            val bundle = Bundle()
            bundle.putString(Keys.OPP_ID, id)
            if (context is MainActivity) (context as MainActivity).navHostFragment.navController.navigate(
                R.id.action_taskFragment_to_opportunityDetailFragment,
                bundle
            )
        }
    }

    override fun getItemCount() = projectList.size

    abstract fun completeTask(id: String)

    class ProjectDetailCardViewHolder(var cardViewProjectsBinding: CardViewTaskBinding) :
        RecyclerView.ViewHolder(cardViewProjectsBinding.root)
}