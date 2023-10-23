package com.stetig.solitaire.data
import com.google.gson.annotations.SerializedName

data class Projects(
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
        @SerializedName("City__c")
        val cityC: String,
        @SerializedName("Id")
        val id: String,
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
}