package com.stetig.solitaire.data
import com.google.gson.annotations.SerializedName

data class Sitevisit(
    @SerializedName("done")
    val done: Boolean,
    @SerializedName("records")
    val records: List<Record>,
    @SerializedName("totalSize")
    val totalSize: Int
) {
    data class Record(
        @SerializedName("attributes")
        val attributes: Task.Record.Attributes?,
        @SerializedName("Call_Proposed_Date_Of_Visit__c")
        val activityDate: String?,
        @SerializedName("Next_Action_Date__c")
        val nextActionDate: String?,
        @SerializedName("Mobile_No2__c")
        val mobileNo2C: String?,
        @SerializedName("Project__r")
        val projectR: Task.Record.ProjectR?,
        @SerializedName("What")
        val what: Task.Record.What?,

        @SerializedName("Id")
        val id: String,
        @SerializedName("Name")
        val name: String,
        @SerializedName("Customer_Name1__c")
        val customername: String,
        @SerializedName("Customer_Name__c")
        val customerNameId: String,
        @SerializedName("Type_of_Visit__c")
        val typeofvisit: String,
        @SerializedName("Type_of_Enquiry__c")
        val typeofenquiry: String,
        @SerializedName("Opportunity_Project__c")
        val oppproject: String,
        @SerializedName("Opportunity_Sourcing_Manager__c")
        val oppsourcingmanager: String,
        @SerializedName("CreatedDate")
        val createdDate: String,
        @SerializedName("Site_Visit_Stage__c")
        val sitevisitstage: String
    ) {
        data class Attributes(
            @SerializedName("type")
            val type: String,
            @SerializedName("url")
            val url: String
        )
    }
}