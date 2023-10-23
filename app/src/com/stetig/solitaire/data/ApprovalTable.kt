package com.stetig.solitaire.data
import com.google.gson.annotations.SerializedName

//id,Base_Rate__c,Base_Rate_Original__c,Total_Amount_for_Unit__c,Infrastructure_Charges__c,

//Infrastructure_Charges_Original__c,e_IBE_MSEB_Development_Charges__c,IBE_MSEB_Development_Charges_Original__c,

//Premium_Charges__c,Premium_Charges_Original__c,Floor_Rise__c,Floor_Rise_Original__c,Legal_Charges__c,

//Legal_Charges_Original__c,X1_Total_Consideration_Value_c_d_e__c,Total_Consideration_Value_Original__c,

//Total_Consideration_Value_Difference__c,Stamp_Duty_Waived_Off__c,GST_Waived_Off__c,Registration_Charges_Waived_Off__c

data class ApprovalTable(
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
        @SerializedName("Base_Rate__c")
        val baseRate: String,
        @SerializedName("Base_Rate_Original__c")
        val baseRateOriginal: String,
        @SerializedName("Total_Amount_for_Unit__c")
        val totalAmountforUnit : String,
        @SerializedName("Infrastructure_Charges__c")
        val infrastructureCharges : String,
        @SerializedName("Infrastructure_Charges_Original__c")
        val infrastructureChargesOriginal: String,
        @SerializedName("e_IBE_MSEB_Development_Charges__c")
        val eIBE_MSEB_development_charges: String,
        @SerializedName("IBE_MSEB_Development_Charges_Original__c")
        val IBE_MSEM_development_charges_original: String,
        @SerializedName("Premium_Charges__c")
        val premium_charges: String,
        @SerializedName("Premium_Charges_Original__c")
        val premium_charges_original: String,
        @SerializedName("Floor_Rise__c")
        val floorRise : String,
        @SerializedName("Floor_Rise_Original__c")
        val floorRise_original: String,
        @SerializedName("Amenities__c")
        val amenities: String,
        @SerializedName("Amenities_PST_Original__c")
        val amenities_original: String,
        @SerializedName("Legal_Charges__c")
        val legalCharges: String,
        @SerializedName("Legal_Charges_Original__c")
        val legalCharges_original: String,
        @SerializedName("X1_Total_Consideration_Value_c_d_e__c")
        val x1_totalConsideration_value: String,
        @SerializedName("Total_Consideration_Value_Original__c")
        val totalConsideration_value_original: String,
        @SerializedName("Total_Consideration_Value_Difference__c")
        val totalConsideration_value_diff : String,
        @SerializedName("Stamp_Duty_Waived_Off__c")
        val stampDuty_waivedOff: String,
        @SerializedName("GST_Waived_Off__c")
        val GST_waivedOff : String,
        @SerializedName("Registration_Charges_Waived_Off__c")
        val registration_Charges_WaivedOff: String

    ) {
        data class Attributes(
            @SerializedName("type")
            val type: String,
            @SerializedName("url")
            val url: String
        )
    }
}