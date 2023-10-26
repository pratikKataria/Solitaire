package com.stetig.solitaire.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.*
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputLayout
import com.stetig.callsync.base.BaseFragment
import com.stetig.solitaire.R
import com.stetig.solitaire.activity.MainActivity
import com.stetig.solitaire.api.Keys
import com.stetig.solitaire.databinding.FeedbackFormBinding
import org.acra.ACRA.log


class FeedBackFragment : BaseFragment() {

    lateinit var activity: MainActivity
    lateinit var binding: FeedbackFormBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = inflate(inflater, R.layout.feedback_form, container, false)
        initView(binding.root)
        return binding.root
    }


    val statusMap = mutableMapOf<String, Array<String>>(
        "Hot" to arrayOf(
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
        val bundle = Bundle()
        val typeofenq = bundle.getString(Keys.TYPE_ENQUIRY)
        log.e("TYPE OF ENQUIRY====", typeofenq.toString())
        if (typeofenq == "Leasing") {
            binding.autosubcategory.visibility = View.VISIBLE
            binding.autowholesalerretailer.visibility = View.VISIBLE
            binding.autoshopownship.visibility = View.VISIBLE
            binding.currentShopSize.visibility = View.VISIBLE
            binding.rental.visibility = View.VISIBLE
            binding.dailybusiness.visibility = View.VISIBLE
            binding.employeesize.visibility = View.VISIBLE
            binding.totaloutlets.visibility = View.VISIBLE
            binding.outletslocation.visibility = View.VISIBLE
            binding.autoleasing.visibility = View.VISIBLE

            binding.autosubcategory1.visibility = View.VISIBLE
            binding.autowholesalerretailer1.visibility = View.VISIBLE
            binding.autoshopownship1.visibility = View.VISIBLE
            binding.currentShopSize1.visibility = View.VISIBLE
            binding.rental1.visibility = View.VISIBLE
            binding.dailybusiness1.visibility = View.VISIBLE
            binding.employeesize1.visibility = View.VISIBLE
            binding.totaloutlets1.visibility = View.VISIBLE
            binding.outletslocation1.visibility = View.VISIBLE
            binding.autoleasing1.visibility = View.VISIBLE

        } else {
            binding.autosubcategory.visibility = View.GONE
            binding.autowholesalerretailer.visibility = View.GONE
            binding.autoshopownship.visibility = View.GONE
            binding.currentShopSize.visibility = View.GONE
            binding.rental.visibility = View.GONE
            binding.dailybusiness.visibility = View.GONE
            binding.employeesize.visibility = View.GONE
            binding.totaloutlets.visibility = View.GONE
            binding.outletslocation.visibility = View.GONE
            binding.autoleasing.visibility = View.GONE

            binding.autosubcategory1.visibility = View.GONE
            binding.autowholesalerretailer1.visibility = View.GONE
            binding.autoshopownship1.visibility = View.GONE
            binding.currentShopSize1.visibility = View.GONE
            binding.rental1.visibility = View.GONE
            binding.dailybusiness1.visibility = View.GONE
            binding.employeesize1.visibility = View.GONE
            binding.totaloutlets1.visibility = View.GONE
            binding.outletslocation1.visibility = View.GONE
            binding.autoleasing1.visibility = View.GONE

        }

        /// For Visit At
        val visitedAddAutoComplete = binding.autovisitedat
        val visitedAutoCompleteAdapter = ArrayAdapter(activity, android.R.layout.simple_spinner_item, arrayOf("Site", "Outbound", "Outbound - Outstation", "E-Visit"))
        visitedAddAutoComplete.threshold = 3
        visitedAddAutoComplete.setAdapter(visitedAutoCompleteAdapter)
        visitedAddAutoComplete.onItemClickListener = OnItemClickListener { parent, view_, position, _ ->
        }

        /// For Ethnicity
        val enthicityAutoCompleteTextView = binding.autoethnicity
        val listOfEthnicity = arrayOf("Bengali", "Christian", "Gujarati", "Marathi", "Marwari", "Muslim", "North Indian Hindu", "Parsi", "Punjabi", "Sindhi", "South Indian Hindu")
        val ethinicityAdapter = ArrayAdapter(activity, android.R.layout.simple_spinner_item, listOfEthnicity)
        enthicityAutoCompleteTextView.threshold = 3
        enthicityAutoCompleteTextView.setAdapter(ethinicityAdapter)
        enthicityAutoCompleteTextView.onItemClickListener = OnItemClickListener { parent, view_, position, _ ->
        }

        /// For Budget
        val budgetAutoCompleteTextView = binding.autobudget
        val listOfBudget = arrayOf("80 Lacs & Below", "80 L - 90 L", "90 L – 01 Cr", "01 Cr – 01.10 Cr", "01.10 Cr – 01.40 Cr", "01.40 Cr +")
        val budgetAdapter = ArrayAdapter(activity, android.R.layout.simple_spinner_item, listOfBudget)
        budgetAutoCompleteTextView.threshold = 3
        budgetAutoCompleteTextView.setAdapter(budgetAdapter)
        budgetAutoCompleteTextView.onItemClickListener = OnItemClickListener { parent, view_, position, _ ->
        }

        /// For Zone
        val zoneAutoCompleteTextView = binding.autozone
        val listOfZone = arrayOf("A", "B", "C", "D", "E", "Any")
        val zoneAdapter = ArrayAdapter(activity, android.R.layout.simple_spinner_item, listOfZone)
        zoneAutoCompleteTextView.threshold = 3
        zoneAutoCompleteTextView.setAdapter(zoneAdapter)
        zoneAutoCompleteTextView.onItemClickListener = OnItemClickListener { parent, view_, position, _ ->
        }

        /// For Wholeseller
        val wholesellerAddAutoComplete = binding.autowholesalerretailer
        val wholesellerAutoCompleteAdapter = ArrayAdapter(activity, android.R.layout.simple_spinner_item, arrayOf("Wholeseller","Retailer" ,"Both"))
        wholesellerAddAutoComplete.threshold = 3
        wholesellerAddAutoComplete.setAdapter(wholesellerAutoCompleteAdapter)
        wholesellerAddAutoComplete.onItemClickListener = OnItemClickListener { parent, view_, position, _ ->
        }

        /// For Sub category
        val subcategoryAddAutoComplete = binding.autosubcategory
        val subcategoryAutoCompleteAdapter = ArrayAdapter(activity, android.R.layout.simple_spinner_item, arrayOf(
            "Grains and Pulses",
            "Spices and Dry Fruits",
            "Packaged foods and ready to eat",
            "Grocery Item",
            "Dairy and Frozen Items",
            "Construction Machinery, Tools, and Equipment",
            "Wood and laminates",
            "Sanitary Ware & Fittings",
            "Hardware, Fittings, and Accessories",
            "Paints and Wall Decor",
            "Lamps, Fixtures, and Electrical Fittings",
            "Furnishings & Furniture Home Décor",
            "Men’s Wear",
            "Women’s Wear",
            "Kids Wear",
            "Textiles Garments",
            "Bags",
            "Belts",
            "Footwear",
            "Watches",
            "Cosmetics",
            "Silver Jewellery and Ornaments",
            "Diamond and Diamond Jewellery",
            "Gold and Gold Jewellery",
            "Metal and artificial fashion jewellery",
            "Precious Stones and Gemstone Jewellery",
            "Mobile, Mobile Accessories",
            "Medical Supplies and Medicines",
            "Crockery and cutlery, utensils",
            "Consumer Electronics & Electrical",
            "Gifts, Crafts, and Stationery Products",
            "Toys and Baby Products",
            "Computers, computer spare parts, and the Internet"))
        subcategoryAddAutoComplete.threshold = 3
        subcategoryAddAutoComplete.setAdapter(subcategoryAutoCompleteAdapter)
        subcategoryAddAutoComplete.onItemClickListener = OnItemClickListener { parent, view_, position, _ ->
        }

        /// Shop Ownership
        val ownshipAddAutoComplete = binding.autoshopownship
        val ownershipAutoCompleteAdapter = ArrayAdapter(activity, android.R.layout.simple_spinner_item, arrayOf("Owner","Rental"))
        ownshipAddAutoComplete.threshold = 3
        ownshipAddAutoComplete.setAdapter(ownershipAutoCompleteAdapter)
        ownshipAddAutoComplete.onItemClickListener = OnItemClickListener { parent, view_, position, _ ->
        }

        /// Leasing(Interested)
        val leasingAddAutoComplete = binding.autoleasing
        val leasingAutoCompleteAdapter = ArrayAdapter(activity, android.R.layout.simple_spinner_item, arrayOf("Yes","No", "Tentative"))
        leasingAddAutoComplete.threshold = 3
        leasingAddAutoComplete.setAdapter(leasingAutoCompleteAdapter)
        leasingAddAutoComplete.onItemClickListener = OnItemClickListener { parent, view_, position, _ ->
        }



        binding.feedbackchipGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId != View.NO_ID) {

                // A chip has been selected
                val selectedChip = group.findViewById<Chip>(checkedId)

                // Change background color of selected chip
//                selectedChip.setChipBackgroundColorResource(com.stetig.solitaire.R.color.selected_chip_background_color)

                // Handle the selected chip
                val selectedText = selectedChip.text.toString()
                when (selectedText) {
                    "Hot" -> {
                        val listOfDisposition = statusMap["Hot"]
                        val adapter = ArrayAdapter(
                            activity,
                            android.R.layout.simple_spinner_item,
                            listOfDisposition ?: arrayOf()
                        )
                        val autoCompleteTextView = binding.autodesiredpossession
                        autoCompleteTextView.threshold = 3
                        autoCompleteTextView.setAdapter(adapter)
                        autoCompleteTextView.onItemClickListener =
                            AdapterView.OnItemClickListener { parent, view_, position, _ ->
//                                val item = listOfOpportunities.get(position)
//                                createTaskRequest.opp_Id = item.oppId;
                            }
                    }
                    "Warm" -> {
                        val listOfDisposition = statusMap["Warm"]

                        val adapter = ArrayAdapter(
                            activity,
                            android.R.layout.simple_spinner_item,
                            listOfDisposition ?: arrayOf()
                        )

                        val autoCompleteTextView = binding.autodesiredpossession
                        autoCompleteTextView.threshold = 3
                        autoCompleteTextView.setAdapter(adapter)
                        autoCompleteTextView.onItemClickListener =
                            AdapterView.OnItemClickListener { parent, view_, position, _ ->
//                                val item = listOfOpportunities.get(position)
//                                createTaskRequest.opp_Id = item.oppId;
                            }
                    }
                    "Cold" -> {
                        val listOfDisposition = statusMap["Cold"]

                        val adapter = ArrayAdapter(
                            activity,
                            android.R.layout.simple_spinner_item,
                            listOfDisposition ?: arrayOf()
                        )

                        val autoCompleteTextView = binding.autodesiredpossession
                        autoCompleteTextView.threshold = 3
                        autoCompleteTextView.setAdapter(adapter)
                        autoCompleteTextView.onItemClickListener =
                            AdapterView.OnItemClickListener { parent, view_, position, _ ->
//                                val item = listOfOpportunities.get(position)
//                                createTaskRequest.opp_Id = item.oppId;
                            }
                    }
                    "Booked" -> {
                        val listOfDisposition = statusMap["Booked"]

                        val adapter = ArrayAdapter(
                            activity,
                            android.R.layout.simple_spinner_item,
                            listOfDisposition ?: arrayOf()
                        )

                        val autoCompleteTextView = binding.autodesiredpossession
                        autoCompleteTextView.threshold = 3
                        autoCompleteTextView.setAdapter(adapter)
                        autoCompleteTextView.onItemClickListener =
                            AdapterView.OnItemClickListener { parent, view_, position, _ ->
//                                val item = listOfOpportunities.get(position)
//                                createTaskRequest.opp_Id = item.oppId;
                            }
                    }
                    "Lost" -> {
                        val listOfDisposition = statusMap["Lost"]

                        val adapter =  ArrayAdapter(
                            activity,
                                android.R.layout.simple_spinner_item,
                            listOfDisposition ?: arrayOf()
                            )

                        val autoCompleteTextView = binding.autodesiredpossession
                        autoCompleteTextView.threshold = 3
                        autoCompleteTextView.setAdapter(adapter)
                        autoCompleteTextView.onItemClickListener =
                            AdapterView.OnItemClickListener { parent, view_, position, _ ->
//                                val item = listOfOpportunities.get(position)
//                                createTaskRequest.opp_Id = item.oppId;
                            }
                    }
                }
            } else {
                // No chip selected
            }
        }




//        val textInputLayout = findViewById<TextInputLayout>(R.id.textInputLayout)
//        val autoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.autovisitedat)
//
//        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, headings)
//        autoCompleteTextView.setAdapter(adapter)
//        autoCompleteTextView.OnItemClickListener { parent, view, position, id ->
//            val selectedData = data[position]
//        }
    }


    private fun moveToFragmentDetials(type: String) {
        val bundle = Bundle()
        bundle.putString(Keys.APP_TYPE, type)
        navigateTo(R.id.action_approvalFragment_to_approvalDetailFragment, bundle)
    }

    private fun moveToCampaignDetials(type: String) {
        val bundle = Bundle()
        bundle.putString(Keys.APP_TYPE, type)
//        navigateTo(R.id.action_approvalFragment_to_campaignApprovalDetailFragment, bundle)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = if (context is MainActivity) context else getActivity() as MainActivity
    }
}