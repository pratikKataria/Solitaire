package com.stetig.solitaire.data


import com.google.gson.annotations.SerializedName

data class Search(
    @SerializedName("done")
    val done: Boolean,
    @SerializedName("records")
    val records: List<Record>,
    @SerializedName("totalSize")
    val totalSize: Int
) {
    data class Record(
        @SerializedName("Account_Mobile_Number__c")
        val accountMobileNumberC: String,
        @SerializedName("attributes")
        val attributes: Attributes,
        @SerializedName("Id")
        val id: String,
        @SerializedName("Name")
        val name: String,
        @SerializedName("Project__c")
        val projectC: String,
        @SerializedName("Project__r")
        val projectR: ProjectR,
        @SerializedName("Sales_Call_Attempt_Date__c")
        val salesCallAttemptDateC: String,
        @SerializedName("StageName")
        val stageName: String,
        @SerializedName("Status__c")
        val statusC: String
    ) {
        data class Attributes(
            @SerializedName("type")
            val type: String,
            @SerializedName("url")
            val url: String
        )

        data class ProjectR(
            @SerializedName("attributes")
            val attributes: Attributes,
            @SerializedName("Name")
            val name: String
        ) {
            data class Attributes(
                @SerializedName("type")
                val type: String,
                @SerializedName("url")
                val url: String
            )
        }
    }
}