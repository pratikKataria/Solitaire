package com.stetig.solitaire.data
import com.google.gson.annotations.SerializedName
data class CreateTask(
    @SerializedName("Tasktype")
    var tasktype: String? = null,
    @SerializedName("Subject")
    var subject: String? = null,
    @SerializedName("DueDate")
    var duedate: String? = null,
    @SerializedName("opp_Id")
    var opp_Id: String? = null,
)