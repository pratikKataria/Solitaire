package com.stetig.solitaire.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stetig.solitaire.R
import com.stetig.solitaire.activity.MainActivity
import com.stetig.solitaire.api.Keys
import com.stetig.solitaire.data.Search
import com.stetig.solitaire.databinding.CardViewSearchBinding
import com.stetig.solitaire.utils.Utils

/**
 * Created by Pratik Kataria on 27-11-2020.
 */
class SearchRecyclerAdapter(private var context: Context, private var projectList: List<Search.Record>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val cardViewProjectsBinding = CardViewSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProjectDetailCardViewHolder(cardViewProjectsBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val projectDetailCardViewHolder = holder as ProjectDetailCardViewHolder
        projectDetailCardViewHolder.cardViewProjectsBinding.name.text = Utils.checkValueOrGiveEmpty(projectList[position].name)
        projectDetailCardViewHolder.cardViewProjectsBinding.project.text = Utils.checkValueOrGiveEmpty(projectList[position].projectR.name)
        projectDetailCardViewHolder.cardViewProjectsBinding.cardView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(Keys.OPP_ID, projectList[position].id)
            if (context is MainActivity) (context as MainActivity).navHostFragment.navController.navigate(R.id.action_searchFragment_to_opportunityDetailFragment, bundle)
        }
    }

    override fun getItemCount() = projectList.size

    class ProjectDetailCardViewHolder(var cardViewProjectsBinding: CardViewSearchBinding) : RecyclerView.ViewHolder(cardViewProjectsBinding.root)
}