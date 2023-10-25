package com.stetig.solitaire.fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.*
import com.google.android.material.textfield.TextInputLayout
import com.stetig.callsync.base.BaseFragment
import com.stetig.solitaire.R
import com.stetig.solitaire.activity.MainActivity
import com.stetig.solitaire.api.Keys
import com.stetig.solitaire.databinding.FeedbackFormBinding


class FeedBackFragment : BaseFragment() {

    lateinit var activity: MainActivity
    lateinit var binding: FeedbackFormBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.feedback_form, container, false)
        initView(binding.root)
        return binding.root
    }

    val statusMap = mutableMapOf<String, Array<String>>(
        "Visited At" to arrayOf(
            "Booking in 3 - 5 days",
            "Partial Swipe Done",
            "PDC Cheque Received",
            "Cheque Pick Up",
            "Ancillary Service Calling Activity"
        ),
        "Cold" to arrayOf(
            "Budget issue",
            "Client not responding",
            "Location issue",
            "Discussion With Family",
            "Decision Maker Absent",
            "Arranging Funds",
            "Expecting Negotiations",
            "Awaiting Loan Eligibility/Approval",
            "Comparing with Other Options",
            "Discussing with CA",
            "Conditional Sale of property",
            "Deciding Need More Time",
            "Looking for Other Options",
            "Looking for RTMI",
            "Asked to CB after 7 days",
            "Not Comfortable with Sales Team",
            "Not in a hurry to purchase",
            "OCR Issue",
            "Possession Timeline Issue",
            "Price Deviation Required",
            "Payment Plan Deviation Required",
            "Layout Issue",
            "Out of Town",
            "Negative Online Review",
            "General/Casual Enquiry",
            "Ancillary Service Calling Activity",
            "Vaastu issue",

            ),

        "Booked" to arrayOf(
            "Booked",
        ),
        "Warm" to arrayOf(
            "Unit Shortlisted - Awaiting Final Offer",
            "Unit Shortlisted- Seeking Approval Details",
            "Discussion With Family",
            "Revisit Proposed",
            "Unit Shortlisted - Awaiting Loan",
            "Revisit Proposed with Decision Maker",
            "Decision Maker Absent",
            "Arranging Funds",
            "Expecting Negotiations",
            "Payment Deviation Required",
            "Awaiting Loan Eligibility/Approval",
            "Comparing with Other Options",
            "Discussing with CA",
            "Follow up in 1 - 4 days",
            "Expecting Discount from Management",

            ),
        "Lost" to arrayOf(
            "Location issue",
            "Not Qualified",
            "Expecting Negotiations",
            "Looking for RTMI",
            "Negative Online Review",
            "Budget/OCR Issues",
            "Conditional Purchase of Property",
            "Capital Gain Issue",
            "Inventory Choice Unavailability",
            "Not Convinced with the Product",
            "Layout/Size Issues",
            "Lost to Competition",
            "Non Eligibility of Loan",
            "Unable to Connect with Client for long",
            "Deferred Real Estate Purchase",
            "Expecting Correction in Market",
            "Policy Related/Agreement",
            "PSF Issue",
            "Came along with friend/relative",
            "Looking for Under Construction",
            "Competition Client/Dummy",
            "Vaastu issue",
            "Invalid Inquiry",
            "Government officials",
            "Purchased resale Property"
        ),
    )



    override fun initView(rootView: View?) {
        val adapter = ArrayAdapter(
            activity,
            android.R.layout.simple_spinner_item,
            listOfOpportunities.map { it.oPPname })
        val autoCompleteTextView = dialogView.findViewById(com.stetig.solitaire.R.id.autoCompleteTextView) as AutoCompleteTextView
        binding.autovisitedat.threshold = 3
        binding.autovisitedat.setAdapter(adapter)
        binding.autovisitedat.onItemClickListener =
            binding.autovisitedat.onItemClickListener { parent, view_, position, _ -> val item = listOfOpportunities.get(position)
                createTaskRequest.opp_Id = item.oppId;
            }
        val textInputLayout = findViewById<TextInputLayout>(R.id.textInputLayout)
        val autoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.autovisitedat)
        val headings = arrayOf("Visited At", "Ethnicity", "Budget", "Zone")
        val data = arrayOf(
            arrayOf("Site", "Outbound", "Outbound - Outstation", "E-Visit"),
            arrayOf(
                "Bengali", "Christian", "Gujarati", "Marathi", "Marwari", "Muslim",
                "North Indian Hindu", "Parsi", "Punjabi", "Sindhi", "South Indian Hindu"
            ),
            arrayOf("80 Lacs & Below", "80 L - 90 L", "90 L – 01 Cr", "01 Cr – 01.10 Cr", "01.10 Cr – 01.40 Cr", "01.40 Cr +"),
            arrayOf("A", "B", "C", "D", "E", "Any")
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, headings)
        autoCompleteTextView.setAdapter(adapter)
        autoCompleteTextView.OnItemClickListener { parent, view, position, id ->
            val selectedData = data[position]
        }
    }

    override fun setContentView(activityMain: Int) {
        TODO("Not yet implemented")
    }

    private fun moveToFragmentDetials(type: String){
        val bundle = Bundle()
        bundle.putString(Keys.APP_TYPE, type)
        navigateTo(R.id.action_approvalFragment_to_approvalDetailFragment, bundle)
    }

    private fun moveToCampaignDetials(type: String){
        val bundle = Bundle()
        bundle.putString(Keys.APP_TYPE, type)
//        navigateTo(R.id.action_approvalFragment_to_campaignApprovalDetailFragment, bundle)
    }
}