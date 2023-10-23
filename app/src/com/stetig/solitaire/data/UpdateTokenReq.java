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
    @SerializedName("deviceName")
    private String devicename;
    @Expose
    @SerializedName("userEmail")
    private String useremail;
    @Expose
    @SerializedName("salesusername")
    private String salesusername;
    @Expose
    @SerializedName("deviceToken")
    private String devicetoken;
    @Expose
    @SerializedName("appVersion")
    private String appversion;

    public UpdateTokenReq() {}

    public UpdateTokenReq(String useremail, String appversion, String devicetoken, String salesusername, String devicename) {
        this.devicename = devicename;
        this.useremail = useremail;
        this.salesusername = salesusername;
        this.devicetoken = devicetoken;
        this.appversion = appversion;
    }

    public String getDevicename() {
        return devicename;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
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

    public String getAppversion() {
        return appversion;
    }

    public void setAppversion(String appversion) {
        this.appversion = appversion;
    }
}