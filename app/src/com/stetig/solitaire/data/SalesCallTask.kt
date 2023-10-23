
package com.stetig.solitaire.data
import com.google.gson.annotations.SerializedName

data class SalesCallTask(
    @SerializedName("Call_Attempt_status")
    val callAttemptStatus: String?,
    @SerializedName("CallDate")
    val callDate: String?,
    @SerializedName("calltime")
    val calltime: String?,
    @SerializedName("Comment")
    val comment: String?,
    @SerializedName("communicationtype")
    val communicationtype: String?,
    @SerializedName("DispositionPicklist")
    val dispositionPicklist: String?,
    @SerializedName("Mobile_number")
    val mobileNumber: String?,
    @SerializedName("Rating")
    val rating: String?,
    @SerializedName("Record_Type")
    val recordType: String?
)