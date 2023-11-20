package com.stetig.solitaire.fragment

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.DatePicker
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.*
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.annotations.SerializedName
import com.stetig.callsync.base.BaseFragment
import com.stetig.solitaire.R
import com.stetig.solitaire.activity.MainActivity
import com.stetig.solitaire.api.CommonClassForApi
import com.stetig.solitaire.api.Keys
import com.stetig.solitaire.api.Query
import com.salesforce.androidsdk.app.SalesforceSDKManager
import com.stetig.solitaire.data.*
import com.stetig.solitaire.databinding.FeedbackFormBinding
import com.stetig.solitaire.utils.Utils
import io.reactivex.observers.DisposableObserver
import org.acra.ACRA.log
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class FeedBackFragment : BaseFragment() {

    lateinit var activity: MainActivity
    lateinit var binding: FeedbackFormBinding
    private var commonClassForApi: CommonClassForApi? = null
    lateinit var typeofenq: String
    lateinit var visitid: String
    lateinit var rating: String
    var feedback = FeedBack()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = inflate(inflater, R.layout.feedback_form, container, false)
        val typeofenq = arguments?.getString(Keys.TYPE_ENQUIRY)
        val visitid = arguments?.getString(Keys.SITE_VISIT_ID)
        feedback.id = visitid.toString()
        feedback.typeofenquiry = typeofenq.toString()
        log.e("TYPE OF ENQUIRY====", typeofenq.toString())
        initView(binding.root)

        feedback.smFeedback = binding.smFeedback.text.toString()
        feedback.placeofwork = binding.placeOfWork.text.toString()

        log.e("error dekho", typeofenq.toString())
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
        commonClassForApi = CommonClassForApi.getInstance(activity)

        binding.anniversaryDate.setOnClickListener {
            showDatePickerDialogAnniversaryDate(binding.anniversaryDate)
        }

        binding.nextActionDate.setOnClickListener {
            showDatePickerDialogNextActionDate(binding.nextActionDate)
        }

        /// For Visit At
        val visitedAddAutoComplete = binding.autovisitedat
        val listofvisitedat = arrayOf("Site", "Outbound", "Outbound - Outstation", "E-Visit")
        val visitedAutoCompleteAdapter = ArrayAdapter(activity, R.layout._layout_spinner_item, listofvisitedat)
        visitedAddAutoComplete.threshold = 3
        visitedAddAutoComplete.setAdapter(visitedAutoCompleteAdapter)
        visitedAddAutoComplete.onItemClickListener = OnItemClickListener { parent, view_, position, _ ->
            feedback.visitiedAt = listofvisitedat[position]
        }

        /// For Ethnicity
        val enthicityAutoCompleteTextView = binding.autoethnicity
        val listOfEthnicity = arrayOf("Bengali", "Christian", "Gujarati", "Marathi", "Marwari", "Muslim", "North Indian Hindu", "Parsi", "Punjabi", "Sindhi", "South Indian Hindu")
        val ethinicityAdapter = ArrayAdapter(activity, R.layout._layout_spinner_item, listOfEthnicity)
        enthicityAutoCompleteTextView.threshold = 3
        enthicityAutoCompleteTextView.setAdapter(ethinicityAdapter)
        enthicityAutoCompleteTextView.onItemClickListener = OnItemClickListener { parent, view_, position, _ ->
            feedback.ethnicity = listOfEthnicity[position]
        }

        /// For Budget
        val budgetAutoCompleteTextView = binding.autobudget
        val listOfBudget = arrayOf("80 Lacs & Below", "80 L - 90 L", "90 L – 01 Cr", "01 Cr – 01.10 Cr", "01.10 Cr – 01.40 Cr", "01.40 Cr +")
        val budgetAdapter = ArrayAdapter(activity, R.layout._layout_spinner_item, listOfBudget)
        budgetAutoCompleteTextView.threshold = 3
        budgetAutoCompleteTextView.setAdapter(budgetAdapter)
        budgetAutoCompleteTextView.onItemClickListener = OnItemClickListener { parent, view_, position, _ ->
            feedback.budget = listOfBudget[position]
        }

        /// For Zone
        val zoneAutoCompleteTextView = binding.autozone
        val listOfZone = arrayOf("A", "B", "C", "D", "E", "Any")
        val zoneAdapter = ArrayAdapter(activity, R.layout._layout_spinner_item, listOfZone)
        zoneAutoCompleteTextView.threshold = 3
        zoneAutoCompleteTextView.setAdapter(zoneAdapter)
        zoneAutoCompleteTextView.onItemClickListener = OnItemClickListener { parent, view_, position, _ ->
            feedback.zone = listOfZone[position]
        }

        /// For Wholeseller
        val wholesellerAddAutoComplete = binding.autowholesalerretailer
        val listofwholeseller = arrayOf("Wholeseller", "Retailer", "Both")
        val wholesellerAutoCompleteAdapter = ArrayAdapter(activity, R.layout._layout_spinner_item, listofwholeseller)
        wholesellerAddAutoComplete.threshold = 3
        wholesellerAddAutoComplete.setAdapter(wholesellerAutoCompleteAdapter)
        wholesellerAddAutoComplete.onItemClickListener = OnItemClickListener { parent, view_, position, _ ->
            feedback.wholesellerretailer = listofwholeseller[position]
        }

        /// For Sub category
        val subcategoryAddAutoComplete = binding.autosubcategory
        val listofsubcategory = arrayOf(
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
            "Computers, computer spare parts, and the Internet"
        )
        val subcategoryAutoCompleteAdapter = ArrayAdapter(activity, R.layout._layout_spinner_item, listofsubcategory)
        subcategoryAddAutoComplete.threshold = 3
        subcategoryAddAutoComplete.setAdapter(subcategoryAutoCompleteAdapter)
        subcategoryAddAutoComplete.onItemClickListener = OnItemClickListener { parent, view_, position, _ ->
            feedback.subCategory = listofsubcategory[position]
        }

        /// Shop Ownership
        val ownshipAddAutoComplete = binding.autoshopownship
        val listofownership = arrayOf("Owned", "Rental")
        val ownershipAutoCompleteAdapter = ArrayAdapter(activity, R.layout._layout_spinner_item, listofownership)
        ownshipAddAutoComplete.threshold = 3
        ownshipAddAutoComplete.setAdapter(ownershipAutoCompleteAdapter)
        ownshipAddAutoComplete.onItemClickListener = OnItemClickListener { parent, view_, position, _ ->
            feedback.shopOwnership = listofownership[position]
        }

        /// Leasing(Interested)
        val leasingAddAutoComplete = binding.autoleasing
        val listofleasing = arrayOf("Yes", "No", "Tentative")
        val leasingAutoCompleteAdapter = ArrayAdapter(activity, R.layout._layout_spinner_item, listofleasing)
        leasingAddAutoComplete.threshold = 3
        leasingAddAutoComplete.setAdapter(leasingAutoCompleteAdapter)
        leasingAddAutoComplete.onItemClickListener = OnItemClickListener { parent, view_, position, _ ->
            feedback.leasign = listofleasing[position]
        }

        /// Talked about competition
        val talkedaboutAddAutoComplete = binding.tac
        val listofTAC = arrayOf("Yes", "No")
        val talkedaboutAutoCompleteAdapter = ArrayAdapter(activity, R.layout._layout_spinner_item, listofTAC)
        talkedaboutAddAutoComplete.threshold = 3
        talkedaboutAddAutoComplete.setAdapter(talkedaboutAutoCompleteAdapter)
        talkedaboutAddAutoComplete.onItemClickListener = OnItemClickListener { parent, view_, position, _ ->
            feedback.tac = listofTAC[position]
        }

        /// Desired Possession
        val desiredpossessionAddAutoComplete = binding.autodesiredpossession
        val listofdesiredposs = arrayOf("Ready-to-Move", "Under Construction")
        val desiredpossessionAutoCompleteAdapter = ArrayAdapter(activity, R.layout._layout_spinner_item, listofdesiredposs)
        desiredpossessionAddAutoComplete.threshold = 3
        desiredpossessionAddAutoComplete.setAdapter(desiredpossessionAutoCompleteAdapter)
        desiredpossessionAddAutoComplete.onItemClickListener = OnItemClickListener { parent, view_, position, _ ->
            feedback.desiredPossession = listofdesiredposs[position]
        }

        binding.feedbackchipGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId != View.NO_ID) {

                // A chip has been selected
                val selectedChip = group.findViewById<Chip>(checkedId)

                // Change background color of selected chip
//                selectedChip.setChipBackgroundColorResource(com.stetig.solitaire.R.color.selected_chip_background_color)

                // Handle the selected chip
                val selectedText = selectedChip.text.toString()
                feedback.rating = selectedText
                when (selectedText) {
                    "Hot" -> {
                        val listOfDisposition = statusMap["Hot"]
                        val adapter = ArrayAdapter(
                            activity,
                            R.layout._layout_spinner_item,
                            listOfDisposition ?: arrayOf()
                        )
                        val autoCompleteTextView = binding.autodisposition
                        autoCompleteTextView.threshold = 3
                        autoCompleteTextView.setAdapter(adapter)
                        autoCompleteTextView.onItemClickListener = AdapterView.OnItemClickListener { parent, view_, position, _ ->
//                                val item = listOfOpportunities.get(position)
//                                createTaskRequest.opp_Id = item.oppId;

                            feedback.disposition = listOfDisposition?.get(position)
                            }

                    }

                    "Warm" -> {
                        val listOfDisposition = statusMap["Warm"]

                        val adapter = ArrayAdapter(
                            activity,
                            R.layout._layout_spinner_item,
                            listOfDisposition ?: arrayOf()
                        )

                        val autoCompleteTextView = binding.autodisposition
                        autoCompleteTextView.threshold = 3
                        autoCompleteTextView.setAdapter(adapter)
                        autoCompleteTextView.onItemClickListener = AdapterView.OnItemClickListener { parent, view_, position, _ ->
//                                val item = listOfOpportunities.get(position)
//                                createTaskRequest.opp_Id = item.oppId;
                            feedback.disposition = listOfDisposition?.get(position)
                        }
                    }

                    "Cold" -> {
                        val listOfDisposition = statusMap["Cold"]

                        val adapter = ArrayAdapter(
                            activity,
                            R.layout._layout_spinner_item,
                            listOfDisposition ?: arrayOf()
                        )

                        val autoCompleteTextView = binding.autodisposition
                        autoCompleteTextView.threshold = 3
                        autoCompleteTextView.setAdapter(adapter)
                        autoCompleteTextView.onItemClickListener =
                            AdapterView.OnItemClickListener { parent, view_, position, _ ->
//                                val item = listOfOpportunities.get(position)
//                                createTaskRequest.opp_Id = item.oppId;
                                feedback.disposition = listOfDisposition?.get(position)
                            }
                    }

                    "Booked" -> {
                        val listOfDisposition = statusMap["Booked"]

                        val adapter = ArrayAdapter(
                            activity,
                            R.layout._layout_spinner_item,
                            listOfDisposition ?: arrayOf()
                        )

                        val autoCompleteTextView = binding.autodisposition
                        autoCompleteTextView.threshold = 3
                        autoCompleteTextView.setAdapter(adapter)
                        autoCompleteTextView.onItemClickListener =
                            AdapterView.OnItemClickListener { parent, view_, position, _ ->
//                                val item = listOfOpportunities.get(position)
//                                createTaskRequest.opp_Id = item.oppId;
                                feedback.disposition = listOfDisposition?.get(position)
                            }
                    }

                    "Lost" -> {
                        val listOfDisposition = statusMap["Lost"]

                        val adapter = ArrayAdapter(
                            activity,
                            R.layout._layout_spinner_item,
                            listOfDisposition ?: arrayOf()
                        )

                        val autoCompleteTextView = binding.autodisposition
                        autoCompleteTextView.threshold = 3
                        autoCompleteTextView.setAdapter(adapter)
                        autoCompleteTextView.onItemClickListener =
                            AdapterView.OnItemClickListener { parent, view_, position, _ ->
//                                val item = listOfOpportunities.get(position)
//                                createTaskRequest.opp_Id = item.oppId;
                                feedback.disposition = listOfDisposition?.get(position)
                            }
                    }
                }
            } else {
                // No chip selected
            }
        }
        binding.submitBtn.setOnClickListener {
            log.e("output data", feedback.toString())
            feedback.designation = binding.designation.text.toString()
            feedback.pincode = binding.workLocationPincode.text.toString()
            feedback.smFeedback = binding.smFeedback.text.toString()
            feedback.placeofwork = binding.placeOfWork.text.toString()

            val account = SalesforceSDKManager.getInstance().userAccountManager.currentUser
            commonClassForApi?.FillFeedBackForm(disposableObserverTaskFromCallResponse, feedback, "Bearer " + account.authToken)
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


    private var disposableObserverTaskFromCallResponse: DisposableObserver<FeedBackResponse> =
        object : DisposableObserver<FeedBackResponse>() {
            override fun onNext(callStatusResponse: FeedBackResponse) {
                Utils.showToast(requireContext(), "FeedBack Sent!!")
//                    commonClassForQuery.getAllManualTasks(
//                        Query.ALL_MANUAL_TASK,
//                        onOpportunityListListener
//                    )
                activity.navHostFragment.findNavController().popBackStack()

            }

            override fun onError(e: Throwable) {
                Log.e(javaClass.name, "onError: 337 " + e.message)
            }

            override fun onComplete() {}
        }

    private fun showDatePickerDialogAnniversaryDate(datePickerEditText: EditText) {
        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val day = currentDate.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { view: DatePicker, selectedYear: Int, monthOfYear: Int, dayOfMonth: Int ->
                val selectedDate = "$selectedYear-" + "${(monthOfYear + 1).toString().padStart(2, '0')}-" + "${dayOfMonth.toString().padStart(2, '0')}"

                // Get the current date in the "yyyy-MM-dd" format
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                val currentDateFormatted = sdf.format(currentDate.time)

                // Check if the selected date is not a past date
//                if (selectedDate >= currentDateFormatted) {
                    feedback.anniversarydate = selectedDate
                    datePickerEditText.setText(selectedDate)
                    // Update your UI element with the selected date
//                    dateTextView.text = selectedDate
//                } else {
                    // Show an error message for selecting a past date
//                    dateTextView.text = "Please select a future date"
//                }
            },
            year,
            month,
            day
        )

        // Set the minimum date to the current date to restrict past dates
//        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        datePickerDialog.datePicker.setMaxDate(System.currentTimeMillis());

        // Show the date picker dialog
        datePickerDialog.show()
    }

    private fun showDatePickerDialogNextActionDate(datePickerEditText: EditText) {
        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val day = currentDate.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { view: DatePicker, selectedYear: Int, monthOfYear: Int, dayOfMonth: Int ->
                val selectedDate = "$selectedYear-" + "${(monthOfYear + 1).toString().padStart(2, '0')}-" + "${dayOfMonth.toString().padStart(2, '0')}"

                // Get the current date in the "yyyy-MM-dd" format
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                val currentDateFormatted = sdf.format(currentDate.time)

                // Check if the selected date is not a past date
                if (selectedDate >= currentDateFormatted) {
                    feedback.nextactiondate = selectedDate
                    datePickerEditText.setText(selectedDate)
                    // Update your UI element with the selected date
//                    dateTextView.text = selectedDate
                } else {
                    // Show an error message for selecting a past date
//                    dateTextView.text = "Please select a future date"
                }
            },
            year,
            month,
            day
        )

        // Set the minimum date to the current date to restrict past dates
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000

        // Show the date picker dialog
        datePickerDialog.show()
    }

}