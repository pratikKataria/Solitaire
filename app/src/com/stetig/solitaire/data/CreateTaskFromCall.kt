package com.stetig.solitaire.data


import com.google.gson.annotations.SerializedName

data class CreateTaskFromCall(
    @SerializedName("callAttemptStatus")
    val callAttemptStatus: String,
    @SerializedName("callproposeddateofvisit")
    val callproposeddateofvisit: String,
    @SerializedName("closedlostreason")
    val closedlostreason: String? = null,
    @SerializedName("communicationtype")
    val communicationtype: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("mobilenumber")
    val mobilenumber: String,
    @SerializedName("Nextactiondate")
    val nextactiondate: String,
    @SerializedName("projectname")
    val projectname: String? = null,
    @SerializedName("salesuserid")
    val salesuserid: String,
    @SerializedName("optyid")
    val optyId: String? = null,
    @SerializedName("callduration")
    val callDuration: String? = null,
    @SerializedName("calldurationseconds")
    val callDurationInSeconds: String? = "",
    @SerializedName("appVersionUser")
    var appVersionUser: String? = "",
)