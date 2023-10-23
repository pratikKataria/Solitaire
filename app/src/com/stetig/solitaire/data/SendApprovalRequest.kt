
package com.stetig.solitaire.data
import com.google.gson.annotations.SerializedName

data class SendApprovalRequest(
    @SerializedName("CostSheet_Id")
    val costsheetId: String?,
    @SerializedName("paymentplan_Id")
    val paymentplanId: String?,
    @SerializedName("Status")
    val status: String?,
    @SerializedName("Type")
    val type: String?,
    @SerializedName("comment")
    val comment: String?,
)