package com.stetig.solitaire.data

import com.google.gson.annotations.SerializedName

class SendCpCreaitonApprovalRequest(
    @SerializedName("cpId")
    val cpId: String?,
    @SerializedName("Status")
    val status: String?,
    @SerializedName("Comment")
    val comment: String?
) {

}