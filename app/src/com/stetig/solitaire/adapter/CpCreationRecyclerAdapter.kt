import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stetig.solitaire.R
import com.stetig.solitaire.activity.MainActivity
import com.stetig.solitaire.api.Keys
import com.stetig.solitaire.data.CpCreationApproval
import com.stetig.solitaire.databinding.CardViewCpCreationApprovalBinding
import com.stetig.solitaire.utils.Utils
import org.acra.ACRA.log

class CpCreationRecyclerAdapter(private var context: Context, private var projectList: List<CpCreationApproval.Record>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val cardViewApprovalBinding = CardViewCpCreationApprovalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ApprovalDetailCardViewHolder(cardViewApprovalBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val approvalDetailCardViewHolder = holder as ApprovalDetailCardViewHolder
        val data = projectList[position]
//        log.e("name", data.opportunityName.name)
        approvalDetailCardViewHolder.cardViewApprovalBinding.cpApprovalNts.text = Utils.checkValueOrGiveEmpty(
            data?.ntsId?:""
        )
        approvalDetailCardViewHolder.cardViewApprovalBinding.cpApprovalNtsName.text = Utils.checkValueOrGiveEmpty(
            data?.name?:""
        )
        approvalDetailCardViewHolder.cardViewApprovalBinding.cpApprovalFiremname.text = Utils.checkValueOrGiveEmpty(
            data?.firmName?:""
        )

        approvalDetailCardViewHolder.cardViewApprovalBinding.cpApprovalSourcingManager.text = Utils.checkValueOrGiveEmpty(
            data?.sourcingManager?.name?:""
        )
        approvalDetailCardViewHolder.cardViewApprovalBinding.cpApprovalReqDate.text = Utils.checkValueOrGiveEmpty(
            data?.level2datetime?:""
        )

        approvalDetailCardViewHolder.cardViewApprovalBinding.cpApprovalLinearLayout.setOnClickListener {
            val bundle = Bundle()
            log.e("Cp Creation ID error", data.id?:"")
            bundle.putString(Keys.CAM_ID, data?.id?:"")
            (context as MainActivity).navHostFragment.navController.navigate(R.id.action_cpcreationApprovalFragment_to_CPCApprovalFragment, bundle)
        }

    }

    override fun getItemCount() = projectList.size

    class ApprovalDetailCardViewHolder(var cardViewApprovalBinding: CardViewCpCreationApprovalBinding) : RecyclerView.ViewHolder(cardViewApprovalBinding.root)
}