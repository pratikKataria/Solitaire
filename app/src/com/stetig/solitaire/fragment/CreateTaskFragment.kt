package com.stetig.solitaire.fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.DatePicker
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
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
import com.stetig.solitaire.data.AllOpportunityDto
import com.stetig.solitaire.data.CreateTask
import com.stetig.solitaire.data.CreateTaskResponse
import com.stetig.solitaire.data.ManualTaskListResponse
import com.stetig.solitaire.data.OpportunityByMobileNumberResponse
import com.stetig.solitaire.databinding.FragmentCreateTaskBinding
import com.stetig.solitaire.utils.Utils
import io.reactivex.observers.DisposableObserver
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
            val dialogView: View = inflater.inflate(com.stetig.solitaire.R.layout.create_task_form, null)

            val listOfTaskTypes = arrayOf("Follow Up", "Visit Proposed")
            val taskTypeAutoCompleteView = dialogView.findViewById(R.id.task_dropdown) as AutoCompleteTextView
            val taskTypeAutoCompleteAdapter = ArrayAdapter(
                activity, R.layout._layout_spinner_item, listOfTaskTypes
            )
            taskTypeAutoCompleteView.threshold = 3
            taskTypeAutoCompleteView.setAdapter(taskTypeAutoCompleteAdapter)
            taskTypeAutoCompleteView.onItemClickListener =
                AdapterView.OnItemClickListener { parent, view_, position, _ ->
                    createTaskRequest.tasktype = listOfTaskTypes[position]
                }


            val editTextSubject = dialogView.findViewById(com.stetig.solitaire.R.id.subject_c) as EditText
//            createTaskRequest.subject = editTextSubject.text.toString()
            editTextSubject.addTextChangedListener { text: Editable? ->
                createTaskRequest.subject = text.toString()
            }
            val datePickerEditText = dialogView.findViewById(com.stetig.solitaire.R.id.due_date_c) as EditText
            datePickerEditText.setOnClickListener {
                showDatePickerDialog(datePickerEditText)
            }


//            val adapter = ArrayAdapter(activity, R.layout._layout_spinner, listOfOpportunities.map { it.oPPname })
            val customAdapter = CustomAdapter(activity, R.layout._layout_spinner, listOfOpportunities)
            val autoCompleteTextView = dialogView.findViewById(com.stetig.solitaire.R.id.autoCompleteTextView) as AutoCompleteTextView
            autoCompleteTextView.threshold = 3
            autoCompleteTextView.setAdapter(customAdapter)
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


    private fun showDatePickerDialog(datePickerEditText: EditText) {
        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val day = currentDate.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { view: DatePicker, selectedYear: Int, monthOfYear: Int, dayOfMonth: Int ->
                val selectedDate = "$selectedYear-" + "${(monthOfYear + 1).toString().padStart(2, '0')}-" + "${dayOfMonth.toString().padStart(2, '0')}"
                val displayTime = "${dayOfMonth.toString().padStart(2, '0')}-" + "${(monthOfYear + 1).toString().padStart(2, '0')}-" + "$selectedYear"

                // Get the current date in the "yyyy-MM-dd" format
                val sdf = SimpleDateFormat("YYYY-MM-dd", Locale.ENGLISH)
                val currentDateFormatted = sdf.format(currentDate.time)

                // Check if the selected date is not a past date
                if (selectedDate >= currentDateFormatted) {


                    val calendar = Calendar.getInstance()
                    val hour = calendar.get(Calendar.HOUR_OF_DAY)
                    val minute = calendar.get(Calendar.MINUTE)

                    // Create a TimePickerDialog and show it
                    val timePickerDialog = TimePickerDialog(
                        activity,
                        { view, hourOfDay, minute ->

                            var time: String = hourOfDay.toString() + ":" + minute
                            print("Time selected$time")
                            // date.setText(convertTo12HourFormat(time))
                            // callTaskRequest.calltime = "$hourOfDay:$minute:00"
                            createTaskRequest.duedate = selectedDate
                            createTaskRequest.time = "$time:00"
                            datePickerEditText.setText("$displayTime ${convertTo12HourFormat(time)}")
                        },
                        hour,
                        minute,
                        false
                    )

                    timePickerDialog.show()

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


    var customTimePicker =
        DatePickerDialog.OnDateSetListener { datePicker: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->
            datePicker?.minDate = System.currentTimeMillis() - 1000;
            val calendar = Calendar.getInstance()
            val timePicker = TimePickerDialog(
                activity, R.style.MyDatePickerDialogTheme, { timePicker, i, i1 ->
                    val calendar = Calendar.getInstance()
                    calendar[year, month, dayOfMonth, i] = i1
//                    buildSendStatus(calendar.time, "Follow Up", _manual_task, opportunityId)
                }, calendar[Calendar.HOUR], calendar[Calendar.MINUTE], false
            )
            timePicker.show()
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

    class CustomAdapter(context: Context, private val resource: Int, private val items: List<AllOpportunityDto.AllOpportunityDtoItem>) :
        ArrayAdapter<AllOpportunityDto.AllOpportunityDtoItem>(context, resource, items) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)
            val item = items[position]
            val textView = view.findViewById<TextView>(R.id.autoCompleteItem)
            val projectName = view.findViewById<TextView>(R.id.projectName)
            textView.text = item.oPPname
            projectName.text = item.projectName

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

