package com.stetig.solitaire.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.stetig.callsync.base.BaseFragment
import com.stetig.solitaire.`interface`.OnSortingItemSelectedListener
import com.stetig.solitaire.activity.MainActivity
import com.stetig.solitaire.adapter.TaskRecyclerAdapter
import com.stetig.solitaire.api.CommonClassForApi
import com.stetig.solitaire.api.CommonClassForQuery
import com.stetig.solitaire.api.Keys
import com.stetig.solitaire.api.Query
import com.stetig.solitaire.data.MarkAsCompleteReq
import com.stetig.solitaire.data.MarkAsCompleteRes
import com.stetig.solitaire.data.Task
import com.stetig.solitaire.databinding.FragmentPageBinding
import com.stetig.solitaire.utils.Utils
import com.salesforce.androidsdk.app.SalesforceSDKManager
import io.reactivex.observers.DisposableObserver
import org.acra.ACRA.log

private var currentOrder = Query.ORDER_BY_CREATED_DATE

class PageFragment : BaseFragment(), OnSortingItemSelectedListener {
    private var param1: Boolean = false
    private var param2: String? = null
    private lateinit var activity: MainActivity
    private lateinit var binding: FragmentPageBinding
    private var projectList = mutableListOf<Task.Record>()
    private lateinit var adapter: TaskRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getBoolean(Keys.IS_TODAY)
            param2 = it.getString(Keys.TASK_TYPE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPageBinding.inflate(inflater, container, false)
        initView(binding.root)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        Log.e(javaClass.name, "onResume: $currentOrder")
        getDataFromSF(currentOrder)
    }

    fun getDataFromSF(orderBy: String) {
        val commonClassForQuery =
            CommonClassForQuery.getInstance(activity, activity?.getRestClient())!!
        val query = if (param1 && param2.toString() == "Proposed site visit") {
            "${Query.PROPOSED_SITE_VISIT_LIST_TODAY} $orderBy"
        } else if (!param1 && param2.toString() == "Proposed site visit") {
            "${Query.PROPOSED_SITE_VISIT_LIST} $orderBy"
        } else if (param1 && param2.toString() == "Follow up") {
            "${Query.FOLLOW_UP_TODAY} $orderBy"
        } else if (!param1 && param2.toString() == "Follow up") {
            "${Query.FOLLOW_UP_LIST} $orderBy"
        } else if (param1 && param2.toString() == "Aloted site visit") {
            "${Query.ALOTED_SITE_VISIT_LIST_TODAY} $orderBy"
        } else if (!param1 && param2.toString() == "Aloted site visit") {
            "${Query.ALOTED_SITE_VISIT_LIST} $orderBy"
        } else {
            "Something went wrong!"
        }
        commonClassForQuery.getTask(query, onDataCommonClassForQuery)

    }

    private fun initRecycler() {
        adapter = object : TaskRecyclerAdapter(context, projectList) {
            override fun completeTask(id: String) {
                val userAccount = SalesforceSDKManager.getInstance().userAccountManager.currentUser ?: return
                val commonClassForApi: CommonClassForApi = CommonClassForApi.getInstance(activity)!!
                val auth = "Bearer " + userAccount.authToken
                commonClassForApi.markAsComplete(
                    disposableObserver,
                    MarkAsCompleteReq(userAccount.username, id),
                    auth
                )
            }
        }
        binding.recyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter
    }

    private var disposableObserver: DisposableObserver<MarkAsCompleteRes> =
        object : DisposableObserver<MarkAsCompleteRes>() {
            override fun onNext(callStatusResponse: MarkAsCompleteRes) {
                getDataFromSF(currentOrder)
            }

            override fun onError(e: Throwable) {
                Toast.makeText(context, "An unexpected error has occurred", Toast.LENGTH_SHORT)
                    .show()
                Log.e(javaClass.name, "onError: 337 " + e.message)
            }

            override fun onComplete() {}
        }


    private val onDataCommonClassForQuery = object : CommonClassForQuery.OnDataReceiveListener {
        override fun onDataReceive(data: Any) {
            if (data is Task) {
                projectList.clear()
                data?.records?.let { projectList.addAll(it) }
                adapter.notifyDataSetChanged()
//                activity.checkListIsEmpty(projectList)
            }
        }

        override fun onError(obj: String) {}

    }

    companion object {
        @JvmStatic
        fun newInstance(isToday: Boolean, taskType: String) =
            PageFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(Keys.IS_TODAY, isToday)
                    putString(Keys.TASK_TYPE, taskType)
                }
            }
    }

    override fun initView(rootView: View?) {
        initRecycler()
    }

    override fun onAttach(context: Context) {
        if (context is MainActivity) activity = context
        super.onAttach(context)
    }

    override fun onItemSelected(orderBY: String) {
        currentOrder = orderBY
        onResume()
    }
}