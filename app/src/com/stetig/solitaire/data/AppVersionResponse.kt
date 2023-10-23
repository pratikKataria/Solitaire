package com.stetig.solitaire.data
data class AppVersionResponse(
    val androidVersion: String,
    val androidVersionCode: Int,
    val iosVersion: String,
    val iosVersionCode: Int,
    val message: String,
    val returnCode: Boolean
)