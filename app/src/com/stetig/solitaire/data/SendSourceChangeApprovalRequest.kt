package com.stetig.solitaire.data

import com.google.gson.annotations.SerializedName

class SendSourceChangeApprovalRequest (
    @SerializedName("sitevisitId")
    val sitevisitId: String?,
    @SerializedName("Status")
    val status: String?,
    @SerializedName("Comment")
    val comment: String?
)