package com.stetig.solitaire.data

import com.google.gson.annotations.SerializedName

data class AllOptyRequest(
    @SerializedName("DueDate")
    val dueDate: String?,
    @SerializedName("opp_Id")
    val oppId: String?,
    @SerializedName("Subject")
    val subject: String?,
    @SerializedName("Tasktype")
    val tasktype: String?
)