package com.stetig.solitaire.data


import com.google.gson.annotations.SerializedName

data class PaymentPlanResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("PayPlanList")
    val payPlanList: String?,
    @SerializedName("status")
    val status: Int?,
    @SerializedName("TotalAVPayPlan")
    val totalAVPayPlan: String?,
    @SerializedName("TotalPayplanPecentage")
    val totalPayplanPecentage: Double?,
    @SerializedName("TotalTaxPayPlan")
    val totalTaxPayPlan: String?,
    @SerializedName("TotalWithTaxPayPlan")
    val totalWithTaxPayPlan: String?
)