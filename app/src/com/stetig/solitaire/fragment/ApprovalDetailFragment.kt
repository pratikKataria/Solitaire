package com.stetig.solitaire.fragment
import ApprovalRecyclerAdapter
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.MaterialSharedAxis
import com.stetig.callsync.base.BaseFragment
import com.stetig.solitaire.R
import com.stetig.solitaire.activity.MainActivity
import com.stetig.solitaire.adapter.OpportunityRecyclerAdapter
import com.stetig.solitaire.api.CommonClassForQuery
import com.stetig.solitaire.api.Keys
import com.stetig.solitaire.api.Query
import com.stetig.solitaire.data.Approval
import com.stetig.solitaire.databinding.FragmentApprovalDetailBinding
import com.stetig.solitaire.utils.Utils

class ApprovalDetailFragment : BaseFragment() {
    private lateinit var binding: FragmentApprovalDetailBinding
    private lateinit var activity: MainActivity
    private lateinit var commonClassForQuery: CommonClassForQuery
    private lateinit var adapter: ApprovalRecyclerAdapter
    private var projectList = mutableListOf<Approval.Record>()



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_approval_detail, container, false)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        initView(rootView = binding.root)
        return binding.root
    }

    override fun initView(rootView: View?) {
        initRecycler()
    }

    private fun initRecycler() {
        adapter = ApprovalRecyclerAdapter(activity, projectList)
        binding.approvalDetailRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.approvalDetailRecyclerView.adapter = adapter
    }


    @SuppressLint("UseRequireInsteadOfGet")
    override fun onResume() {
        super.onResume()
        commonClassForQuery = CommonClassForQuery.getInstance(activity, activity.getRestClient())!!
        if (arguments != null) {
            val type = arguments?.getString(Keys.APP_TYPE, "not found")
            binding.title.text = type

            val query = when {
                type.equals(getString(R.string.approvalpending)) -> {
                    Query.APPROVAL_LIST
                }
                type.equals(getString(R.string.action_opportunity_for_today)) -> {
                    Query.TODAY_OPPORTUNITY
                }
                else -> {
                    Query.OPPORTUNITY_LIST + Utils.buildQueryParameter(type)
                }
            }
            Log.e("", "onResume: ${Query.APPROVAL_LIST}")
            commonClassForQuery.getApproval(query, approvalListListener)

        }
    }

    private val approvalListListener = object : CommonClassForQuery.OnDataReceiveListener {
        override fun onDataReceive(data: Any) {
            if (data is Approval) {
                projectList.clear()

                data.records?.forEach {
                    if (it?.costSheets1R != null || it?.paymentPlansR != null) {
                        projectList.add(it)
                        activity.checkListIsEmpty(projectList)
                    }
                }
                activity.checkListIsEmpty(projectList)
                adapter.notifyDataSetChanged()

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