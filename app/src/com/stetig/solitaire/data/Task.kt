package com.stetig.solitaire.data

import com.google.gson.annotations.SerializedName

  data class Task(
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
        @SerializedName("CreatedDate")
        val createdDate: String?,
        @SerializedName("Customer_Name__c")
        val customerNameC: String?,
        @SerializedName("Id")
        val id: String?,
        @SerializedName("Mobile_No2__c")
        val mobileNo2C: String?,
        @SerializedName("Project__r")
        val projectR: ProjectR?,
        @SerializedName("Site_Visit_Stage__c")
        val siteVisitStageC: String?,
        @SerializedName("What")
        val what: What?,
        @SerializedName("Type_of_Enquiry__c")
        val typeofEnquiry: String?,
    ) {
        data class Attributes(
            @SerializedName("type")
            val type: String?,
            @SerializedName("url")
            val url: String?
        )

        data class ProjectR(
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

        data class What(
            @SerializedName("attributes")
            val attributes: Attributes?,
            @SerializedName("Name")
            val name: String?   ,
            @SerializedName("Id")
            val id: String?
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