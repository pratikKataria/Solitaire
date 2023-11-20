package com.stetig.solitaire.data


import com.google.gson.annotations.SerializedName

data class OpportunityByMobileNumberResponse(
    @SerializedName("done")
    val done: Boolean?,
    @SerializedName("records")
    val records: List<Record?>,
    @SerializedName("totalSize")
    val totalSize: Int?
) {
    data class Record(
        @SerializedName("Account_Mobile_Number__c")
        val accountMobileNumberC: String?,
        @SerializedName("attributes")
        val attributes: Attributes?,
        @SerializedName("Id")
        val id: String?,
        @SerializedName("Name")
        val name: String?,
        @SerializedName("OwnerId")
        val ownerId: String?,
        @SerializedName("Project__r")
        val projectR: ProjectR?
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
    }
}