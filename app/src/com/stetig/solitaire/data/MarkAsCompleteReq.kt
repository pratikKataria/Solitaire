package com.stetig.solitaire.data


import com.google.gson.annotations.SerializedName

data class MarkAsCompleteReq(
    @SerializedName("Id")
    val id: String,
)