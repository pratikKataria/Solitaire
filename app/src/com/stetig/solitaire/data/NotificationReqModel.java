/**
 * Created by Pratik Katariya on 12-09-2020
 */
package com.stetig.solitaire.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Pratik Katariya on 12-09-2020
 */
public class NotificationReqModel {

    @Expose
    @SerializedName("salesmanagerId")
    private String salesmanagerid;

    public NotificationReqModel(String salesmanagerid) {
        this.salesmanagerid = salesmanagerid;
    }

    public String getSalesmanagerid() {
        return salesmanagerid;
    }

    public void setSalesmanagerid(String salesmanagerid) {
        this.salesmanagerid = salesmanagerid;
    }
}