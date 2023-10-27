package com.stetig.solitaire.data

import com.google.gson.annotations.SerializedName
class CpCreationApproval(
    @SerializedName("done")
    val done: Boolean?,
    @SerializedName("records")
    val records: List<Record>,
    @SerializedName("totalSize")
    val totalSize: Int?
) {
    data class Record(
        @SerializedName("Id")
        val id: String?,
        @SerializedName("NTS_ID__c")
        val ntsId: String?,
        @SerializedName("Name")
        val name: String? = null,
        @SerializedName("CP_Sub_Type__c")
        val cpSubType: String,
        @SerializedName("CP_Type__c")
        val cpType: String,
        @SerializedName("Firm_Name__c")
        val firmName: String,
        @SerializedName("Sourcing_Manager__r")
        val sourcingManager: SourcingManagerR,
        @SerializedName("Maha_RERA_No__c")
        val mahaRERAno: String,
        @SerializedName("RERA_Expiry_Date__c")
        val RERAexpData: String,
        @SerializedName("Zone__c")
        val zone: String,
        @SerializedName("Expertise__c")
        val expertise: String,
        @SerializedName("Location_Of_the_CPs_Office__c")
        val officelocation: String,
        @SerializedName("Level_1_Submitted_Date_Time__c")
        val level1submissiondatetime: String,
        @SerializedName("Level_2_Date_Time__c")
        val level2datetime: String,
    ){
        data class Attributes(
            @SerializedName("type")
            val type: String,
            @SerializedName("url")
            val url: String
        )

        data class SourcingManagerR(
            @SerializedName("attributes")
            val attributes: Task.Record.ProjectR.Attributes?,
            @SerializedName("Name")
            val name: String?
        ){
            data class Attributes(
                @SerializedName("type")
                val type: String,
                @SerializedName("url")
                val url: String
            )
        }
    }
}