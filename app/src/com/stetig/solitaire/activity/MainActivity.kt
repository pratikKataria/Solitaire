package com.stetig.solitaire.activity

import android.Manifest
import android.app.ActivityManager
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.Settings
import android.util.Log
import android.view.KeyEvent.KEYCODE_BACK
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.DatePicker
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.pm.PackageInfoCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import com.salesforce.androidsdk.app.SalesforceSDKManager
import com.stetig.solitaire.R
import com.stetig.solitaire.api.CommonClassForApi
import com.stetig.solitaire.api.CommonClassForQuery
import com.stetig.solitaire.api.Keys
import com.stetig.solitaire.api.Query
import com.stetig.solitaire.base.BaseActivity
import com.stetig.solitaire.callutilities.CallStateandRecordingService
import com.stetig.solitaire.data.AppVersionResponse
import com.stetig.solitaire.data.CallDispositionRequest
import com.stetig.solitaire.data.CallTaskRequest
import com.stetig.solitaire.data.CreateTaskFromCall
import com.stetig.solitaire.data.CreateTaskFromCallResponse
import com.stetig.solitaire.data.Event
import com.stetig.solitaire.data.Opportunity
import com.stetig.solitaire.data.OpportunityByMobileNumberResponse
import com.stetig.solitaire.data.ServerNotification
import com.stetig.solitaire.data.UpdateTokenReq
import com.stetig.solitaire.data.UpdateTokenRes
import com.stetig.solitaire.databinding.ActivityMainBinding
import com.stetig.solitaire.databinding.LayoutAlertDialogCallPopUpBinding
import com.stetig.solitaire.databinding.LayoutAlertDialogUpdateVersionBinding
import com.stetig.solitaire.fragment.CreateTaskFragment
import com.stetig.solitaire.utils.Alarm
import com.stetig.solitaire.utils.CallDuration
import com.stetig.solitaire.utils.CountryCodeRemover
import com.stetig.solitaire.utils.GetInCommingNumber
import com.stetig.solitaire.utils.SharedPrefs
import com.stetig.solitaire.utils.UpcommingNotificationPrefs
import com.stetig.solitaire.utils.Utils
import io.reactivex.observers.DisposableObserver
import retrofit2.HttpException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.Random
import java.util.Timer
import java.util.TimerTask


class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    var getTimeInMillis: Long = 0
    private var action: String = "Face to Face"
    private lateinit var commonClassForQuery: CommonClassForQuery
    private var commonClassForApi: CommonClassForApi? = null
    val navHostFragment: NavHostFragment get() = supportFragmentManager.findFragmentById(R.id.main_nav_host) as NavHostFragment
    private val mMobilNumber: String get() = GetInCommingNumber.getBulletProofNumber(this, intent)
    var timerTask: TimerTask? = null
    val TAG = "MainActivity"

    var callDispositionRequest = CallDispositionRequest()

    private val closedLostReasons = arrayOf(
        "Choose close lost reason", "Interested in other solitaire property",//1
        "Lost to Competitor",//2
        "Not in Budget",//3
        "Not interested in location",//4
        "Not responding",//5
        "Required inventory not available",//6
        "Not enquired with solitaire"//7
    )


    val statusMap = mutableMapOf<String, Array<String>>(
        "Hot" to arrayOf(
            "Booking in 3 - 5 days", "Partial Swipe Done", "PDC Cheque Received", "Cheque Pick Up", "Ancillary Service Calling Activity"
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


    var _manual_task = false
    var _manual_mobile_number = ""
    var _number_from_opportunity = ""
    var opportunityId = ""

    var selected = ""
    var callTaskRequest = CallTaskRequest()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initViews()
        updateUserVersionInformation()
    }

    private fun updateUserVersionInformation() {

        // Send User Version Data to Firebase
        val firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        val versionBundle = Bundle()
        val email = SalesforceSDKManager.getInstance()?.userAccountManager?.currentUser?.username ?: return

        val packageInfo = this.packageManager.getPackageInfo(this.packageName, 0)
        versionBundle.putString("user_email", email)
        versionBundle.putString("app_version", packageInfo.versionName)

        firebaseAnalytics.logEvent("user_version", versionBundle)

        val account = SalesforceSDKManager.getInstance().userAccountManager.currentUser
        val auth = "Bearer " + account.authToken
        if (account.username != null) {
            commonClassForApi?.checkDeviceVersion(disposableObserver = object : DisposableObserver<AppVersionResponse>() {
                @RequiresApi(Build.VERSION_CODES.P)
                override fun onNext(t: AppVersionResponse) {
                    val pInfo: PackageInfo = this@MainActivity.getPackageManager().getPackageInfo(this@MainActivity.packageName, 0)
                    val currentVersionCode = PackageInfoCompat.getLongVersionCode(pInfo);
                    Log.e(
                        TAG, "onNext: version update check currentVersionCode $currentVersionCode"
                    )


                    if (currentVersionCode < t.androidVersionCode!!) {
                        updateDialog()
                    }

                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "onError: ${e.message} version update")
                }

                override fun onComplete() {}
            }, auth = auth)
        }
    }


    override fun initViews() {
        commonClassForApi = CommonClassForApi.getInstance(this)

        setupNavigation()
        checkForOverlay()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkPermissions2()
        } else {
            checkPermissions()
        }
        binding.toolBarLayout.imageViewNotification.setOnClickListener {
//            navHostFragment.navController.popBackStack(R.id.homeFragment, false)
            navHostFragment.navController.popBackStack()
            navHostFragment.navController.navigate(com.stetig.solitaire.R.id.homeFragment)
            navHostFragment.navController.navigate(
                com.stetig.solitaire.R.id.notificationFragment, Bundle(), NavOptions.Builder().setLaunchSingleTop(true).build()
            )
        }

//        binding.toolBarLayout.imageViewSearch.setOnClickListener {
////            navHostFragment.navController.popBackStack(R.id.homeFragment, false)
//            navHostFragment.navController.popBackStack()
//            navHostFragment.navController.navigate(R.id.homeFragment)
//            navHostFragment.navController.navigate(
//                R.id.searchFragment,
//                Bundle(),
//                NavOptions.Builder().setLaunchSingleTop(true).build()
//            )
//        }

        timerTask = object : TimerTask() {
            override fun run() {
                checkNotification()
            }
        }

        val timer = Timer()
        timer.scheduleAtFixedRate(timerTask, 0, 3000L)

//        showPopUp(false, "")
    }

    var tempcom: String? = null;
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        tempcom = intent?.getStringExtra("commType").toString()
        Log.e("TAG", "onNewIntent: ${intent?.getStringExtra("commType")}")
        setIntent(intent);
    }

    private fun setupNavigation() {
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController)
    }

    override fun onResume() {
        super.onResume()
        startCallHandlerService()
        checkForCallPopUp()
        checkNotification()
//        checkPermissions()

//        FirebaseCrashlytics.getInstance().recordException(RuntimeException("Send Logs"))
        if (SalesforceSDKManager.getInstance().userAccountManager.currentUser == null) return
        updateToken()

    }

    private fun updateToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            val token = task.result
            val account = SalesforceSDKManager.getInstance().userAccountManager.currentUser
            val auth = "Bearer " + account.authToken
            if (account.username != null) {
                val updateTokenReq = UpdateTokenReq(token, account.username)
                commonClassForApi?.updateTokenInSalesForce(updateTokenResDisposableObserver, updateTokenReq, auth)
                Log.e(javaClass.name, "onComplete: 115 firebase fcm token $token")
            }

        })
    }

    private fun getDeviceName(): String {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        if (model.startsWith(manufacturer)) {
            return model
        } else {
            return "$manufacturer $model"
        }
    }

    var updateTokenResDisposableObserver: DisposableObserver<UpdateTokenRes> = object : DisposableObserver<UpdateTokenRes>() {
        override fun onNext(updateTokenRes: UpdateTokenRes) {
            Log.e(javaClass.name, "onNext: 494 $updateTokenRes")
        }

        override fun onError(e: Throwable) {
            if (e is HttpException) {
                if (e.code() == 401) {
                    SalesforceSDKManager.getInstance().logout(this@MainActivity)
                }
            }
        }

        override fun onComplete() {}
    }


    fun checkForCallPopUp() {
        if (SharedPrefs.getBooleanData(this, Keys.CALL_DETECTED)) {

            showPopUp(false, "")
        }
    }

    private lateinit var popUpBinding: LayoutAlertDialogCallPopUpBinding
    private lateinit var alertDialog: AlertDialog

    fun showPopUp(
        MANUAL_TASK: Boolean = false, opportunityId: String, MANUAL_MOBILE_NUMBER: String = ""
    ) {
        this._manual_task = MANUAL_TASK
        this.opportunityId = opportunityId
        this._manual_mobile_number = MANUAL_MOBILE_NUMBER

        SharedPrefs.saveData(this, Keys.CALL_DETECTED, false)
        val builder = MaterialAlertDialogBuilder(this, R.style.AlertDialog)
        popUpBinding = LayoutAlertDialogCallPopUpBinding.inflate(layoutInflater, null, false)
        builder.setView(popUpBinding.root)
        builder.setCancelable(false)
        try {
            if (alertDialog.isShowing()) {
                alertDialog.dismiss()
                Log.e(javaClass.name, "showPopUp: " + "dismissed")
            }
        } catch (ignored: java.lang.Exception) {
            Log.e(javaClass.name, "showPopUp: " + ignored.message)
        }
        if (MANUAL_TASK) {
            popUpBinding.yesNoCall.rootLayout.visibility = GONE
            popUpBinding.yesNoCallDetail.root.visibility = VISIBLE
        }
        alertDialog = builder.show()
        val window: Window? = alertDialog.window
        window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        )
        alertDialog.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KEYCODE_BACK) {
                try {
                    alertDialog.dismiss()
                    Log.e(javaClass.name, "showPopUp: " + "dismissed")
                } catch (ignored: java.lang.Exception) {
                    Log.e(javaClass.name, "showPopUp: " + ignored.message)
                }
                true
            } else false
        }

        popUpBinding.yesNoCall.yes.setOnClickListener {
            popUpBinding.yesNoCall.rootLayout.visibility = GONE
            popUpBinding.opportunityOrCp.rootLayout.visibility = VISIBLE

//            popUpBinding.yesNoCallDetail.detailRootLayout.visibility = VISIBLE
        }

        popUpBinding.yesNoCall.no.setOnClickListener { alertDialog.dismiss() }


        popUpBinding.opportunityOrCp.opportunity.setOnClickListener {
            callDispositionRequest.recordType = "Opportunity"
            callTaskRequest.recordType = "Opportunity"


            commonClassForQuery = CommonClassForQuery.getInstance(this@MainActivity, this@MainActivity.getRestClient())!!
            commonClassForQuery.mobileNumberOnOpportunity(Query.OPPORTUNITY_ON_MOBILE_NUMBER + Utils.buildQueryParameter(CountryCodeRemover.numberFormatter(mMobilNumber)),
                object : CommonClassForQuery.OnDataReceiveListener {
                    override fun onDataReceive(data: Any) {
                        if (data is OpportunityByMobileNumberResponse) {

                            if (data.records.isEmpty()) {
                                popUpBinding.opportunityOrCp.rootLayout.visibility = GONE
                                popUpBinding.yesNoCallDetail.detailRootLayout.visibility = VISIBLE
                                return
                            }

                            popUpBinding.opportunityOrCp.rootLayout.visibility = GONE
                            popUpBinding.selectOpportunityLayout.llSelectOpty.visibility = VISIBLE

                            val customAdapter = CustomAdapter(this@MainActivity, R.layout._layout_spinner, data.records)
                            val autoCompleteTextView = popUpBinding.selectOpportunityLayout.autoCompleteTextView
                            autoCompleteTextView.threshold = 3
                            autoCompleteTextView.setAdapter(customAdapter)
                            popUpBinding.selectOpportunityLayout.autoCompleteTextView.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                                    callTaskRequest.opportunityId = data.records[position]?.id
                                }

                                override fun onNothingSelected(parent: AdapterView<*>?) {}
                            }

//                            val listOfRecords = data.records.map { "Opportunity: ${it?.name}\n Project Name: ${it?.projectR?.name}" ?: "" }
//                            listOfRecords.forEach {
//                                val aa: ArrayAdapter<*> = ArrayAdapter<Any?>(this@MainActivity, R.layout._layout_spinner_item, listOfRecords)
//                                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//
//
//                                popUpBinding.selectOpportunityLayout.opportunitySpinnerStage.adapter = aa
//                                popUpBinding.selectOpportunityLayout.opportunitySpinnerStage.onItemSelectedListener =
//                                    object : AdapterView.OnItemSelectedListener {
//                                        override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
//                                        }
//
//                                        override fun onNothingSelected(parent: AdapterView<*>?) {}
//                                    }
//                            }

                            popUpBinding.selectOpportunityLayout.next.setOnClickListener {
                                popUpBinding.opportunityOrCp.rootLayout.visibility = GONE
                                popUpBinding.yesNoCallDetail.detailRootLayout.visibility = VISIBLE
                            }
//                        projectList.clear()
//                        projectList.addAll(data.records)
//                        adapter.notifyDataSetChanged()
//                        activity.checkListIsEmpty(data.records)
                        }
                    }

                    override fun onError(obj: String) {

                    }
                })
        }

        popUpBinding.opportunityOrCp.cp.setOnClickListener {
            callDispositionRequest.recordType = "CP"
            callTaskRequest.recordType = "CP"
            popUpBinding.opportunityOrCp.rootLayout.visibility = GONE
            popUpBinding.yesNoCallDetail.detailRootLayout.visibility = VISIBLE
        }

        popUpBinding.yesNoCallDetail.date.setOnClickListener {
            showDatePickerDialog(popUpBinding.yesNoCallDetail.date)
        }

        popUpBinding.yesNoCallDetail.time.setOnClickListener {
            showTimePickerDialog(popUpBinding.yesNoCallDetail.time)
        }


        val layoutProfessionalCallDetailBinding = popUpBinding.yesNoCallDetail

        layoutProfessionalCallDetailBinding.callStatusGroup.setOnCheckedChangeListener { group, checkedId ->
            val chip: RadioButton = group.findViewById(checkedId)
            val chipText = chip.text.toString()
//            nextActionDate = "" //resetting the next action date on selection
//            visitDate = "" //resetting the visit date on selection
//            when (chipText) {
//                "Call Back", "Follow Up" -> showNextActionDateLayout()
//                "Visit Proposed", "Visit Scheduled" -> showVisitDateLayout()
//                else -> hideBoth()
//            }
            action = chipText
            callTaskRequest.callAttemptStatus = chipText
        }

        layoutProfessionalCallDetailBinding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
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
                            this@MainActivity, R.layout._layout_spinner_item, listOfDisposition ?: arrayOf()
                        )
                        val autoCompleteTextView = layoutProfessionalCallDetailBinding.autoCompleteTextView
                        autoCompleteTextView.threshold = 3
                        autoCompleteTextView.setAdapter(adapter)
                        autoCompleteTextView.onItemClickListener = AdapterView.OnItemClickListener { parent, view_, position, _ ->
//                                val item = listOfOpportunities.get(position)
//                                createTaskRequest.opp_Id = item.oppId;
                            callTaskRequest.dispositionPicklist = listOfDisposition?.get(position)
                        }

                        autoCompleteTextView.text.clear()
                        callTaskRequest.rating = selectedText
                    }

                    "Warm" -> {
                        val listOfDisposition = statusMap["Warm"]

                        val adapter = ArrayAdapter(
                            this@MainActivity, R.layout._layout_spinner_item, listOfDisposition ?: arrayOf()
                        )

                        val autoCompleteTextView = layoutProfessionalCallDetailBinding.autoCompleteTextView
                        autoCompleteTextView.threshold = 3
                        autoCompleteTextView.setAdapter(adapter)
                        autoCompleteTextView.onItemClickListener = AdapterView.OnItemClickListener { parent, view_, position, _ ->
//                                val item = listOfOpportunities.get(position)
//                                createTaskRequest.opp_Id = item.oppId;
                            callTaskRequest.dispositionPicklist = listOfDisposition?.get(position)
                        }
                        autoCompleteTextView.text.clear()
                        callTaskRequest.rating = selectedText
                    }

                    "Cold" -> {
                        val listOfDisposition = statusMap["Cold"]

                        val adapter = ArrayAdapter(
                            this@MainActivity, R.layout._layout_spinner_item, listOfDisposition ?: arrayOf()
                        )

                        val autoCompleteTextView = layoutProfessionalCallDetailBinding.autoCompleteTextView
                        autoCompleteTextView.threshold = 3
                        autoCompleteTextView.setAdapter(adapter)
                        autoCompleteTextView.onItemClickListener = AdapterView.OnItemClickListener { parent, view_, position, _ ->
//                                val item = listOfOpportunities.get(position)
//                                createTaskRequest.opp_Id = item.oppId;
                            callTaskRequest.dispositionPicklist = listOfDisposition?.get(position)
                        }
                        autoCompleteTextView.text.clear()
                        callTaskRequest.rating = selectedText
                    }

                    "Booked" -> {
                        val listOfDisposition = statusMap["Booked"]

                        val adapter = ArrayAdapter(
                            this@MainActivity, R.layout._layout_spinner_item, listOfDisposition ?: arrayOf()
                        )

                        val autoCompleteTextView = layoutProfessionalCallDetailBinding.autoCompleteTextView
                        autoCompleteTextView.threshold = 3
                        autoCompleteTextView.setAdapter(adapter)
                        autoCompleteTextView.onItemClickListener = AdapterView.OnItemClickListener { parent, view_, position, _ ->
//                                val item = listOfOpportunities.get(position)
//                                createTaskRequest.opp_Id = item.oppId;
                            callTaskRequest.dispositionPicklist = listOfDisposition?.get(position)
                        }
                        autoCompleteTextView.text.clear()
                        callTaskRequest.rating = selectedText
                    }

                    "Lost" -> {
                        val listOfDisposition = statusMap["Lost"]

                        val adapter = ArrayAdapter(
                            this@MainActivity, R.layout._layout_spinner_item, listOfDisposition ?: arrayOf()
                        )

                        val autoCompleteTextView = layoutProfessionalCallDetailBinding.autoCompleteTextView
                        autoCompleteTextView.threshold = 3
                        autoCompleteTextView.setAdapter(adapter)
                        autoCompleteTextView.onItemClickListener = AdapterView.OnItemClickListener { parent, view_, position, _ ->
//                                val item = listOfOpportunities.get(position)
//                                createTaskRequest.opp_Id = item.oppId;
                            callTaskRequest.dispositionPicklist = listOfDisposition?.get(position)
                        }
                        autoCompleteTextView.text.clear()
                        callTaskRequest.rating = selectedText
                    }
                }
            } else {
                // No chip selected
            }
        }

        layoutProfessionalCallDetailBinding.submitCalldisposition.setOnClickListener {
            val account = SalesforceSDKManager.getInstance().userAccountManager.currentUser
            val auth = "Bearer " + account.authToken
            callTaskRequest.mobileNumber = CountryCodeRemover.numberFormatter(mMobilNumber)
            callTaskRequest.communicationtype = if (tempcom == "OutCall") "Outbound Call" else "Inbound call"
//            callTaskRequest.communicationtype =
//            callTaskRequest.rating = "Hot"
//            callTaskRequest.dispositionPicklist = "Partial Swipe Done"
            callTaskRequest.comment = popUpBinding.yesNoCallDetail.desc.text.toString()


            commonClassForApi?.salesCallTaskRequest(disposableObserverCallTaskObserver, callTaskRequest, auth)
        }


//        popUpBinding.yesNoCallDetail.min15.setOnClickListener { createFollowUp(0) }
//        popUpBinding.yesNoCallDetail.h1.setOnClickListener { createFollowUp(1) }
//        popUpBinding.yesNoCallDetail.customTime.setOnClickListener { createFollowUp(2) }
//
//
//        popUpBinding.yesNoCallDetail.face2faceBtn.setOnClickListener {
//            if (popUpBinding.yesNoCallDetail.desc.getText().toString().length < 10) {
//                popUpBinding.yesNoCallDetail.desc.requestFocus()
//                popUpBinding.yesNoCallDetail.desc.setError("should not be empty or greater then 10")
//                Toast.makeText(
//                    this@MainActivity,
//                    "comment should not be empty or greater then 10",
//                    Toast.LENGTH_SHORT
//                ).show()
//                return@setOnClickListener
//            }
//            action = Query.FACE_TO_FACE
//            val calendar = Calendar.getInstance()
//            val datePickerDialog = DatePickerDialog(
//                this@MainActivity,
//                R.style.MyDatePickerDialogTheme,
//                f2f_sv_time_picker,
//                calendar[Calendar.YEAR],
//                calendar[Calendar.MONTH],
//                calendar[Calendar.DAY_OF_MONTH]
//            )
//            datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
//            datePickerDialog.show()
//        }
//
//        popUpBinding.yesNoCallDetail.SVBtn.setOnClickListener {
//            if (popUpBinding.yesNoCallDetail.desc.getText().toString().length < 10) {
//                popUpBinding.yesNoCallDetail.desc.requestFocus()
//                popUpBinding.yesNoCallDetail.desc.setError("should not be empty or greater then 10")
//                Toast.makeText(
//                    this@MainActivity,
//                    "comment should not be empty or greater then 10",
//                    Toast.LENGTH_SHORT
//                ).show()
//                return@setOnClickListener
//            }
//            action = Query.SITE_VISIT
//            val calendar = Calendar.getInstance()
//            val datePickerDialog = DatePickerDialog(
//                this@MainActivity,
//                R.style.MyDatePickerDialogTheme,
//                f2f_sv_time_picker,
//                calendar[Calendar.YEAR],
//                calendar[Calendar.MONTH],
//                calendar[Calendar.DAY_OF_MONTH]
//            )
//            datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
//            datePickerDialog.show()
//        }

//        popUpBinding.yesNoCallDetail.callNow.setOnClickListener {
//            OpportunityRecyclerAdapter.callNumber(
//                mMobilNumber, this@MainActivity
//            )
//        }
//        popUpBinding.yesNoCallDetail.shareProject.setOnClickListener {
//            val commonClassForQuery = CommonClassForQuery.getInstance(this, getRestClient())!!
//            commonClassForQuery.getOptyDetail(
//                Query.PROJECT_NAME_FROM_OPPORTUNITY_NUMBER + Utils.buildQueryParameter(
//                    mMobilNumber
//                ), onOpportunityListListener
//            )
//
//        }
//        popUpBinding.yesNoCallDetail.closeLostBtn.setOnClickListener {
//            popUpBinding.closeLost.closeLostLayout.visibility = VISIBLE
//            popUpBinding.yesNoCallDetail.detailRootLayout.visibility = GONE
//        }
//
//        popUpBinding.yesNoCallDetail.proposal.setOnClickListener {
//            if (popUpBinding.yesNoCallDetail.desc.getText().toString().length < 10) {
//                popUpBinding.yesNoCallDetail.desc.requestFocus()
//                popUpBinding.yesNoCallDetail.desc.setError("should not be empty or greater then 10")
//                Toast.makeText(
//                    this@MainActivity,
//                    "comment should not be empty or greater then 10",
//                    Toast.LENGTH_SHORT
//                ).show()
//                return@setOnClickListener
//            }
//            val cal = Calendar.getInstance()
//            cal.time = Date()
//            cal.add(Calendar.MINUTE, 15)
//            buildSendStatus(cal.time, "Proposal Sent", MANUAL_TASK, opportunityId)
//        }

        val aa: ArrayAdapter<*> = ArrayAdapter<Any?>(
            this@MainActivity, com.stetig.solitaire.R.layout._layout_spinner_item, closedLostReasons
        )
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        popUpBinding.closeLost.updateOpportunitySpinnerStage.adapter = aa
        popUpBinding.closeLost.updateOpportunitySpinnerStage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View, position: Int, id: Long
            ) {
                selected = closedLostReasons[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        popUpBinding.closeLost.no.setOnClickListener {
            popUpBinding.closeLost.closeLostLayout.visibility = GONE
            popUpBinding.yesNoCallDetail.detailRootLayout.visibility = VISIBLE
        }

        popUpBinding.closeLost.yes.setOnClickListener {

            if (selected.isEmpty()) {
                Utils.setToast(this@MainActivity, "Please Select Reason")
                return@setOnClickListener
            }

            if (selected.equals("Choose close lost reason", ignoreCase = true)) {
                Utils.setToast(this@MainActivity, "Please Select Reason")
                return@setOnClickListener
            }


            buildSendCloseLost(
                CountryCodeRemover.numberFormatter(
                    if (MANUAL_TASK) {
                        _manual_mobile_number
                    } else {
                        mMobilNumber
                    }
                )
            )
        }
    }

    private fun showDatePickerDialog(datePickerEditText: EditText) {
        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val day = currentDate.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this@MainActivity, { view: DatePicker, selectedYear: Int, monthOfYear: Int, dayOfMonth: Int ->
                val selectedDate = "$selectedYear-" + "${(monthOfYear + 1).toString().padStart(2, '0')}-" + "${dayOfMonth.toString().padStart(2, '0')}"

                // Get the current date in the "yyyy-MM-dd" format
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                val currentDateFormatted = sdf.format(currentDate.time)

                // Check if the selected date is not a past date
                if (selectedDate >= currentDateFormatted) {
                    datePickerEditText.setText(selectedDate)
                    callTaskRequest.callDate = selectedDate
                    // Update your UI element with the selected date
//                    dateTextView.text = selectedDate
                } else {
                    // Show an error message for selecting a past date
//                    dateTextView.text = "Please select a future date"
                }
            }, year, month, day
        )

        // Set the minimum date to the current date to restrict past dates
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000

        // Show the date picker dialog
        datePickerDialog.show()
    }


    fun updateDialog() {
        val builder = MaterialAlertDialogBuilder(this, R.style.AlertDialog)
        val updateVersionDialogBinding = LayoutAlertDialogUpdateVersionBinding.inflate(layoutInflater, null, false)
        builder.setView(updateVersionDialogBinding.root)
        builder.setCancelable(false)


        val updateAlertDialog = builder.show()
        val window: Window? = updateAlertDialog.window
        window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        )

        updateVersionDialogBinding.update.setOnClickListener {
            val appPackageName = packageName // package name of the app

            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")
                    )
                )
            } catch (anfe: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                    )
                )
            }
        }
    }

    fun buildSendCloseLost(fMobileNumber: String) {
        val account = SalesforceSDKManager.getInstance().userAccountManager.currentUser
        val salesUserId = account.username
        val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm a")
        val nextActionDate: String = sdf.format(Date())
        val status: String = "Closed lost"
        val mobileNumber: String = fMobileNumber
        val communicationType = ""
        val projectName = "solitaire"
        val description = ""
        val closedLostReason = selected

        val data = CreateTaskFromCall(
            closedlostreason = closedLostReason,
            description = description,
            projectname = projectName,
            nextactiondate = nextActionDate,
            callproposeddateofvisit = nextActionDate,
            mobilenumber = mobileNumber,
            callAttemptStatus = status,
            communicationtype = communicationType,
            salesuserid = salesUserId,
        )

        val auth = "Bearer " + account.authToken
        commonClassForApi!!.callAttempt(closeLost, data, auth)
    }

    private var closeLost: DisposableObserver<CreateTaskFromCallResponse> = object : DisposableObserver<CreateTaskFromCallResponse>() {
        override fun onNext(callStatusResponse: CreateTaskFromCallResponse) {


            if (callStatusResponse.message.equals("Please update App to the latest version.")) {
                Utils.setToast(this@MainActivity, "Please update App to the latest version.")
            }


            if (callStatusResponse.opportunityId == null) {
                Utils.setToast(this@MainActivity, "Unable to tag opportunity")
            } else {
                Utils.setToast(this@MainActivity, "Successful")
            }

            navHostFragment.navController.popBackStack(R.id.homeFragment, false)
            try {
                if (alertDialog.isShowing()) {
                    alertDialog.dismiss()
                    Log.e(javaClass.name, "showPopUp: " + "dismissed")
                }
            } catch (ignored: java.lang.Exception) {
                Log.e(javaClass.name, "showPopUp: " + ignored.message)
            }
        }

        override fun onError(e: Throwable) {
            Log.e(javaClass.name, "onError: 337 " + e.message)
        }

        override fun onComplete() {}
    }

    private val onOpportunityListListener = object : CommonClassForQuery.OnDataReceiveListener {
        override fun onDataReceive(data: Any) {
            if (data is Opportunity) {

                if (data.records.isEmpty()) {
                    Utils.setToast(this@MainActivity, "No Opportunity Found")
                    return
                }
                val opty = data.records[0]

                if (Utils.checkValueOrGiveEmpty(opty.projectC).isEmpty()) {
                    Utils.setToast(this@MainActivity, "Project Name not found")
                    return
                }

//                OpportunityRecyclerAdapter.sendProjectDetails(
//                    Utils.checkValueOrGiveEmpty(opty.projectC),
//                    Utils.checkValueOrGiveEmpty(opty.accountMobileNumberC),
//                    Utils.checkValueOrGiveEmpty(opty.accountEmailIdC),
//                    this@MainActivity,
//                    R.id.projectDetailFragment
//                )
                alertDialog.dismiss()
            }
        }

        override fun onError(obj: String) {

        }
    }

    private fun createFollowUp(type: Int) {
        if (popUpBinding.yesNoCallDetail.desc.getText().toString().length < 10) {
            popUpBinding.yesNoCallDetail.desc.requestFocus()
            popUpBinding.yesNoCallDetail.desc.setError("should not be empty or greater then 10")
            Toast.makeText(
                this@MainActivity, "comment should not be empty or greater then 10", Toast.LENGTH_SHORT
            ).show()
            return
        }

        when (type) {
            0 -> {
                val calender = Calendar.getInstance()
                calender.add(Calendar.MINUTE, 15)
                Log.e(TAG, "15 mina: ${calender.time.toString()}")
                val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm a")
                val nextActionDate: String = sdf.format(calender.time)
                Log.e(TAG, "15 formated: ${nextActionDate}")
                buildSendStatus(calender.time, "Follow Up", _manual_task, opportunityId)
            }

            1 -> {
                val calendar = Calendar.getInstance()
                calendar.add(Calendar.HOUR, 1)
                buildSendStatus(calendar.time, "Follow up", _manual_task, opportunityId)
            }

            2 -> {
                val calendar = Calendar.getInstance()
                val datePickerDialog = DatePickerDialog(
                    this@MainActivity, R.style.MyDatePickerDialogTheme, customTimePicker, calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH]
                )
                datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000;
                datePickerDialog.show()
            }
        }
    }


    var taskType = ""
    var time: Long = 0
    fun buildSendStatus(
        timestamp: Date, task: String, MANUAL_TASK: Boolean, opportunityId: String
    ) {
        val account = SalesforceSDKManager.getInstance().userAccountManager.currentUser
        val salesUserId = account.username
        val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm a")
        val mobileNumber = CountryCodeRemover.numberFormatter(
            if (MANUAL_TASK) {
                _manual_mobile_number
            } else {
                mMobilNumber
            }
        )
        val nextActionDate: String = sdf.format(timestamp)
        Log.e(TAG, "buildSendStatus: ${nextActionDate}")
        val status: String = task
        val communicationType: String = GetInCommingNumber.callType(this)
        val projectName = "solitaire"
        val description = popUpBinding.yesNoCallDetail.desc.text.toString()
        val closedLostReason = ""
        getTimeInMillis = timestamp.time


        time = timestamp.time
        taskType = task

        if (MANUAL_TASK) {

            val data = CreateTaskFromCall(
                description = description,
                optyId = opportunityId,
                nextactiondate = nextActionDate,
                callproposeddateofvisit = nextActionDate,
                mobilenumber = mobileNumber,
                callAttemptStatus = status,
                communicationtype = communicationType,
                salesuserid = salesUserId
            )

            val auth = "Bearer " + account.authToken
            commonClassForApi!!.createTaskFromOpportunity(disposableObserver, data, auth)

        } else {
            val totalDurationInSeconds = CallDuration.getCallDurationByMobileNo(this, mobileNumber)
            val callDuration = CallDuration.getFormattedCallDuration(totalDurationInSeconds)

            val data = CreateTaskFromCall(
                closedlostreason = closedLostReason,
                description = description,
                projectname = projectName,
                nextactiondate = nextActionDate,
                callproposeddateofvisit = nextActionDate,
                mobilenumber = mobileNumber,
                callAttemptStatus = status,
                communicationtype = communicationType,
                salesuserid = salesUserId,
                callDuration = callDuration,
                callDurationInSeconds = totalDurationInSeconds
            )

            val auth = "Bearer " + account.authToken
            checkAppVersionAttemptCall(data)
        }
    }

    private fun checkAppVersionAttemptCall(data: CreateTaskFromCall) {
        // Send User Version Data to Firebase
        val firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        val versionBundle = Bundle()
        val email = SalesforceSDKManager.getInstance()?.userAccountManager?.currentUser?.username ?: return

        val packageInfo = this.packageManager.getPackageInfo(this.packageName, 0)
        versionBundle.putString("user_email", email)
        versionBundle.putString("app_version", packageInfo.versionName)

        firebaseAnalytics.logEvent("user_version", versionBundle)

        val account = SalesforceSDKManager.getInstance().userAccountManager.currentUser
        val auth = "Bearer " + account.authToken
        if (account.username != null) {
            commonClassForApi?.checkDeviceVersion(disposableObserver = object : DisposableObserver<AppVersionResponse>() {
                @RequiresApi(Build.VERSION_CODES.P)
                override fun onNext(t: AppVersionResponse) {
                    val pInfo: PackageInfo = this@MainActivity.getPackageManager().getPackageInfo(this@MainActivity.packageName, 0)
                    val currentVersionCode = PackageInfoCompat.getLongVersionCode(pInfo);
                    Log.e(
                        TAG, "onNext: version update check currentVersionCode $currentVersionCode"
                    )

                    if (currentVersionCode < t.androidVersionCode!!) {
                        updateDialog()
                    } else {
                        commonClassForApi!!.callAttempt(disposableObserver, data, auth)
                    }

                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "onError: ${e.message} version update")
                }

                override fun onComplete() {}
            }, auth = auth)
        }
    }

    private fun showTimePickerDialog(date: AutoCompleteTextView) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        // Create a TimePickerDialog and show it
        val timePickerDialog = TimePickerDialog(
            this, { view, hourOfDay, minute ->

                var time: String = hourOfDay.toString() + ":" + minute;
                print("Time selected$time");
                date.setText(convertTo12HourFormat(time))
                callTaskRequest.calltime = "$hourOfDay:$minute:00"

            }, hour, minute, false
        )

        timePickerDialog.show()

    }

    fun convertTo12HourFormat(time24Hour: String): String {
        val inputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val outputFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())

        var formattedTime = ""
        try {
            val date = inputFormat.parse(time24Hour)
            formattedTime = outputFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return formattedTime
    }


    var customTimePicker = OnDateSetListener { datePicker: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->
        datePicker?.minDate = System.currentTimeMillis() - 1000;
        val calendar = Calendar.getInstance()
        val timePicker = TimePickerDialog(
            this@MainActivity, R.style.MyDatePickerDialogTheme, { timePicker, i, i1 ->
                val calendar = Calendar.getInstance()
                calendar[year, month, dayOfMonth, i] = i1
                buildSendStatus(calendar.time, "Follow Up", _manual_task, opportunityId)
            }, calendar[Calendar.HOUR], calendar[Calendar.MINUTE], false
        )
        timePicker.show()
    }

    private var f2f_sv_time_picker = OnDateSetListener { datePicker: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->
        datePicker?.minDate = System.currentTimeMillis() - 1000;
        val calendar = Calendar.getInstance()
        val timePicker = TimePickerDialog(
            this@MainActivity, R.style.MyDatePickerDialogTheme, { timePicker, i, i1 ->
                val calendar = Calendar.getInstance()
                calendar[year, month, dayOfMonth, i] = i1
                buildSendStatus(calendar.time, action, _manual_task, opportunityId)
            }, calendar[Calendar.HOUR], calendar[Calendar.MINUTE], false
        )
        timePicker.show()
    }


    private fun startCallHandlerService() {
        try {
            val callStateAndRecordingService = CallStateandRecordingService()
            val serviceIntent = Intent(this, CallStateandRecordingService::class.java)
            val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
            val record = preferences.getBoolean("record", true)
            if (!isMyServiceRunning(CallStateandRecordingService::class.java) && record) {
                startService(serviceIntent)
            }
            if (!record) {
                applicationContext.stopService(serviceIntent)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                Log.i("Service status", "Running")
                return true
            }
        }
        Log.i("Service status", "Not running")
        return false
    }

    private val PERMISSION_REQUEST_CODE = 100

    private var permissions = arrayOf(
        Manifest.permission.CALL_PHONE,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.READ_CALL_LOG,
    )

    private fun checkPermissions() {
        ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun checkPermissions2() {
        var permissions1 = arrayOf(
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.POST_NOTIFICATIONS
        )
        ActivityCompat.requestPermissions(this, permissions1, PERMISSION_REQUEST_CODE);
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            // Check if all permissions are granted
            var allPermissionsGranted = true
            for (grantResult in grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    // At least one permission is not granted
                    allPermissionsGranted = false
                    break
                }
            }
        }


    }

    private fun checkForOverlay() {
        if (checkSelfPermission(Manifest.permission.READ_CALL_LOG) === PackageManager.PERMISSION_DENIED) {
            requestPermissions(arrayOf(Manifest.permission.READ_CALL_LOG), 1010)
        }
        if (!Settings.canDrawOverlays(this)) {
            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(this@MainActivity)
            materialAlertDialogBuilder.setTitle("Require ScreenOverlay Permission.")
            materialAlertDialogBuilder.setMessage("The app requires to draw over other apps to show call pop up window when app is in background")
            materialAlertDialogBuilder.setPositiveButton("yes") { dialog: DialogInterface?, which: Int ->
                val intent1 = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName")
                )
                startActivityForResult(intent1, 0)
            }
            materialAlertDialogBuilder.setCancelable(false)
            materialAlertDialogBuilder.show()
        }
    }

    private var disposableObserver: DisposableObserver<CreateTaskFromCallResponse> = object : DisposableObserver<CreateTaskFromCallResponse>() {
        override fun onNext(callStatusResponse: CreateTaskFromCallResponse) {
            _manual_task = false

            if (callStatusResponse.message.equals("Please update App to the latest version.")) {
                Utils.setToast(this@MainActivity, "Please update App to the latest version.")
            }


            if (callStatusResponse.opportunityId == null) {
                Utils.setToast(this@MainActivity, "Unable to tag opportunity")
            } else {
                Utils.setToast(this@MainActivity, "Successful")
            }

            if (callStatusResponse.opportunityId != null) {
//                val notificationlist = ServerNotification.NotificationList()
//
//                notificationlist.setOppid(callStatusResponse.opportunityId)
//                notificationlist.setNotificationid(callStatusResponse.id)
//                notificationlist.setType("Reminder")
//                notificationlist.setOppname("")
//                notificationlist.setTitle("Click to view opportunity details")
//                UpcommingNotificationPrefs.saveData(this@MainActivity, callStatusResponse.id, Gson().toJson(notificationlist))
//
//                val event = Event(taskType + "-" + callStatusResponse.opportunityname, time, callStatusResponse.id, callStatusResponse.opportunityId)
//                val sharedPreferences = getSharedPreferences("calendarEvents", MODE_PRIVATE)
//                val randomInt = Random().nextInt(12112)
//                sharedPreferences.edit().putString(callStatusResponse.id + randomInt, Gson().toJson(event)).apply()
//
//                val calendar = Calendar.getInstance()
//                calendar.time = Date(getTimeInMillis)
//                calendar.add(Calendar.MINUTE, -15)
//
//                Alarm.setAlarm(
//                    application, calendar.time.time, callStatusResponse.id, callStatusResponse.opportunityId, callStatusResponse.opportunityname
//                )
//                Alarm.setAlarm(
//                    application, calendar.time.time, callStatusResponse.id, callStatusResponse.opportunityId, callStatusResponse.opportunityname
//                )
            }

            try {
                alertDialog.dismiss()
            } catch (e: Exception) {
            }
        }

        override fun onError(e: Throwable) {
            Log.e(javaClass.name, "onError: 337 " + e.message)
        }

        override fun onComplete() {}
    }


    private var disposableObserverCallTaskObserver: DisposableObserver<CreateTaskFromCallResponse> = object : DisposableObserver<CreateTaskFromCallResponse>() {
        override fun onNext(callStatusResponse: CreateTaskFromCallResponse) {

            try {
                Utils.setToast(this@MainActivity, "Successful")
                alertDialog.dismiss()
            } catch (e: Exception) {

            }
        }

        override fun onError(e: Throwable) {
            Log.e(javaClass.name, "onError: 337 " + e.message)
        }

        override fun onComplete() {}
    }

    fun checkNotification() {
        try {
            Log.e("mainActivity", "checkNotification: checking notification")
            runOnUiThread {
                val isActive = SharedPrefs.getBooleanData(this, SharedPrefs.IS_ACTIVE)
                if (isActive) {
                    findViewById<View>(R.id.textViewNotificationCounter).visibility = VISIBLE
                } else {
                    findViewById<View>(R.id.textViewNotificationCounter).visibility = GONE
                }
            }
        } catch (e: java.lang.Exception) {
            Log.e("mainActivity", "checkNotification: ${e.message}")
            e.printStackTrace()
        }
    }

    public fun checkListIsEmpty(list: List<*>) = if (list.isEmpty()) binding.noDataFound.visibility = VISIBLE else binding.noDataFound.visibility = GONE

    public fun hideNoDataText() {
        binding.noDataFound.visibility = GONE
    }

    override fun onDestroy() {
        Log.e(TAG, "onDestroy: timer task canceled");
        timerTask?.cancel()
        super.onDestroy()
    }


    class CustomAdapter(context: Context, private val resource: Int, private val items: List<OpportunityByMobileNumberResponse.Record?>) :
        ArrayAdapter<OpportunityByMobileNumberResponse.Record>(context, resource, items) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)
            val item = items[position]
            val textView = view.findViewById<TextView>(R.id.autoCompleteItem)
            val projectName = view.findViewById<TextView>(R.id.projectName)
            textView.text = item?.name ?: ""
            projectName.text = item?.projectR?.name ?: ""

            val itemContainer = view.findViewById<LinearLayout>(R.id.ll_spinner)
            if (position % 2 == 0) {
                // Set alternate background color for even-positioned items
                itemContainer.setBackgroundColor(ContextCompat.getColor(context, R.color.list_even_color))
            } else {
                // Set alternate background color for odd-positioned items
                itemContainer.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
            }

            return view
        }
    }

}