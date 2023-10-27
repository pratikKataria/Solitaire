
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stetig.solitaire.R
import com.stetig.solitaire.activity.MainActivity
import com.stetig.solitaire.api.Keys
import com.stetig.solitaire.data.SourceChangeApproval
import com.stetig.solitaire.databinding.CardViewSourcechangeApprovalBinding
import com.stetig.solitaire.utils.Utils
import org.acra.ACRA.log

class SourceChangeRecyclerAdapter(private var context: Context, private var projectList: List<SourceChangeApproval.Record>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val cardViewApprovalBinding = CardViewSourcechangeApprovalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ApprovalDetailCardViewHolder(cardViewApprovalBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val approvalDetailCardViewHolder = holder as ApprovalDetailCardViewHolder
        val data = projectList[position]
//        log.e("name", data.opportunityName.name)
        approvalDetailCardViewHolder.cardViewApprovalBinding.sourcechangeProjectName.text = Utils.checkValueOrGiveEmpty(
            data?.name?:""
        )
        approvalDetailCardViewHolder.cardViewApprovalBinding.sourcechangeOppOwner.text = Utils.checkValueOrGiveEmpty(
            data?.owner?.name?:""
        )
        approvalDetailCardViewHolder.cardViewApprovalBinding.sourcechangeOpp.text = Utils.checkValueOrGiveEmpty(
            data?.customerName?.name?:""
        )
        approvalDetailCardViewHolder.cardViewApprovalBinding.sourceChangeApprovalLinearLayout.setOnClickListener {
            val bundle = Bundle()
            log.e("source change ID error", data.id?:"")
            bundle.putString(Keys.CAM_ID, data?.id?:"")
            (context as MainActivity).navHostFragment.navController.navigate(R.id.action_sourceChangeApprovalDetailFragment_to_sourceChangeApprovalSubDetailFragment, bundle)
        }

    }

    override fun getItemCount() = projectList.size

    class ApprovalDetailCardViewHolder(var cardViewApprovalBinding: CardViewSourcechangeApprovalBinding) : RecyclerView.ViewHolder(cardViewApprovalBinding.root)
}