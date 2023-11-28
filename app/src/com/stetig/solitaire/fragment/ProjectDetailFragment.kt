package com.stetig.solitaire.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import com.stetig.callsync.base.BaseFragment
import com.stetig.solitaire.R
import com.stetig.solitaire.activity.MainActivity
import com.stetig.solitaire.api.CommonClassForQuery
import com.stetig.solitaire.api.Keys
import com.stetig.solitaire.api.Query
import com.stetig.solitaire.data.ProjectDetail
import com.stetig.solitaire.databinding.FragmentProjectDetailBinding
import com.stetig.solitaire.utils.Utils
import com.google.android.material.transition.MaterialSharedAxis
import com.stetig.solitaire.data.ProjectLink
import org.acra.ACRA.log
import java.net.URLEncoder


class ProjectDetailFragment : BaseFragment() {

    lateinit var binding: FragmentProjectDetailBinding
    lateinit var activity: MainActivity
    private lateinit var city: String
    private lateinit var projectName: String
    private lateinit var projectId: String
    private var number: String? = null
    private var email: String? = null
    var checkBoxLink = mutableListOf<String>()
    var checkBoxList = mutableListOf<CheckBox>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        city = arguments?.getString(Keys.CITIES, "") ?: ""
        projectName = arguments?.getString(Keys.PROJECT, "") ?: ""
        projectId = arguments?.getString(Keys.PROJECT_ID, "") ?: ""
        number = arguments?.getString(Keys.NUMBER, null)
        email = arguments?.getString(Keys.EMAIL, null)
        Log.e("TAG", "onCreate: $city")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_project_detail, container, false)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        initView(rootView = binding.root)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun initView(rootView: View?) {
        binding.title.text = "$projectName"
        binding.whatsappBtn.setOnClickListener { shareViaWhatsApp() }
        binding.emailBtn.setOnClickListener { shareViaEmail() }
    }

    override fun onResume() {
        super.onResume()
        val commonClassForQuery = CommonClassForQuery.getInstance(activity, activity.getRestClient())!!
        commonClassForQuery.getProjectDetails(Query.PROJECT_LINK_FETCH + Utils.buildQueryParameter(projectId), onDataReceiveListener)

        checkBoxList.clear()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = if (context is MainActivity) context else getActivity() as MainActivity
    }

    fun buildCheckBox(name: String?, projectLink: String): CheckBox {
        val checkBox = CheckBox(activity)
        val checkBoxParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        checkBoxParams.setMargins(0, 24, 0, 24)
        checkBox.layoutParams = checkBoxParams
        checkBox.tag = projectLink
        checkBox.textSize = 18F
        checkBox.typeface = ResourcesCompat.getFont(activity, R.font.myriad_pro_regular)
        checkBox.setPadding(40, 0, 0, 0)
        checkBox.id = checkBoxLink.size - 1
        checkBox.top = 12
        checkBox.text = name
        return checkBox
    }

    private var onDataReceiveListener = object : CommonClassForQuery.OnDataReceiveListener {
        override fun onDataReceive(data: Any) {
            binding.checkboxGroup.removeAllViews()
            checkBoxLink.clear()
            if (data is ProjectDetail) data.records?.forEach {
                val commonClassForQuery = CommonClassForQuery.getInstance(activity, activity.getRestClient())!!
                commonClassForQuery.getPublicURL(Query.PROJECT_LINK + Utils.buildQueryParameter(it?.contentDocumentId), onLinkReceiveListener)
            }
        }

        override fun onError(obj: String) {
        }

    }

    private var onLinkReceiveListener = object : CommonClassForQuery.OnDataReceiveListener {
        override fun onDataReceive(data: Any) {
            binding.checkboxGroup.removeAllViews()
            checkBoxLink.clear()
            if (data is ProjectLink) data.records?.forEach {
                it.latestPublishedVersion.attributes.url
                log.e("PublicURL========", it.latestPublishedVersion.attributes.url)
                val checkBox = buildCheckBox(it?.latestPublishedVersion?.type, it?.latestPublishedVersion?.url__c ?: "")
                checkBoxList.add(checkBox)
                binding.checkboxGroup.addView(checkBox)
            }
        }

        override fun onError(obj: String) {}

    }

    private fun shareViaWhatsApp() {

        if (getWhatsAppLinks().isEmpty()) {
            Toast.makeText(activity, "Please select at least one Brochure", Toast.LENGTH_LONG).show()
            return
        }

        if (Utils.isWhatsAppInstalled(getActivity(), "com.whatsapp")) {
            val whatsAppQuery =
                if (number == null) "http://api.whatsapp.com/send?text=${getWhatsAppLinks()}" else "http://api.whatsapp.com/send?phone=+91$number &text= ${getLinks()}"
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            if (checkIfEmpty()) return
            intent.data = Uri.parse(whatsAppQuery)
            startActivity(intent)
        }
    }

    private fun shareViaEmail() {


        if (getMailLinks().isEmpty()) {
            Toast.makeText(activity, "Please select at least one Brochure", Toast.LENGTH_LONG).show()
            return
        }


        val outlookPackageName = "com.microsoft.office.outlook"
        val playStoreUri = Uri.parse("market://details?id=$outlookPackageName")
        val playStoreIntent = Intent(Intent.ACTION_VIEW, playStoreUri)

//        // Verify if there is an app to handle the intent
//        if (playStoreIntent.resolveActivity(activity.packageManager) != null) {
//            // Outlook app is installed or can be installed from Play Store, open Play Store
//            startActivity(playStoreIntent)
//            return
//        }
        val emailBody = buildEmailBody(getMailLinks())

        try {
            val emailAdd = if (email == null) "" else "$email"
            val outlookUri = Uri.parse("ms-outlook://emails/new?to=$emailAdd&body=$emailBody")
            val outlookIntent = Intent(Intent.ACTION_VIEW, outlookUri)
            startActivity(outlookIntent)
        } catch (xe: Exception) {
            Toast.makeText(activity, "Please Install Microsoft Outlook", Toast.LENGTH_LONG).show()
        }

// Verify if there is an app to handle the intent
//        if (outlookIntent.resolveActivity(packageManager) != null) {
//            // Outlook app is installed, open it
//            startActivity(outlookIntent)
//        } else {
        // Outlook app is not installed, open Outlook website in a browser
//        val outlookWebUri = Uri.parse("https://outlook.live.com/")
//        val outlookWebIntent = Intent(Intent.ACTION_VIEW, outlookWebUri)
//        startActivity(outlookWebIntent)
////        }
//
//


//        intent.action = Intent.ACTION_SENDTO
//        intent.data = Uri.parse(emailAdd)
//        if (checkIfEmpty()) return
//        intent.putExtra(Intent.EXTRA_TEXT, getLinks())
//        startActivity(Intent.createChooser(intent, "Send email to: "))
    }

    private fun getLinks(): String {
        val s = StringBuilder()
        for (checkBox in checkBoxList) {
            if (checkBox.isChecked && checkBox.tag.toString().isNotEmpty()) {
                s.append(" ").append(checkBox.tag.toString()).append("\n")
            }
        }
        return s.toString()
    }

    private fun getMailLinks(): List<String> {
        val listOfLinks = arrayListOf<String>()
        for (checkBox in checkBoxList) {
            if (checkBox.isChecked && checkBox.tag.toString().isNotEmpty()) {
                listOfLinks.add(checkBox.tag.toString())
            }
        }
        return listOfLinks
    }

    private fun getWhatsAppLinks(): String {
        val s = StringBuilder()
        for (checkBox in checkBoxList) {
            if (checkBox.isChecked && checkBox.tag.toString().isNotEmpty()) {
                val link = checkBox.tag.toString().replace("&", "%26")
                s.append(" ").append(link).append("\n")
            }
        }
        return s.toString()
    }

    private fun checkIfEmpty(): Boolean {
        if (checkBoxList.isEmpty()) return true
        var empty = true
        for (checkBox in checkBoxList) if (checkBox.isChecked) empty = false
        if (empty) {
            Utils.setToast(getActivity(), "At lease select one")
            return true
        }
        return empty
    }

    private fun buildEmailBody(links: List<String>): String {
        val emailTemplate = StringBuilder()
        emailTemplate.append("Hi,<br><br>")
        emailTemplate.append("To download the brochure, simply click on the links below:<br><br>")

        // Build HTML for each link
        for ((index, link) in links.withIndex()) {
            val encodedLink = URLEncoder.encode(link, "UTF-8")

            emailTemplate.append("<br>${index+1}. Click the link to Download : <a href=\"$encodedLink\">Boucher Link</a>")
            emailTemplate.append("<br>")
        }

        return emailTemplate.toString()
    }

}