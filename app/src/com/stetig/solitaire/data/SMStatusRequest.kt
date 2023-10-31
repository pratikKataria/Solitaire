package com.stetig.solitaire.data

import com.google.gson.annotations.SerializedName

data class SMStatusRequest(
    @SerializedName("Id")
    val id: String?,
    @SerializedName("Status")
    val status: String?
)