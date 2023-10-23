package com.stetig.solitaire.data


import com.google.gson.annotations.SerializedName

data class CreateTaskFromCallResponse(
    @SerializedName("accountId")
    val accountId: String,
    @SerializedName("Id")
    val id: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("opportunityId")
    val opportunityId: String,
    @SerializedName("opportunityname")
    val opportunityname: String,
    @SerializedName("returnCode")
    val returnCode: Int
)