package com.stetig.solitaire.data


import com.google.gson.annotations.SerializedName

data class UpdateOpportunityReq(
    @SerializedName("address")
    val address: String,
    @SerializedName("closedlostreason")
    val closedlostreason: String,
    @SerializedName("oppId")
    val oppId: String,
    @SerializedName("project")
    val project: String,
    @SerializedName("Salesuserid")
    val salesuserid: String,
    @SerializedName("stage")
    val stage: String
)