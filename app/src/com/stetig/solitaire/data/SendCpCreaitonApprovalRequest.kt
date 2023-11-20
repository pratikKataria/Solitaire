package com.stetig.solitaire.data

import com.google.gson.annotations.SerializedName

class SendCpCreaitonApprovalRequest(
    @SerializedName("cpId")
    val cpId: String? = null,
    @SerializedName("CCRId")
    val BilkEmailSMSId: String? = null,
    @SerializedName("Status")
    val status: String? = null,
    @SerializedName("Comment")
    val comment: String? = null,
) {

}