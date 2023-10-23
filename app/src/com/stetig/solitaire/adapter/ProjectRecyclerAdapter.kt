package com.stetig.solitaire.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stetig.solitaire.R
import com.stetig.solitaire.activity.MainActivity
import com.stetig.solitaire.api.Keys
import com.stetig.solitaire.data.Projects
import com.stetig.solitaire.databinding.CardViewProjectsBinding
import com.stetig.solitaire.utils.Utils

/**
 * Created by Pratik Kataria on 27-11-2020.
 */
class ProjectRecyclerAdapter(private var context: Context, private var projectList: List<Projects.Record>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val cardViewProjectsBinding = CardViewProjectsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProjectDetailCardViewHolder(cardViewProjectsBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val projectDetailCardViewHolder = holder as ProjectDetailCardViewHolder
        projectDetailCardViewHolder.cardViewProjectsBinding.listItemProjectsName.text = Utils.checkValueOrGiveEmpty(projectList[position].name)
//        projectDetailCardViewHolder.cardViewProjectsBinding.listItemProjectsCity.text = Utils.checkValueOrGiveEmpty(projectList[position].cityC)
        projectDetailCardViewHolder.cardViewProjectsBinding.cardViewProject.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(Keys.CITIES, projectList[position].cityC)
            bundle.putString(Keys.PROJECT, projectList[position].name)
            bundle.putString(Keys.PROJECT_ID, projectList[position].id)
            if (context is MainActivity) (context as MainActivity).navHostFragment.navController.navigate(R.id.action_projectFragment_to_projectDetailFragment, bundle)
        }
    }

    override fun getItemCount() = projectList.size

    class ProjectDetailCardViewHolder(var cardViewProjectsBinding: CardViewProjectsBinding) : RecyclerView.ViewHolder(cardViewProjectsBinding.root)
}