package com.stetig.solitaire.data
import com.google.gson.annotations.SerializedName

data class ManualTaskListResponse(
    @SerializedName("done")
    val done: Boolean?,
    @SerializedName("records")
    val records: List<Record?>,
    @SerializedName("totalSize")
    val totalSize: Int?
) {
    data class Record(
        @SerializedName("ActivityDate")
        val activityDate: String?,
        @SerializedName("attributes")
        val attributes: Attributes?,
        @SerializedName("CreatedDate")
        val createdDate: String?,
        @SerializedName("Id")
        val id: String?,
        @SerializedName("ProjectInterestedWeb__r")
        val projectInterestedWebR: Any?,
        @SerializedName("Subject")
        val subject: String?,
        @SerializedName("Task_Type__c")
        val taskTypeC: String?,
        @SerializedName("What")
        val what: What?,
        @SerializedName("WhatId")
        val whatId: String?
    ) {
        data class Attributes(
            @SerializedName("type")
            val type: String?,
            @SerializedName("url")
            val url: String?
        )

        data class What(
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