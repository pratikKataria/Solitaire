package com.stetig.solitaire.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.stetig.callsync.base.BaseFragment
import com.stetig.solitaire.R
import com.stetig.solitaire.activity.MainActivity
import com.stetig.solitaire.api.CommonClassForQuery
import com.stetig.solitaire.api.Query
import com.stetig.solitaire.data.AccountDetail
import com.stetig.solitaire.databinding.FragmentAccountBinding
import com.stetig.solitaire.utils.Utils
import com.salesforce.androidsdk.app.SalesforceSDKManager
import android.content.pm.PackageManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.pm.PackageInfoCompat
class AccountFragment : BaseFragment() {

    lateinit var activity: MainActivity
    lateinit var binding: FragmentAccountBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false)
        initView(binding.root)
        return binding.root
    }

    override fun initView(rootView: View?) {

        binding.linearLayoutLogout.setOnClickListener {
            MaterialAlertDialogBuilder(activity, R.style.AlertDialogTheme).apply {
                setTitle("Logout")
                setMessage("Would you like to logout?")
                setPositiveButton("yes") { _: DialogInterface?, _: Int -> SalesforceSDKManager.getInstance().logout(activity) }
                setNegativeButton("no") { _: DialogInterface?, _: Int -> }
            }.show()
        }
    }

    override fun onResume() {
        super.onResume()

        try {
            val commonClassForQuery = CommonClassForQuery.getInstance(activity, activity.getRestClient())!!
            commonClassForQuery.getAccountDetails(Query.USER_ACCOUNT, onDataReceiveListener)
        } catch (e: Exception) {
        }
    }

    private var onDataReceiveListener = object : CommonClassForQuery.OnDataReceiveListener {
        @SuppressLint("SetTextI18n")
        override fun onDataReceive(data: Any) {
            val packageInfo = packageManager.getPackageInfo(packageName, 0)
            val versionName = packageInfo.versionName
            val versionCode = PackageInfoCompat.getLongVersionCode(packageInfo)
            if (data is AccountDetail && data.records.isNotEmpty()) {
                binding.accountUserFullName.text = Utils.checkValueOrGiveEmptySpace(data.records[0].firstName) + Utils.checkValueOrGiveEmptySpace(data.records[0].lastName)
                binding.accountMobileNum.text = Utils.checkValueOrGiveDef(data.records[0].phone)
                binding.accountEmailId.text = Utils.checkValueOrGiveDef(data.records[0].email)
                binding.appVersionText.text = "App Version: $versionName ($versionCode)"
            }
        }

        override fun onError(obj: String) {
            Log.e(javaClass.name, "onError: $obj")
        }

    }

    override fun onAttach(context: Context) {
        if (context is MainActivity) activity = context
        super.onAttach(context)
    }
}