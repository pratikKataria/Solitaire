package com.stetig.solitaire.data


import com.google.gson.annotations.SerializedName

data class AccountDetail(
        @SerializedName("done")
        val done: Boolean,
        @SerializedName("records")
        val records: List<Record>,
        @SerializedName("totalSize")
        val totalSize: Int
) {
    data class Record(
            @SerializedName("attributes")
            val attributes: Attributes,
            @SerializedName("Email")
            val email: String,
            @SerializedName("FirstName")
            val firstName: String,
            @SerializedName("Id")
            val id: String,
            @SerializedName("LastName")
            val lastName: String,
            @SerializedName("MobilePhone")
            val mobilePhone: String,
            @SerializedName("Phone")
            val phone: String,
            @SerializedName("Username")
            val username: String,
            @SerializedName("SM_Status__c")
            val smActiveStatus: String
    ) {
        data class Attributes(
                @SerializedName("type")
                val type: String,
                @SerializedName("url")
                val url: String
        )
    }
}