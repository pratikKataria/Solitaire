package com.stetig.solitaire.data
import com.google.gson.annotations.SerializedName

data class AppVersionResponse(
    @SerializedName("androidVersion")
    val androidVersion: Any?,
    @SerializedName("androidVersionCode")
    val androidVersionCode: Int?,
    @SerializedName("iosVersion")
    val iosVersion: String?,
    @SerializedName("iosVersionCode")
    val iosVersionCode: Int?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("returnCode")
    val returnCode: Boolean?
)