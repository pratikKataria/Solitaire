package com.stetig.solitaire.data

import com.google.gson.annotations.SerializedName
class SolitaireCreateTask (
    @SerializedName("done")
    val done: Boolean,
    @SerializedName("records")
    val records: List<Record>,
    @SerializedName("totalSize")
    val totalSize: Int
        ){
    data class Record(

    @SerializedName("Id")
    val id : String?,
    @SerializedName("Subject")
    val subject: String?,
    @SerializedName("ActivityDate")
    val activityDate: String?,
    @SerializedName("What")
    val what: WhatName
    ){
        data class WhatName(
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
    }

}