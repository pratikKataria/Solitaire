
package com.stetig.solitaire.data
import android.os.Bundle
import com.google.gson.annotations.SerializedName

class SalesCallTaskResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Int?,
    var bundle: Bundle? = null
)