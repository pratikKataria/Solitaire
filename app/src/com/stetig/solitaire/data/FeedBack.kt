package com.stetig.solitaire.data
import com.google.gson.annotations.SerializedName

data class FeedBack(
    @SerializedName("Id")
    val id: String?,
    @SerializedName("TypeOfEnquiry")
    val typeofenquiry: String?,
    @SerializedName("VisitedAt")
    val visitiedAt: String?,
    @SerializedName("Ethnicity")
    val ethnicity: String?,
    @SerializedName("Zone")
    val zone: String?,
    @SerializedName("TAC")
    val tac: String?,
    @SerializedName("Rating")
    val rating: String?,
    @SerializedName("Sm_Feedback")
    val smFeedback: String?,
    @SerializedName("Placeofwork")
    val placeofwork: String?,
    @SerializedName("designation")
    val designation: String?,
    @SerializedName("Pincode")
    val pincode: String?,
)