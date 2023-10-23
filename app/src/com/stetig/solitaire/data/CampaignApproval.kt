package com.stetig.solitaire.data

import com.google.gson.annotations.SerializedName

data class CampaignApproval(
    @SerializedName("done")
    val done: Boolean?,
    @SerializedName("records")
    val records: List<Record>,
    @SerializedName("totalSize")
    val totalSize: Int?
) {
    data class Record(
        @SerializedName("attributes")
        val attributes: Attributes?,
        @SerializedName("BudgetedCost")
        val budgetedCost: Double?,
        @SerializedName("EndDate")
        val endDate: String?,
        @SerializedName("Id")
        val id: String?,
        @SerializedName("Name")
        val name: String?,
        @SerializedName("Parent")
        val parent: Parent?,
        @SerializedName("Primary_Project__r")
        val primaryProjectR: PrimaryProjectR?,
        @SerializedName("StartDate")
        val startDate: String?,
        @SerializedName("Approval_Status__c")
        val approvalStatus: String?
    ) {
        data class Attributes(
            @SerializedName("type")
            val type: String?,
            @SerializedName("url")
            val url: String?
        )

        data class Parent(
            @SerializedName("attributes")
            val attributes: Attributes?,
            @SerializedName("Name")
            val name: String?
        ) {
            data class Attributes(
                @SerializedName("type")
                val type: String?,
                @SerializedName("url")
                val url: String?
            )
        }

        data class PrimaryProjectR(
            @SerializedName("attributes")
            val attributes: Attributes?,
            @SerializedName("Name")
            val name: String?
        ) {
            data class Attributes(
                @SerializedName("type")
                val type: String?,
                @SerializedName("url")
                val url: String?
            )
        }
    }
}