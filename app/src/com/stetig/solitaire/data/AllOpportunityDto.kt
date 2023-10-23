package com.stetig.solitaire.data

import com.google.gson.annotations.SerializedName

class AllOpportunityDto : ArrayList<AllOpportunityDto.AllOpportunityDtoItem>(){
    data class AllOpportunityDtoItem(
        @SerializedName("OPPname")
        val oPPname: String?,
        @SerializedName("OppId")
        val oppId: String?
    )
}