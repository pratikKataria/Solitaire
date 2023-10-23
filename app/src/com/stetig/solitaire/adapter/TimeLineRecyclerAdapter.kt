package com.stetig.solitaire.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.stetig.solitaire.R
import com.stetig.solitaire.data.Timeline
import com.stetig.solitaire.databinding.CardViewTimeLineBinding
import com.stetig.solitaire.utils.Utils
import java.util.*

/**
 * Created by Pratik Kataria on 27-11-2020.
 */
class TimeLineRecyclerAdapter(private var context: Context, private var projectList: List<Timeline.Record>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var res = hashMapOf<String, Int>()

    init {
        res["call"] = R.drawable.icon_call_dark
        res["site"] = R.drawable.ic_car_black
        res["meeting"] = R.drawable.ic_person_dark
        res["proposal"] = R.drawable.ic_proposal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val cardViewProjectsBinding = CardViewTimeLineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProjectDetailCardViewHolder(cardViewProjectsBinding)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val projectDetailCardViewHolder = holder as ProjectDetailCardViewHolder
        val data = projectList[position]
        projectDetailCardViewHolder.cardViewProjectsBinding.subject.text = Utils.checkValueOrGiveEmpty(data.subject)
        projectDetailCardViewHolder.cardViewProjectsBinding.time.text = Utils.getFormattedDateSF_Timeline(data.createdDate)

        try {
            var drawable: Drawable? = null
            if (data.subject.toLowerCase(Locale.ROOT).contains("call")) drawable = context.getDrawable(res["call"]!!)
            else if (data.subject.toLowerCase(Locale.ROOT).contains("site") || data.subject.toLowerCase(Locale.ROOT).contains("visit")) drawable = context.getDrawable(res["site"]!!)
            else if (data.subject.toLowerCase(Locale.ROOT).contains("meeting") || data.subject.toLowerCase(Locale.ROOT).contains("follow")) drawable = context.getDrawable(res["meeting"]!!)
            else if (data.subject.toLowerCase(Locale.ROOT).contains("proposal") || data.subject.toLowerCase(Locale.ROOT).contains("sent")) drawable = context.getDrawable(res["proposal"]!!)

            projectDetailCardViewHolder.cardViewProjectsBinding.iconImageView.setImageDrawable(drawable)
            projectDetailCardViewHolder.cardViewProjectsBinding.iconImageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
        } catch (e: Exception) {
        }
    }

    override fun getItemCount() = projectList.size

    class ProjectDetailCardViewHolder(var cardViewProjectsBinding: CardViewTimeLineBinding) : RecyclerView.ViewHolder(cardViewProjectsBinding.root)
}