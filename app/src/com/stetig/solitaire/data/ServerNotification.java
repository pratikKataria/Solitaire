package com.stetig.solitaire.data; /**
 * Created by Pratik Katariya on 14-10-2020
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Pratik Katariya on 14-10-2020
 */
public class ServerNotification {

    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("NotificationList")
    private List<ServerNotification.NotificationList> NotificationList;
    @Expose
    @SerializedName("returnCode")
    private boolean returnCode;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ServerNotification.NotificationList> getNotificationList() {
        return NotificationList;
    }

    public void setNotificationList(List<ServerNotification.NotificationList> NotificationList) {
        this.NotificationList = NotificationList;
    }

    public boolean getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(boolean returnCode) {
        this.returnCode = returnCode;
    }

    public static class NotificationList {
        @Expose
        @SerializedName("Body")
        private String Body;
        @Expose
        @SerializedName("Is_Read")
        private boolean Is_Read;
        @Expose
        @SerializedName("NotificationID")
        private String NotificationID;
        @Expose
        @SerializedName("Published_date")
        private String Published_date;
        @Expose
        @SerializedName("RecordId")
        private String RecordId;
        @Expose
        @SerializedName("RecordName")
        private String RecordName;

        public String getBody() {
            return Body;
        }

        public void setBody(String Body) {
            this.Body = Body;
        }

        public boolean getIs_Read() {
            return Is_Read;
        }

        public void setIs_Read(boolean Is_Read) {
            this.Is_Read = Is_Read;
        }

        public String getNotificationID() {
            return NotificationID;
        }

        public void setNotificationID(String NotificationID) {
            this.NotificationID = NotificationID;
        }

        public String getPublished_date() {
            return Published_date;
        }

        public void setPublished_date(String Published_date) {
            this.Published_date = Published_date;
        }

        public String getRecordId() {
            return RecordId;
        }

        public void setRecordId(String RecordId) {
            this.RecordId = RecordId;
        }

        public String getRecordName() {
            return RecordName;
        }

        public void setRecordName(String RecordName) {
            this.RecordName = RecordName;
        }
    }
}