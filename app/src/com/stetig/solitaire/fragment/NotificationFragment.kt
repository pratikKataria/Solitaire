package com.stetig.solitaire.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableBoolean
import androidx.recyclerview.widget.LinearLayoutManager
import com.stetig.callsync.base.BaseFragment
import com.stetig.solitaire.R
import com.stetig.solitaire.activity.MainActivity
import com.stetig.solitaire.adapter.NotificationRecyclerAdapter
import com.stetig.solitaire.api.CommonClassForApi
import com.stetig.solitaire.api.Keys
import com.stetig.solitaire.api.RestClient
import com.stetig.solitaire.data.MarkAsReadModel
import com.stetig.solitaire.data.MarkAsReadResponse
import com.stetig.solitaire.data.NotificationReqModel
import com.stetig.solitaire.data.ServerNotification
import com.stetig.solitaire.databinding.FragmentNotificationBinding
import com.stetig.solitaire.utils.NotificationPrefs
import com.stetig.solitaire.utils.SharedPrefs
import com.stetig.solitaire.utils.UpcommingNotificationPrefs
import com.stetig.solitaire.utils.Utils
import com.google.gson.Gson
import com.salesforce.androidsdk.app.SalesforceSDKManager
import io.reactivex.observers.DisposableObserver
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class NotificationFragment : BaseFragment() {

    lateinit var fragmentProjectBinding: FragmentNotificationBinding
    private var recyclerAdapter: NotificationRecyclerAdapter? = null
    private val isEmpty = ObservableBoolean(false)
    private val isLoading = ObservableBoolean(true)
    lateinit var activity: MainActivity
    private var commonClassForApi: CommonClassForApi? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentProjectBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_notification, container, false)
        SharedPrefs.saveData(context, SharedPrefs.IS_ACTIVE, false)
        initView(fragmentProjectBinding.root)
        return fragmentProjectBinding.root
    }


    override fun onResume() {
        super.onResume()
        setHeader()
        updateNotification()
    }

    override fun initView(rootView: View?) {
        commonClassForApi = CommonClassForApi.getInstance(activity)
        initRecyclerView()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = if (context is MainActivity) context else getActivity() as MainActivity
    }

    fun updateNotification() {
        tasks.clear()
        val notifications: HashMap<String, String> = NotificationPrefs.getAllData(activity)
        if (notifications != null) {
            for (key in notifications.keys) {
                Log.e(javaClass.name, "onCreate: 59 $key")
                tasks.add(0, Gson().fromJson(notifications[key], ServerNotification.Notificationlist::class.java))
            }
            recyclerAdapter?.notifyDataSetChanged()
        }
        getNotificationList()
    }


    var tasks: ArrayList<ServerNotification.Notificationlist> = ArrayList<ServerNotification.Notificationlist>()

    fun initRecyclerView() {
        fragmentProjectBinding.recyclerView.setLayoutManager(LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false))
        recyclerAdapter = object : NotificationRecyclerAdapter(activity, tasks) {
            override fun _markAsCompleted(nId: String?, oId: String?, type: String) {
                if (nId == null) {
                    Utils.setToast(activity, "No Notification Id found")
                    return
                }
                if (type.equals("Reminder", ignoreCase = true)) {
                    readReamin(nId, oId)
                    return
                }
                read(nId, oId)
            }
        }
        fragmentProjectBinding.recyclerView.setAdapter(recyclerAdapter)
    }

    private fun setHeader() {
        fragmentProjectBinding.tvType.setText("Notifications")
    }

    private fun getNotificationList() {
        try {
            val userAccount = SalesforceSDKManager.getInstance().userAccountManager.currentUser
            if (userAccount != null) {
                val userEmail = userAccount.username
                val authToken = "Bearer " + userAccount.authToken
                commonClassForApi?.getNotificationList(disposableObserver, NotificationReqModel(userEmail), authToken)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private var disposableObserver: DisposableObserver<ServerNotification> = object : DisposableObserver<ServerNotification>() {
        override fun onNext(callStatusResponse: ServerNotification) {
            tasks.addAll(callStatusResponse.notificationlist)
            recyclerAdapter?.notifyDataSetChanged()
        }

        override fun onError(e: Throwable) {
            Log.e(javaClass.name, "onError: 337 " + e.message)
        }

        override fun onComplete() {}
    }


    fun onResume(client: RestClient?) {}

    fun readReamin(nId: String?, oId: String?) {
        if (oId == null) {
            Utils.setToast(activity, "No Opportunity Id found")
            return
        }
        Log.e("TAG", "readReamin: $nId")
        NotificationPrefs.removeData(context, nId)
        UpcommingNotificationPrefs.removeData(context, nId)
        val bundle = Bundle()
        bundle.putString(Keys.OPP_ID, oId)
        activity.navHostFragment.navController.navigate(R.id.action_notificationFragment_to_opportunityDetailFragment, bundle)
    }

    fun read(nId: String?, oId: String?) {
        try {
            val userAccount = SalesforceSDKManager.getInstance().userAccountManager.currentUser
            if (userAccount != null) {
                val userEmail = userAccount.username
                val authToken = "Bearer " + userAccount.authToken
                val stringCall: Call<MarkAsReadResponse> = RestClient.getInstance().service.markAsRead(MarkAsReadModel("true", nId), authToken)
                stringCall.enqueue(object : Callback<MarkAsReadResponse?> {
                    override fun onResponse(call: Call<MarkAsReadResponse?>, response: Response<MarkAsReadResponse?>) {
                        val readResponse: MarkAsReadResponse? = response.body()
                        if (oId == null) {
                            updateNotification()
                            Utils.setToast(context, "No Opportunity Id found")
                            return
                        }
//
                        val bundle = Bundle()
                        bundle.putString(Keys.OPP_ID, oId)
                        (context as MainActivity).navHostFragment.navController.navigate(R.id.action_notificationFragment_to_opportunityDetailFragment, bundle)
//
                    }

                    override fun onFailure(call: Call<MarkAsReadResponse?>, t: Throwable) {
                    }
                })
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}