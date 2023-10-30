import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stetig.solitaire.R
import com.stetig.solitaire.activity.MainActivity
import com.stetig.solitaire.api.Keys
import com.stetig.solitaire.data.CampaignApproval
import com.stetig.solitaire.databinding.CardViewApprovalBinding
import com.stetig.solitaire.databinding.CardViewCampaignApprovalBinding
import com.stetig.solitaire.utils.Utils
import org.acra.ACRA.log

class CampaignApprovalRecyclerAdapter(private var context: Context, private var projectList: List<CampaignApproval.Record>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val cardViewApprovalBinding = CardViewCampaignApprovalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ApprovalDetailCardViewHolder(cardViewApprovalBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val approvalDetailCardViewHolder = holder as ApprovalDetailCardViewHolder
        val data = projectList[position]
//        log.e("name", data.opportunityName.name)
        approvalDetailCardViewHolder.cardViewApprovalBinding.parentCampaignName.text = Utils.checkValueOrGiveEmpty(
            data?.parent?.name?:""
        )
        approvalDetailCardViewHolder.cardViewApprovalBinding.childCampaignName.text = Utils.checkValueOrGiveEmpty(
            data?.name?:""
        )
        approvalDetailCardViewHolder.cardViewApprovalBinding.campaignProjectName.text = Utils.checkValueOrGiveEmpty(
            data?.primaryProjectR?.name?:""
        )
        approvalDetailCardViewHolder.cardViewApprovalBinding.approvalLinearLayout.setOnClickListener {
            val bundle = Bundle()
            log.e("Campaign ID error", data.id?:"")
            bundle.putString(Keys.CAM_ID, data?.id?:"")
            (context as MainActivity).navHostFragment.navController.navigate(R.id.action_campaignApprovalDetailFragment_to_campaignApprovalSubDetailFragment, bundle)
        }

    }

    override fun getItemCount() = projectList.size

    class ApprovalDetailCardViewHolder(var cardViewApprovalBinding: CardViewCampaignApprovalBinding) : RecyclerView.ViewHolder(cardViewApprovalBinding.root)
}