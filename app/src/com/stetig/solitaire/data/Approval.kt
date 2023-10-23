
package com.stetig.solitaire.data
import com.google.gson.annotations.SerializedName

data class Approval(
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
        @SerializedName("Opportunity_Name__c")
        val oppId: String,
        @SerializedName("Name")
        val name: String,
        @SerializedName("Status__c")
        val status: String,
        @SerializedName("Project__r")
        val projectR: ProjectR,
        @SerializedName("Tower__r")
        val towerR : TowerR,
        @SerializedName("Project_Unit__r")
        val projectUnitR : ProjectUnitR,
        @SerializedName("Opportunity_Name__r")
        val opportunityName: OpportunityNameR

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
        data class TowerR(
            @SerializedName("attributes")
            val attributes: Attributes,
            @SerializedName("Name")
            val name: String
        ){
            data class Attributes(
                @SerializedName("type")
                val type: String,
                @SerializedName("url")
                val url: String
            )
        }
        data class ProjectUnitR(
            @SerializedName("attributes")
            val attributes: Attributes,
            @SerializedName("Name")
            val name: String
        ){
            data class Attributes(
                @SerializedName("type")
                val type: String,
                @SerializedName("url")
                val url: String
            )
        }
        data class OpportunityNameR(
            @SerializedName("attributes")
            val attributes: Attributes,
            @SerializedName("Name")
            val name: String,
            @SerializedName("Owner.Name")
            val ownername: String,
            @SerializedName("Opportunity_Number__c ")
            val oppnumber: String,
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