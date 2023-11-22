package com.stetig.solitaire.data


import com.google.gson.annotations.SerializedName

data class Opportunity(
    var type: String? = null,
    @SerializedName("done")
    val done: Boolean,
    @SerializedName("records")
    val records: List<Record>,
    @SerializedName("totalSize")
    val totalSize: Int
) {
    data class Record(
        @SerializedName("Name")
        val name: String,
        @SerializedName("Id")
        val id: String,
        @SerializedName("StageName")
        val stageName: String,
        @SerializedName("Sales_Call_Attempt_Status__c")
        val salesCallAttemptStatusC: String,
        @SerializedName("Status__c")
        val statusC: String,
        @SerializedName("Account_Email__c")
        val accountEmailIdC: String,
        @SerializedName("CreatedDate")
        val createdDate: String,
        @SerializedName("Sales_Call_Description__c")
        val salesCallDescriptionC: String,
        @SerializedName("CloseDate")
        val closeDate: String,
        @SerializedName("Project_Type__c")
        val projectTypeC: String,
        @SerializedName("Booking__r")
        val bookingR: BookingR,
        @SerializedName("Project__c")
        val projectC: String,
        @SerializedName("Account_Mobile_Number__c")
        val accountMobileNumberC: String,
        @SerializedName("Configuration__c")
        val configurationC: String,
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

        data class BookingR(
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