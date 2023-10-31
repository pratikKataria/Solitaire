package com.stetig.solitaire.data
import com.google.gson.annotations.SerializedName
data class SMStatusResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Int?
)