
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stetig.solitaire.R
import com.stetig.solitaire.activity.MainActivity
import com.stetig.solitaire.api.Keys
import com.stetig.solitaire.data.Approval
import com.stetig.solitaire.databinding.CardViewApprovalBinding
import com.stetig.solitaire.utils.Utils
import org.acra.ACRA.log

class ApprovalRecyclerAdapter(private var context: Context, private var projectList: List<Approval.Record>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val cardViewApprovalBinding = CardViewApprovalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ApprovalDetailCardViewHolder(cardViewApprovalBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val approvalDetailCardViewHolder = holder as ApprovalDetailCardViewHolder
        val data = projectList[position]
//        log.e("name", data.opportunityName.name)
        approvalDetailCardViewHolder.cardViewApprovalBinding.approvalOpportunityName.text = Utils.checkValueOrGiveEmpty(
            data?.opportunityName?.name?:""
        )
        approvalDetailCardViewHolder.cardViewApprovalBinding.approvalOpportunity.text = Utils.checkValueOrGiveEmpty(
            data?.opportunityName?.oppnumber?:""
        )
        approvalDetailCardViewHolder.cardViewApprovalBinding.approvalProjectName.text = Utils.checkValueOrGiveEmpty(
            data?.projectR?.name?:""
        )
        approvalDetailCardViewHolder.cardViewApprovalBinding.approvalTowerName.text = Utils.checkValueOrGiveEmpty(
            data?.towerR?.name?:""
        )
        approvalDetailCardViewHolder.cardViewApprovalBinding.approvalUnitNo.text = Utils.checkValueOrGiveEmpty(
            data?.projectUnitR?.name?:""
        )
        approvalDetailCardViewHolder.cardViewApprovalBinding.approvalOwner.text = Utils.checkValueOrGiveEmpty(
            data?.opportunityName?.ownername?:""
        )

        approvalDetailCardViewHolder.cardViewApprovalBinding.approvalLinearLayout.setOnClickListener {
            val bundle = Bundle()
            log.e("opp ID error", data.oppId?:"")
            bundle.putString(Keys.OPP_ID, data?.oppId?:"")
            (context as MainActivity).navHostFragment.navController.navigate(R.id.action_approvalDetailFragment_to_approvalSubdetailFragment, bundle)
        }

    }

    override fun getItemCount() = projectList.size

    class ApprovalDetailCardViewHolder(var cardViewApprovalBinding: CardViewApprovalBinding) : RecyclerView.ViewHolder(cardViewApprovalBinding.root)
}