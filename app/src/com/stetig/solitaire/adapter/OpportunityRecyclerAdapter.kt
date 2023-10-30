package com.stetig.solitaire.adapter

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
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
class OpportunityRecyclerAdapter(private var context: Context, private var projectList: List<Opportunity.Record>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val cardViewProjectsBinding = CardViewOpportuntiyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProjectDetailCardViewHolder(cardViewProjectsBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val projectDetailCardViewHolder = holder as ProjectDetailCardViewHolder
        val data = projectList[position]
        projectDetailCardViewHolder.cardViewProjectsBinding.name.text = Utils.checkValueOrGiveEmpty(data.name)
        projectDetailCardViewHolder.cardViewProjectsBinding.projectName.text = Utils.checkValueOrGiveEmpty(data.projectC)
        projectDetailCardViewHolder.cardViewProjectsBinding.date.text = Utils.getFormattedDateWithTimeSF(data.createdDate)
        projectDetailCardViewHolder.cardViewProjectsBinding.configurationOpp.text = Utils.checkValueOrGiveEmpty(data.configurationC)
        projectDetailCardViewHolder.cardViewProjectsBinding.statusOpp.text = Utils.checkValueOrGiveEmpty(data.statusC)
        projectDetailCardViewHolder.cardViewProjectsBinding.mobileNoOpp.text = Utils.checkValueOrGiveEmpty(data.accountMobileNumberC)
        projectDetailCardViewHolder.cardViewProjectsBinding.emailOpp.text = Utils.checkValueOrGiveEmpty(data.accountEmailIdC)

        projectDetailCardViewHolder.cardViewProjectsBinding.include.call.setOnClickListener { callNumber(data.accountMobileNumberC, context) }
        projectDetailCardViewHolder.cardViewProjectsBinding.include.whatsapp.setOnClickListener { sendWhatsAppToSpecificNumber(data.accountMobileNumberC, context) }
        projectDetailCardViewHolder.cardViewProjectsBinding.include.share.setOnClickListener {
            smsNumber(data.accountMobileNumberC, context)
        }
//        projectDetailCardViewHolder.cardViewProjectsBinding.include.timer.setOnClickListener {
//            val bundle = Bundle()
//            bundle.putString(Keys.OPP_ID, data.id)
//            (context as MainActivity).navHostFragment.navController.navigate(R.id.action_opportunityFragment_to_timeLineFragment, bundle)
//        }
        projectDetailCardViewHolder.cardViewProjectsBinding.linearLayout.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(Keys.OPP_ID, data.id)
            (context as MainActivity).navHostFragment.navController.navigate(R.id.action_opportunityFragment_to_opportunityDetailFragment, bundle)
        }
//        projectDetailCardViewHolder.cardViewProjectsBinding.include.share.setOnClickListener {
//            sendProjectDetails(data.projectC ?: "", data.accountMobileNumberC
//                    ?: "", data.accountEmailIdC
//                    ?: "", context, R.id.action_opportunityFragment_to_projectDetailFragment)
//        }

    }

    companion object {
        public fun callNumber(number: String?, context: Context) {

            if (number == null) {
                Utils.setToast(context, "Number Not Found")
                return
            }

//            val intent = Intent(Intent.ACTION_CALL)
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$number")
//            if (context.checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                context.startActivity(intent)
//            }
        }

        public fun sendWhatsAppToSpecificNumber(number: String?, context: Context) {
            if (number == null) {
                Utils.setToast(context, "Number Not Found")
                return
            }

            if (Utils.isWhatsAppInstalled(context, "com.whatsapp")) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = (Uri.parse("http://api.whatsapp.com/send?phone=+91$number &text= "))
                context.startActivity(intent)
            } else {
                Utils.setToast(context, "Whats app is not installed")
            }
        }

        public fun smsNumber(number: String?, context: Context) {
            if (number == null) {
                Utils.setToast(context, "Number Not Found")
                return
            }
//            val intent = Intent(Intent.ACTION_CALL)
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("sms:$number")
            context.startActivity(intent)
//            if (context.checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
//
//            }
        }
    }

    override fun getItemCount() = projectList.size

    class ProjectDetailCardViewHolder(var cardViewProjectsBinding: CardViewOpportuntiyBinding) : RecyclerView.ViewHolder(cardViewProjectsBinding.root)
}