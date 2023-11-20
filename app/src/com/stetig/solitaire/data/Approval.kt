package com.stetig.solitaire.data
import com.google.gson.annotations.SerializedName

data class Approval(
    @SerializedName("done")
    val done: Boolean?,
    @SerializedName("records")
    val records: List<Record>,
    @SerializedName("totalSize")
    val totalSize: Int?
) {
    data class Record(
        @SerializedName("attributes")
        val attributes: Attributes?,
        @SerializedName("Cost_Sheets1__r")
        val costSheets1R: CostSheets1R?,
        @SerializedName("Id")
        val id: String?,
        @SerializedName("Name")
        val name: String?,
        @SerializedName("Owner")
        val owner: Owner?,
        @SerializedName("Payment_Plans__r")
        val paymentPlansR: PaymentPlansR?,
        @SerializedName("Project__r")
        val projectR: ProjectR?,
        @SerializedName("Project_Unit__r")
        val projectUnitR: ProjectUnitR?
    ) {
        data class Attributes(
            @SerializedName("type")
            val type: String?,
            @SerializedName("url")
            val url: String?
        )

        data class CostSheets1R(
            @SerializedName("done")
            val done: Boolean?,
            @SerializedName("records")
            val records: List<Record?>?,
            @SerializedName("totalSize")
            val totalSize: Int?
        ) {
            data class Record(
                @SerializedName("attributes")
                val attributes: Attributes?,
                @SerializedName("Id")
                val id: String?,
                @SerializedName("Opportunity_Name__c")
                val opportunityNameC: String?,
                @SerializedName("Opportunity_Name__r")
                val opportunityNameR: OpportunityNameR?,
                @SerializedName("Status__c")
                val statusC: String?,
                @SerializedName("Tower__r")
                val towerR: TowerR?
            ) {
                data class Attributes(
                    @SerializedName("type")
                    val type: String?,
                    @SerializedName("url")
                    val url: String?
                )

                data class OpportunityNameR(
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

                data class TowerR(
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

        data class Owner(
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

        data class PaymentPlansR(
            @SerializedName("done")
            val done: Boolean?,
            @SerializedName("records")
            val records: List<Record?>?,
            @SerializedName("totalSize")
            val totalSize: Int?
        ) {
            data class Record(
                @SerializedName("attributes")
                val attributes: Attributes?,
                @SerializedName("Customer__c")
                val customerC: String?,
                @SerializedName("Customer__r")
                val customerR: CustomerR?,
                @SerializedName("Id")
                val id: String?,
                @SerializedName("Name")
                val name: String?,
                @SerializedName("Status__c")
                val statusC: String?
            ) {
                data class Attributes(
                    @SerializedName("type")
                    val type: String?,
                    @SerializedName("url")
                    val url: String?
                )

                data class CustomerR(
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

        data class ProjectUnitR(
            @SerializedName("attributes")
            val attributes: Attributes?,
            @SerializedName("Name")
            val name: String?,
            @SerializedName("TowerName__r")
            val towerNameR: TowerNameR?
        ) {
            data class Attributes(
                @SerializedName("type")
                val type: String?,
                @SerializedName("url")
                val url: String?
            )

            data class TowerNameR(
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
}