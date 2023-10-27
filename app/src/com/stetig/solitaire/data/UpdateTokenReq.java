/**
 * Created by Pratik Katariya on 14-10-2020
 */
package com.stetig.solitaire.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Pratik Katariya on 14-10-2020
 */
public class UpdateTokenReq {

    @Expose
    @SerializedName("salesusername")
    private String salesusername;
    @Expose
    @SerializedName("deviceToken")
    private String devicetoken;

    public UpdateTokenReq() {}

    public UpdateTokenReq(String devicetoken, String salesusername) {
        this.salesusername = salesusername;
        this.devicetoken = devicetoken;

    }


    public String getSalesusername() {
        return salesusername;
    }

    public void setSalesusername(String salesusername) {
        this.salesusername = salesusername;
    }

    public String getDevicetoken() {
        return devicetoken;
    }

    public void setDevicetoken(String devicetoken) {
        this.devicetoken = devicetoken;
    }

}