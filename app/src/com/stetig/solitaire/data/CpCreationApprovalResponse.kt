package com.stetig.solitaire.data

import com.google.gson.annotations.SerializedName

class CpCreationApprovalResponse (
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
        )