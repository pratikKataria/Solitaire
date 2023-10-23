package com.stetig.solitaire.data


import com.google.gson.annotations.SerializedName

data class UpdateOpportunityRes(
    @SerializedName("message")
    val message: String,
    @SerializedName("Opplst")
    val opplst: List<Opplst>,
    @SerializedName("returnCode")
    val returnCode: Int
) {
    data class Opplst(
        @SerializedName("Booking")
        val booking: String,
        @SerializedName("Id")
        val id: String,
        @SerializedName("Name")
        val name: String,
        @SerializedName("Project")
        val project: String,
        @SerializedName("Stage")
        val stage: String,
        @SerializedName("status")
        val status: String,
        @SerializedName("unit")
        val unit: String
    )
}