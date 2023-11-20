import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stetig.solitaire.R
import com.stetig.solitaire.activity.MainActivity
import com.stetig.solitaire.api.Keys
import com.stetig.solitaire.data.CpCreationApproval
import com.stetig.solitaire.databinding.CardViewCcrApprovalBinding
import com.stetig.solitaire.databinding.CardViewCpCreationApprovalBinding
import com.stetig.solitaire.utils.Utils
import org.acra.ACRA.log

class CCRRecyclerAdapter(private var context: Context, private var projectList: List<CpCreationApproval.Record>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val cardViewApprovalBinding = CardViewCcrApprovalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ApprovalDetailCardViewHolder(cardViewApprovalBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val approvalDetailCardViewHolder = holder as ApprovalDetailCardViewHolder
        val data = projectList[position]
//        log.e("name", data.opportunityName.name)
        approvalDetailCardViewHolder.cardViewApprovalBinding.name.text = Utils.checkValueOrGiveEmpty(
            data?.name?:""
        )
        approvalDetailCardViewHolder.cardViewApprovalBinding.status.text = Utils.checkValueOrGiveEmpty(
            data?.statusCCR?:""
        )
        approvalDetailCardViewHolder.cardViewApprovalBinding.budget.text = Utils.checkValueOrGiveEmpty(
            data?.Updated_Budgeted_Cost_in_Campaign__c
        )

        approvalDetailCardViewHolder.cardViewApprovalBinding.sDate.text = Utils.getFormattedDateYYYYMMDD(data?.Updated_Campaign_Start_Date__c)
        approvalDetailCardViewHolder.cardViewApprovalBinding.eDate.text = Utils.getFormattedDateYYYYMMDD(data?.Updated_Campaign_End_Date__c)

        approvalDetailCardViewHolder.cardViewApprovalBinding.cpApprovalLinearLayout.setOnClickListener {
            val bundle = Bundle()
            log.e("Cp Creation ID error", data.id?:"")
            bundle.putString(Keys.CAM_ID, data?.id?:"")
            (context as MainActivity).navHostFragment.navController.navigate(R.id.action_CCRApprovalRequestFragment_to_CCRDetailFragment, bundle)
        }

    }

    override fun getItemCount() = projectList.size

    class ApprovalDetailCardViewHolder(var cardViewApprovalBinding: CardViewCcrApprovalBinding) : RecyclerView.ViewHolder(cardViewApprovalBinding.root)
}