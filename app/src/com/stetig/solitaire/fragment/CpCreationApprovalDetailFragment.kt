


package com.stetig.solitaire.fragment
import ApprovalRecyclerAdapter
import CampaignApprovalRecyclerAdapter
import CpCreationRecyclerAdapter
import SourceChangeRecyclerAdapter
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
import com.stetig.solitaire.api.CommonClassForQuery
import com.stetig.solitaire.api.Keys
import com.stetig.solitaire.api.Query
import com.stetig.solitaire.data.CpCreationApproval
import com.stetig.solitaire.data.SourceChangeApproval
import com.stetig.solitaire.databinding.FragmentCpCreationApprovalBinding
import com.stetig.solitaire.utils.Utils
import org.acra.ACRA.log

class CpCreationApprovalDetailFragment : BaseFragment() {
    private lateinit var binding: FragmentCpCreationApprovalBinding
    private lateinit var activity: MainActivity
    private lateinit var commonClassForQuery: CommonClassForQuery
    private lateinit var adapter: CpCreationRecyclerAdapter
    private var projectList = mutableListOf<CpCreationApproval.Record>()



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_cp_creation_approval, container, false)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        initView(rootView = binding.root)
        return binding.root
    }

    override fun initView(rootView: View?) {
        initRecycler()
    }

    private fun initRecycler() {
        adapter = CpCreationRecyclerAdapter(activity, projectList)
        binding.cpApprovalDetailRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.cpApprovalDetailRecyclerView.adapter = adapter
    }


    @SuppressLint("UseRequireInsteadOfGet")
    override fun onResume() {
        super.onResume()
        commonClassForQuery = CommonClassForQuery.getInstance(activity, activity.getRestClient())!!
        if (arguments != null) {
            val type = arguments?.getString(Keys.APP_TYPE, "not found")
            binding.title.text = type
            log.e("idcheck", type.toString())
            val query = when {
                type.equals("CP CREATION APPROVAL") -> {
                    Query.CP_CREATION_APPROVAL_LIST
                }
                type.equals(getString(R.string.action_opportunity_for_today)) -> {
                    Query.TODAY_OPPORTUNITY
                }
                else -> {
                    Query.OPPORTUNITY_LIST + Utils.buildQueryParameter(type)

                }
            }
            Log.e("", "getCpCreationApproval: ${Query.CP_CREATION_APPROVAL_LIST}")
            commonClassForQuery.getCpCreationApprovalWithoutLoader(query, cpcreationapprovalListListener)

        }
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