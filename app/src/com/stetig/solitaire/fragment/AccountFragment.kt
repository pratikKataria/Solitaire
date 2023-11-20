package com.stetig.solitaire.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.salesforce.androidsdk.app.SalesforceSDKManager
import com.stetig.callsync.base.BaseFragment
import com.stetig.solitaire.R
import com.stetig.solitaire.activity.MainActivity
import com.stetig.solitaire.api.CommonClassForApi
import com.stetig.solitaire.api.CommonClassForQuery
import com.stetig.solitaire.api.Query
import com.stetig.solitaire.data.AccountDetail
import com.stetig.solitaire.data.CreateTaskFromCallResponse
import com.stetig.solitaire.data.SMStatusRequest
import com.stetig.solitaire.data.SMStatusResponse
import com.stetig.solitaire.databinding.FragmentAccountBinding
import com.stetig.solitaire.utils.Utils
import com.stetig.solitaire.utils.Utils.showToast
import io.reactivex.observers.DisposableObserver


class AccountFragment : BaseFragment() {

    lateinit var activity: MainActivity
    lateinit var binding: FragmentAccountBinding
    private var commonClassForApi: CommonClassForApi? = null
    private var commonClassForQuery: CommonClassForQuery? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false)
        initView(binding.root)
        return binding.root
    }

    override fun initView(rootView: View?) {
        commonClassForApi = CommonClassForApi.getInstance(activity)

        binding.linearLayoutLogout.setOnClickListener {
            MaterialAlertDialogBuilder(activity, R.style.AlertDialogTheme).apply {
                setTitle("Logout")
                setMessage("Would you like to logout?")
                setPositiveButton("yes") { _: DialogInterface?, _: Int -> SalesforceSDKManager.getInstance().logout(activity) }
                setNegativeButton("no") { _: DialogInterface?, _: Int -> }
            }.show()
        }

        binding.toggleSwitch.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            // Handle the state change (checked or unchecked) of the Switch
            val account = SalesforceSDKManager.getInstance().userAccountManager.currentUser
            val auth = "Bearer " + account.authToken
            val body = SMStatusRequest(account.userId, if (isChecked) "Active" else "Inactive")

            commonClassForApi?.updateSalesManagerStatus(disposableObserverCallTaskObserver, body, auth)

        })
    }

    override fun onResume() {
        super.onResume()

        try {
            commonClassForQuery = CommonClassForQuery.getInstance(activity, activity.getRestClient())!!
            commonClassForQuery?.getAccountDetails(Query.USER_ACCOUNT, onDataReceiveListener)
        } catch (e: Exception) {
        }
    }

    private var onDataReceiveListener = object : CommonClassForQuery.OnDataReceiveListener {
        @SuppressLint("SetTextI18n")
        override fun onDataReceive(data: Any) {
            if (data is AccountDetail && data.records.isNotEmpty()) {
                binding.accountUserFullName.text = Utils.checkValueOrGiveEmptySpace(data.records[0].firstName) + Utils.checkValueOrGiveEmptySpace(data.records[0].lastName)
                binding.accountMobileNum.text = Utils.checkValueOrGiveDef(data.records[0].phone)
                binding.accountEmailId.text = Utils.checkValueOrGiveDef(data.records[0].email)
                binding.activeText.text = Utils.checkValueOrGiveDef(data.records[0].smActiveStatus)


                binding.toggleSwitch.visibility = if (data.records[0].smActiveStatus != null && data.records[0].smActiveStatus == "In Progress") View.GONE else View.VISIBLE


                if (data.records[0].smActiveStatus != null && data.records[0].smActiveStatus == "Active") {
                    binding.toggleSwitch.isChecked = true
                }

                if (data.records[0].smActiveStatus != null && data.records[0].smActiveStatus == "Active") {
                    binding.iconActive.setImageDrawable(getResources().getDrawable(R.drawable.ic_active))
                    binding.iconActive.colorFilter = null;
                } else {
                    binding.iconActive.setImageDrawable(getResources().getDrawable(R.drawable.ic_inactive))
                    binding.iconActive.setColorFilter(ContextCompat.getColor(activity, R.color.light_grey));
                }
            }
        }

        override fun onError(obj: String) {
            Log.e(javaClass.name, "onError: $obj")
        }

    }

    private var disposableObserverCallTaskObserver: DisposableObserver<SMStatusResponse> =
        object : DisposableObserver<SMStatusResponse>() {
            override fun onNext(callStatusResponse: SMStatusResponse) {
                commonClassForQuery?.getAccountDetails(Query.USER_ACCOUNT, onDataReceiveListener)
            }

            override fun onError(e: Throwable) {
                Log.e(javaClass.name, "onError: 337 " + e.message)
            }

            override fun onComplete() {}
        }

    override fun onAttach(context: Context) {
        if (context is MainActivity) activity = context
        super.onAttach(context)
    }

}