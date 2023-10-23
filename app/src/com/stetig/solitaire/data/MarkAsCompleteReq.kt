package com.stetig.solitaire.data


import com.google.gson.annotations.SerializedName

data class MarkAsCompleteReq(
    @SerializedName("salesuserid")
    val salesuserid: String,
    @SerializedName("taskid")
    val taskid: String
)