package com.stetig.solitaire.data


import com.google.gson.annotations.SerializedName

data class Timeline(
    @SerializedName("done")
    val done: Boolean,
    @SerializedName("records")
    val records: List<Record>,
    @SerializedName("totalSize")
    val totalSize: Int
) {
    data class Record(
        @SerializedName("ActivityDate")
        val activityDate: String,
        @SerializedName("attributes")
        val attributes: Attributes,
        @SerializedName("CreatedDate")
        val createdDate: String,
        @SerializedName("Id")
        val id: String,
        @SerializedName("Mobile_Number_Webform__c")
        val mobileNumberWebformC: String,
        @SerializedName("RecordTypeId")
        val recordTypeId: String,
        @SerializedName("Status")
        val status: String,
        @SerializedName("Subject")
        val subject: String,
        @SerializedName("Task_Type__c")
        val taskTypeC: String,
        @SerializedName("WhatId")
        val whatId: String
    ) {
        data class Attributes(
            @SerializedName("type")
            val type: String,
            @SerializedName("url")
            val url: String
        )
    }
}