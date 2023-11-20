package com.stetig.solitaire.fragment;

import CCRRecyclerAdapter
import CpCreationRecyclerAdapter
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.MaterialSharedAxis
import com.salesforce.androidsdk.app.SalesforceSDKManager
import com.stetig.callsync.base.BaseFragment
import com.stetig.solitaire.R
import com.stetig.solitaire.activity.MainActivity
import com.stetig.solitaire.api.CommonClassForApi
import com.stetig.solitaire.api.CommonClassForQuery
import com.stetig.solitaire.api.Keys
import com.stetig.solitaire.api.Query
import com.stetig.solitaire.data.CpCreationApproval
import com.stetig.solitaire.data.CpCreationApprovalResponse
import com.stetig.solitaire.data.SendCpCreaitonApprovalRequest
import com.stetig.solitaire.databinding.FragmentCCRApprovalRequestBinding
import com.stetig.solitaire.databinding.FragmentCPCApprovalBinding
import com.stetig.solitaire.databinding.FragmentCpCreationApprovalBinding
import com.stetig.solitaire.databinding.FragmentCpCreationApprovalSubdetailBinding
import com.stetig.solitaire.utils.Utils
import io.reactivex.observers.DisposableObserver
import org.acra.ACRA


public class CCRApprovalRequestFragment : BaseFragment() {
    private lateinit var binding: FragmentCCRApprovalRequestBinding
    private lateinit var activity: MainActivity
    private lateinit var commonClassForQuery: CommonClassForQuery
    private lateinit var adapter: CCRRecyclerAdapter
    private var projectList = mutableListOf<CpCreationApproval.Record>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_c_c_r_approval_request, container, false)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        initView(rootView = binding.root)
        return binding.root
    }

    override fun initView(rootView: View?) {
        initRecycler()
    }

    private fun initRecycler() {
        adapter = CCRRecyclerAdapter(activity, projectList)
        binding.cpApprovalDetailRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.cpApprovalDetailRecyclerView.adapter = adapter
    }


    @SuppressLint("UseRequireInsteadOfGet")
    override fun onResume() {
        super.onResume()
        commonClassForQuery = CommonClassForQuery.getInstance(activity, activity.getRestClient())!!
        commonClassForQuery.getCpCreationApprovalWithoutLoader(Query.CCR_APPROVAL_LIST, cpcreationapprovalListListener)
    }

    private val cpcreationapprovalListListener = object : CommonClassForQuery.OnDataReceiveListener {
        override fun onDataReceive(data: Any) {
            if (data is CpCreationApproval) {
                projectList.clear()
                projectList.addAll(data.records)
                adapter.notifyDataSetChanged()
                activity.checkListIsEmpty(data.records)
            }
        }

        override fun onError(obj: String) {}


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = if (context is MainActivity) context else getActivity() as MainActivity
    }
}