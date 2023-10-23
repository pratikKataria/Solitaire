/**
 * Created by Pratik Katariya on 14-10-2020
 */
package com.stetig.solitaire.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Pratik Katariya on 14-10-2020
 */
public class UpdateTokenRes {

    @SerializedName("message")
    private String message;
    @SerializedName("returnCode")
    private int returncode;
    @SerializedName("token")
    private String token;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getReturncode() {
        return returncode;
    }

    public void setReturncode(int returncode) {
        this.returncode = returncode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "UpdateTokenRes{" +
                "message='" + message + '\'' +
                ", returncode=" + returncode +
                ", token='" + token + '\'' +
                '}';
    }
}