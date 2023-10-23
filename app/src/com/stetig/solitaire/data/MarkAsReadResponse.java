/**
 * Created by Pratik Katariya on 12-09-2020
 */
package com.stetig.solitaire.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Pratik Katariya on 12-09-2020
 */
public class MarkAsReadResponse {

    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("returnCode")
    private int returncode;

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
}