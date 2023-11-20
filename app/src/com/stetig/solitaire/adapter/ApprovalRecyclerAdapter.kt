import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
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
        val costSheets1R: Approval.Record.CostSheets1R.Record? = if (data.costSheets1R?.records?.isEmpty() == true) null else data.costSheets1R?.records?.first()
        val paymentPlansR: Approval.Record.PaymentPlansR.Record? = if (data.paymentPlansR?.records?.isEmpty() == true) null else data.paymentPlansR?.records?.first()


        val cardViewApprovalBinding = approvalDetailCardViewHolder.cardViewApprovalBinding
        cardViewApprovalBinding.costSheet.visibility = if (data.costSheets1R == null) View.GONE else View.VISIBLE
        cardViewApprovalBinding.paymentPlan.visibility = if (data.paymentPlansR == null) View.GONE else View.VISIBLE
//        log.e("name", data.opportunityName.name)
        approvalDetailCardViewHolder.cardViewApprovalBinding.approvalOpportunityName.text = Utils.checkValueOrGiveEmpty(
            data?.name ?: ""
        )
        approvalDetailCardViewHolder.cardViewApprovalBinding.approvalOpportunity.text = Utils.checkValueOrGiveEmpty(
            data?.owner?.name ?: ""
        )
        approvalDetailCardViewHolder.cardViewApprovalBinding.approvalProjectName.text = Utils.checkValueOrGiveEmpty(
            data?.projectR?.name ?: ""
        )
        approvalDetailCardViewHolder.cardViewApprovalBinding.approvalTowerName.text = Utils.checkValueOrGiveEmpty(
            data?.projectUnitR?.towerNameR?.name ?: ""
        )
        approvalDetailCardViewHolder.cardViewApprovalBinding.approvalUnitNo.text = Utils.checkValueOrGiveEmpty(
            data?.projectUnitR?.name ?: ""
        )
        approvalDetailCardViewHolder.cardViewApprovalBinding.approvalOwner.text = Utils.checkValueOrGiveEmpty(
            data?.owner?.name ?: ""
        )

        approvalDetailCardViewHolder.cardViewApprovalBinding.approvalLinearLayout.setOnClickListener {
            val bundle = Bundle()
            log.e("opp ID error", data?.id ?: "")
            bundle.putString(Keys.OPP_ID, data?.id ?: "")
            bundle.putString(Keys.PP_ID, paymentPlansR?.id)
            bundle.putString(Keys.CS_ID, costSheets1R?.id)
            (context as MainActivity).navHostFragment.navController.navigate(R.id.action_approvalDetailFragment_to_approvalSubdetailFragment, bundle)
        }

    }

    override fun getItemCount() = projectList.size

    class ApprovalDetailCardViewHolder(var cardViewApprovalBinding: CardViewApprovalBinding) : RecyclerView.ViewHolder(cardViewApprovalBinding.root)
}