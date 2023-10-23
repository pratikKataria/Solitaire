/**
 * Created by Pratik Katariya on 12-09-2020
 */
package com.stetig.solitaire.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Pratik Katariya on 12-09-2020
 */
public class MarkAsReadModel {

    @Expose
    @SerializedName("Is_Read")
    private String isRead;
    @Expose
    @SerializedName("NotificationID")
    private String notificationid;

    public MarkAsReadModel(String isRead, String notificationid) {
        this.isRead = isRead;
        this.notificationid = notificationid;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public String getNotificationid() {
        return notificationid;
    }

    public void setNotificationid(String notificationid) {
        this.notificationid = notificationid;
    }
}