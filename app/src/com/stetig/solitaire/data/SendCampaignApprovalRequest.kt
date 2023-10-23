package com.stetig.solitaire.data

import com.google.gson.annotations.SerializedName

data class SendCampaignApprovalRequest (
    @SerializedName("campaignId")
    val campaignId: String,
    @SerializedName("Status")
    val status: String
    )