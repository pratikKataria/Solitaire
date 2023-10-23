package com.stetig.solitaire.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stetig.solitaire.data.ManualTaskListResponse
import com.stetig.solitaire.data.SolitaireCreateTask
import com.stetig.solitaire.databinding.CardViewCreatetaskBinding
import com.stetig.solitaire.utils.Utils

/**
 * Created by Pratik Kataria on 27-11-2020.
 */
class TaskListRecyclerAdapyer(private var context: Context, private var projectList: List<ManualTaskListResponse.Record?>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val cardViewProjectsBinding = CardViewCreatetaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProjectDetailCardViewHolder(cardViewProjectsBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val projectDetailCardViewHolder = holder as ProjectDetailCardViewHolder
        val data = projectList[position]
        projectDetailCardViewHolder.cardViewProjectsBinding.markAsComplete.visibility = View.GONE
        projectDetailCardViewHolder.cardViewProjectsBinding.name.text = Utils.checkValueOrGiveEmpty(data?.what?.name)
        projectDetailCardViewHolder.cardViewProjectsBinding.subject.text = Utils.checkValueOrGiveEmpty(data?.subject)
        projectDetailCardViewHolder.cardViewProjectsBinding.dueDate.text = Utils.checkValueOrGiveEmpty(data?.activityDate)
        projectDetailCardViewHolder.cardViewProjectsBinding.taskType.text = Utils.checkValueOrGiveEmpty(data?.taskTypeC)

    }

    override fun getItemCount() = projectList.size

    class ProjectDetailCardViewHolder(var cardViewProjectsBinding: CardViewCreatetaskBinding) : RecyclerView.ViewHolder(cardViewProjectsBinding.root)
}