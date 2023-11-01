package com.stetig.solitaire.data
import com.google.gson.annotations.SerializedName
data class FeedBack(
    @SerializedName("Id")
    var id: String? = null,
    @SerializedName("TypeOfEnquiry")
    var typeofenquiry: String? = null,
    @SerializedName("VisitedAt")
    var visitiedAt: String? = null,
    @SerializedName("Ethnicity")
    var ethnicity: String? = null,
    @SerializedName("Zone")
    var zone: String? = null,
    @SerializedName("TAC")
    var tac: String? = null,
    @SerializedName("Rating")
    var rating: String? = null,
    @SerializedName("Sm_Feedback")
    var smFeedback: String? = null,
    @SerializedName("Placeofwork")
    var placeofwork: String? = null,
    @SerializedName("designation")
    var designation: String? = null,
    @SerializedName("Pincode")
    var pincode: String? = null,
    @SerializedName("NextActionDate")
    var nextactiondate: String? = null,
    @SerializedName("Budget")
    var budget: String? = null,
    @SerializedName("DesiredPossession")
    var desiredPossession: String? = null,
    @SerializedName("Anniversary")
    var anniversarydate: String? = null,
    @SerializedName("WholesellerRetailer")
    var wholesellerretailer: String? = null,
    @SerializedName("Subcategory")
    var subCategory: String? = null,
    @SerializedName("ShopOwnership")
    var shopOwnership: String? = null,
    @SerializedName("ShopSize")
    var shopSize: String? = null,
    @SerializedName("Rental")
    var rental: String? = null,
    @SerializedName("DailyBusiness")
    var dailyBusiness: String? = null,
    @SerializedName("EmployeeSize")
    var employeeSize: String? = null,
    @SerializedName("TotalOutlet")
    var totalOutlet: String? = null,
    @SerializedName("OutletLocation")
    var outletLocation: String? = null,
    @SerializedName("Leasign")
    var leasign: String? = null,
    @SerializedName("Disposition")
    var disposition: String? = null,
)