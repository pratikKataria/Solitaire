
package com.stetig.solitaire.data
import com.google.gson.annotations.SerializedName

class SalesCallTaskResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Int?
)