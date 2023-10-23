package com.stetig.solitaire.data


import com.google.gson.annotations.SerializedName

data class TaskCount(
        @SerializedName("done")
        val done: Boolean,
        @SerializedName("records")
        val records: List<Record>,
        @SerializedName("totalSize")
        val totalSize: Int,
        var type: String
) {
    data class Record(
            @SerializedName("attributes")
            val attributes: Attributes,
            @SerializedName("expr0")
            val expr0: Int
    ) {
        data class Attributes(
                @SerializedName("type")
                val type: String
        )
    }
}