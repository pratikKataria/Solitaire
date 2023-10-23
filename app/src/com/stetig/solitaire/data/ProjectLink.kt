package com.stetig.solitaire.data
import com.google.gson.annotations.SerializedName
data class ProjectLink(
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
        @SerializedName("LatestPublishedVersion")
        val latestPublishedVersion: LatestPublishedVersion,
    ) {
        data class LatestPublishedVersion(
            @SerializedName("attributes")
            val attributes:Attributes,
            @SerializedName("Title")
            val type: String,
            @SerializedName("Public_URL__c")
            val url__c: String
        )
        data class Attributes(
            @SerializedName("type")
            val type: String,
            @SerializedName("url")
            val url: String
        )
    }
}