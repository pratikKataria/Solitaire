package com.stetig.solitaire.data
import com.google.gson.annotations.SerializedName

class ProjectDetail(
    @SerializedName("done")
    val done: Boolean?,
    @SerializedName("records")
    val records: List<Record?>,
    @SerializedName("totalSize")
    val totalSize: Int?
)

data class Record(
    @SerializedName("attributes")
    val attributes: Attributes?,
    @SerializedName("ContentDocumentId")
    val contentDocumentId: String?,
    @SerializedName("Id")
    val id: String?
)

data class Attributes(
    @SerializedName("type")
    val type: String?,
    @SerializedName("url")
    val url: String?
)