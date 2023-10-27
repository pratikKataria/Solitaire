package com.stetig.solitaire.data

import com.google.gson.annotations.SerializedName

data class CallTaskRequest(
    @SerializedName("Call_Attempt_status")
    var callAttemptStatus: String? = null,
    @SerializedName("CallDate")
    var callDate: String? = null,
    @SerializedName("calltime")
    var calltime: String? = null,
    @SerializedName("Comment")
    var comment: String? = null,
    @SerializedName("communicationtype")
    var communicationtype: String? = null,
    @SerializedName("DispositionPicklist")
    var dispositionPicklist: String? = null,
    @SerializedName("Mobile_number")
    var mobileNumber: String? = null,
    @SerializedName("Rating")
    var rating: String? = null,
    @SerializedName("Record_Type")
    var recordType: String? = null
)