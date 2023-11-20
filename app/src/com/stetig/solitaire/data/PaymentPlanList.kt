package com.stetig.solitaire.data


import com.google.gson.annotations.SerializedName

class PaymentPlanList : ArrayList<PaymentPlanList.PaymentPlanListItem>(){
    data class PaymentPlanListItem(
        @SerializedName("Amount_Value__c")
        val amountValueC: Double?,
        @SerializedName("attributes")
        val attributes: Attributes?,
        @SerializedName("Customer_Pay_Plan_Header__c")
        val customerPayPlanHeaderC: String?,
        @SerializedName("Days_Months_Value__c")
        val daysMonthsValueC: Int?,
        @SerializedName("Due_Date__c")
        val dueDateC: String?,
        @SerializedName("Id")
        val id: String?,
        @SerializedName("Installment__c")
        val installmentC: Int?,
        @SerializedName("Is_to_be__c")
        val isToBeC: String?,
        @SerializedName("Is_to_be_Paid__c")
        val isToBePaidC: String?,
        @SerializedName("Milestone_Percentage__c")
        val milestonePercentageC: Double?,
        @SerializedName("Name")
        val name: String?,
        @SerializedName("OC_Milestone__c")
        val oCMilestoneC: Boolean?,
        @SerializedName("Original_Percentage__c")
        val originalPercentageC: Double?,
        @SerializedName("Project_Construction_Stages__c")
        val projectConstructionStagesC: String?,
        @SerializedName("Project_Construction_Stages__r")
        val projectConstructionStagesR: ProjectConstructionStagesR?,
        @SerializedName("Service_Tax_Amount_Q__c")
        val serviceTaxAmountQC: Int?,
        @SerializedName("Standard_Pay_Plan_Header__c")
        val standardPayPlanHeaderC: String?,
        @SerializedName("Standard_Pay_Plan_Header__r")
        val standardPayPlanHeaderR: StandardPayPlanHeaderR?,
        @SerializedName("Total_of_Tax_Av_Milestone_wise__c")
        val totalOfTaxAvMilestoneWiseC: Int?
    ) {
        data class Attributes(
            @SerializedName("type")
            val type: String?,
            @SerializedName("url")
            val url: String?
        )
    
        data class ProjectConstructionStagesR(
            @SerializedName("attributes")
            val attributes: Attributes?,
            @SerializedName("Id")
            val id: String?,
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
    
        data class StandardPayPlanHeaderR(
            @SerializedName("attributes")
            val attributes: Attributes?,
            @SerializedName("Id")
            val id: String?,
            @SerializedName("Payment_Plan__c")
            val paymentPlanC: String?,
            @SerializedName("Payment_Plan__r")
            val paymentPlanR: PaymentPlanR?
        ) {
            data class Attributes(
                @SerializedName("type")
                val type: String?,
                @SerializedName("url")
                val url: String?
            )
    
            data class PaymentPlanR(
                @SerializedName("attributes")
                val attributes: Attributes?,
                @SerializedName("Id")
                val id: String?,
                @SerializedName("Sub_Vention__c")
                val subVentionC: Boolean?
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