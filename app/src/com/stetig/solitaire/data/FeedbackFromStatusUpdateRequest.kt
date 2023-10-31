package com.stetig.solitaire.data
import com.google.gson.annotations.SerializedName

data class FeedbackFromStatusUpdateRequest(
    @SerializedName("Id")
    val id: String?
)