package com.stetig.solitaire.data


import com.google.gson.annotations.SerializedName

data class MarkAsCompleteRes(
    @SerializedName("message")
    val message: String,
    @SerializedName("returnCode")
    val returnCode: Int,
    @SerializedName("status")
    val status: String
)