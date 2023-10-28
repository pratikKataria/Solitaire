package com.stetig.solitaire.data


import com.google.gson.annotations.SerializedName

data class MarkAsCompleteRes(
    @SerializedName("message")
    val message: String,
    @SerializedName("TaskId")
    val taskid: Int,
    @SerializedName("status")
    val status: String
)