package com.stetig.solitaire.data

import com.google.gson.annotations.SerializedName
class SourceChangeApproval(
    @SerializedName("done")
    val done: Boolean,
    @SerializedName("records")
    val records: List<Record>,
    @SerializedName("totalSize")
    val totalSize: Int
) {
    data class Record(
        @SerializedName("Id")
        val id: String?,
        @SerializedName("Name")
        val name: String?,
        @SerializedName("Customer_Name__c")
        val cusName: String?,
        @SerializedName("Customer_Name__r")
        val customerName: CustomerNamer,
        @SerializedName("Owner")
        val owner: OwnerName,
        @SerializedName("Visit_source_approval_Status__c")
        val approvalstatus: String?,
        @SerializedName("Site_Visit_Stage__c")
        val sitevisitstage: String?,
        @SerializedName("Type_of_Enquiry__c")
        val typeenquiry: String?,
        @SerializedName("Mobile_No2__c")
        val mobileno: String?,
        @SerializedName("Source1__c")
        val newsource: String?,
        @SerializedName("Opportunity_Walk_in_Source__c")
        val oldsource: String?,
        @SerializedName("Sub_Source2__c")
        val newsubsource: String?,
        @SerializedName("Opportunity_Walk_in_Sub_Source__c")
        val oldsubsource: String?,
        @SerializedName("Channel_Partner_Leasing__c")
        val partnertype: String?,
        @SerializedName("Project__r")
        val project: ProjectR

    ){
        data class Attributes(
            @SerializedName("type")
            val type: String,
            @SerializedName("url")
            val url: String
        )

        data class CustomerNamer(
            @SerializedName("attributes")
            val attributes: Opportunity.Record.BookingR.Attributes,
            @SerializedName("Name")
            val name: String
        ){ data class Attributes(
            @SerializedName("type")
            val type: String,
            @SerializedName("url")
            val url: String
        )}

        data class OwnerName(
            @SerializedName("attributes")
            val attributes: Opportunity.Record.BookingR.Attributes,
            @SerializedName("Name")
            val name: String
        ){ data class Attributes(
            @SerializedName("type")
            val type: String,
            @SerializedName("url")
            val url: String
        )}

        data class ProjectR(
            @SerializedName("attributes")
            val attributes: Opportunity.Record.BookingR.Attributes,
            @SerializedName("Name")
            val name: String
        ){ data class Attributes(
            @SerializedName("type")
            val type: String,
            @SerializedName("url")
            val url: String
        )}
    }
}