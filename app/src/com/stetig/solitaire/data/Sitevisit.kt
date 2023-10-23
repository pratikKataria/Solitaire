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