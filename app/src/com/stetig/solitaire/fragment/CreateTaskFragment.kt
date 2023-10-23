package com.stetig.solitaire.fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.DatePicker
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.MaterialSharedAxis
import com.salesforce.androidsdk.app.SalesforceSDKManager
import com.stetig.callsync.base.BaseFragment
import com.stetig.solitaire.R
import com.stetig.solitaire.activity.MainActivity
import com.stetig.solitaire.adapter.TaskListRecyclerAdapyer
import com.stetig.solitaire.api.CommonClassForApi
import com.stetig.solitaire.api.CommonClassForQuery
import com.stetig.solitaire.api.Query
import com.stetig.solitaire.api.Query.Companion.TASK_LIST
import com.stetig.solitaire.data.AllOpportunityDto
import com.stetig.solitaire.data.AllOptyRequest
import com.stetig.solitaire.data.CreateTask
import com.stetig.solitaire.data.CreateTaskFromCallResponse
import com.stetig.solitaire.data.CreateTaskResponse
import com.stetig.solitaire.data.ManualTaskListResponse
import com.stetig.solitaire.data.SolitaireCreateTask
import com.stetig.solitaire.databinding.FragmentCreateTaskBinding
import com.stetig.solitaire.utils.Utils
import io.reactivex.observers.DisposableObserver
import org.acra.ACRA.log
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class CreateTaskFragment : BaseFragment() {
    private lateinit var binding: FragmentCreateTaskBinding
    private lateinit var activity: MainActivity
    private lateinit var commonClassForQuery: CommonClassForQuery
    private lateinit var adapter: TaskListRecyclerAdapyer
    private var projectList = mutableListOf<ManualTaskListResponse.Record?>()
    private var commonClassForApi: CommonClassForApi? = null
    private var listOfOpportunities: ArrayList<AllOpportunityDto.AllOpportunityDtoItem> =
        arrayListOf()
    private var createTaskRequest: CreateTask = CreateTask()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_create_task, container, false)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        setupFloatingActionButton()
//
//        val fab: FloatingActionButton = view?.findViewById(R.id.floating_action_button)!!
//        fab.setOnClickListener {
//            showPopupForm()
//        }
        initView(rootView = binding.root)
        return binding.root
    }

    override fun initView(rootView: View?) {
        commonClassForApi = CommonClassForApi.getInstance(activity)
        val userAccount =
            SalesforceSDKManager.getInstance().userAccountManager.currentUser ?: return
        val auth = "Bearer " + userAccount.authToken
        commonClassForApi?.getAllOpportunities(disposableObserver, auth)
        initRecycler()
    }

    private var disposableObserver: DisposableObserver<AllOpportunityDto> =
        object : DisposableObserver<AllOpportunityDto>() {
            override fun onNext(callStatusResponse: AllOpportunityDto) {
                listOfOpportunities.clear()
                listOfOpportunities.addAll(callStatusResponse)
            }

            override fun onError(e: Throwable) {
                Log.e(javaClass.name, "onError: 337 " + e.message)
            }

            override fun onComplete() {}
        }


    private fun setupFloatingActionButton() {
        binding.floatingActionButton.setOnClickListener {

            val builder = AlertDialog.Builder(this.activity)
            builder.setTitle("Add Task")
            val inflater = layoutInflater
            val dialogView: View =
                inflater.inflate(com.stetig.solitaire.R.layout.create_task_form, null)

            val editText =
                dialogView.findViewById(com.stetig.solitaire.R.id.task_type_c) as EditText
//            createTaskRequest.tasktype = editText.text.toString()
            editText.addTextChangedListener { text: Editable? ->
                createTaskRequest.tasktype = text.toString()
            }

            val editTextSubject =
                dialogView.findViewById(com.stetig.solitaire.R.id.subject_c) as EditText
//            createTaskRequest.subject = editTextSubject.text.toString()
            editTextSubject.addTextChangedListener { text: Editable? ->
                createTaskRequest.subject = text.toString()
            }
            val datePickerEditText = dialogView.findViewById(com.stetig.solitaire.R.id.due_date_c) as EditText
            datePickerEditText.setOnClickListener {
                showDatePickerDialog()
            }

            val adapter = ArrayAdapter(
                activity,
                android.R.layout.simple_spinner_item,
                listOfOpportunities.map { it.oPPname })
            val autoCompleteTextView = dialogView.findViewById(com.stetig.solitaire.R.id.autoCompleteTextView) as AutoCompleteTextView
            autoCompleteTextView.threshold = 3
            autoCompleteTextView.setAdapter(adapter)
            autoCompleteTextView.onItemClickListener =
                AdapterView.OnItemClickListener { parent, view_, position, _ ->
                    val item = listOfOpportunities.get(position)
                    createTaskRequest.opp_Id = item.oppId;
                }

            builder.setView(dialogView)
            builder.setPositiveButton("Submit") { dialog, which ->

//                val userAccount = SalesforceSDKManager.getInstance().userAccountManager.currentUser
//                val commonClassForApi: CommonClassForApi = CommonClassForApi.getInstance(activity)!!
//                val auth = "Bearer " + userAccount.authToken
//                val data = CreateTask(
//                    tasktype = data.records[0]?.id,
//                    subject = " ",
//                    duedate = "Approve",
//                    opp_Id = "Cost sheet",
//                )
//                commonClassForApi.ApprovalRequest(disposableObserver,data,auth)
                val account = SalesforceSDKManager.getInstance().userAccountManager.currentUser
                commonClassForApi?.createTask(
                    disposableObserverTaskFromCallResponse,
                    createTaskRequest,
                    "Bearer " + account.authToken
                )
            }
            builder.setNegativeButton("Cancel") { dialog, which ->
                dialog.cancel()
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()

        }
    }

    private var disposableObserverTaskFromCallResponse: DisposableObserver<CreateTaskResponse> =
        object : DisposableObserver<CreateTaskResponse>() {
            override fun onNext(callStatusResponse: CreateTaskResponse) {
                Utils.showToast(requireContext(), "Task Created")
                commonClassForQuery.getAllManualTasks(
                    Query.ALL_MANUAL_TASK,
                    onOpportunityListListener
                )
            }

            override fun onError(e: Throwable) {
                Log.e(javaClass.name, "onError: 337 " + e.message)
            }

            override fun onComplete() {}
        }


    private fun showDatePickerDialog() {
        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val day = currentDate.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { view: DatePicker, selectedYear: Int, monthOfYear: Int, dayOfMonth: Int ->
                val selectedDate =
                    "$selectedYear-${(monthOfYear + 1).toString().padStart(2, '0')}-" +
                            "${dayOfMonth.toString().padStart(2, '0')}"

                // Get the current date in the "yyyy-MM-dd" format
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                val currentDateFormatted = sdf.format(currentDate.time)

                // Check if the selected date is not a past date
                if (selectedDate >= currentDateFormatted) {
                    createTaskRequest.duedate = selectedDate
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

    private fun initRecycler() {
        adapter = TaskListRecyclerAdapyer(activity, projectList)
        binding.recyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onResume() {
        super.onResume()
        commonClassForQuery = CommonClassForQuery.getInstance(activity, activity.getRestClient())!!
        commonClassForQuery.getAllManualTasks(Query.ALL_MANUAL_TASK, onOpportunityListListener)
    }

    private val onOpportunityListListener = object : CommonClassForQuery.OnDataReceiveListener {
        override fun onDataReceive(data: Any) {
            if (data is ManualTaskListResponse) {
                projectList.clear()
                projectList.addAll(data.records)
                adapter.notifyDataSetChanged()
                activity.checkListIsEmpty(data.records)
            }
        }

        override fun onError(obj: String) {

        }


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = if (context is MainActivity) context else getActivity() as MainActivity
    }
}