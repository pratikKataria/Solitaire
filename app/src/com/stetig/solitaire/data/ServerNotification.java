package com.stetig.solitaire.data; /**
 * Created by Pratik Katariya on 14-10-2020
 */
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Pratik Katariya on 14-10-2020
 */
public class ServerNotification {

    @SerializedName("message")
    private String message;
    @SerializedName("NotificationList")
    private List<Notificationlist> notificationlist;
    @SerializedName("returnCode")
    private int returncode;

    public String remainder = "";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Notificationlist> getNotificationlist() {
        return notificationlist;
    }

    public void setNotificationlist(List<Notificationlist> notificationlist) {
        this.notificationlist = notificationlist;
    }

    public int getReturncode() {
        return returncode;
    }

    public void setReturncode(int returncode) {
        this.returncode = returncode;
    }

    public static class Notificationlist {
        @SerializedName("Body")
        private String body;
        @SerializedName("Is_Read")
        private boolean isRead;
        @SerializedName("NotificationID")
        private String notificationid;
        @SerializedName("oppId")
        private String oppid;
        @SerializedName("Oppname")
        private String oppname;
        @SerializedName("Published_date")
        private String publishedDate;
        @SerializedName("Title")
        private String title;
        @SerializedName("Type")
        private String type;
        @SerializedName("UserId")
        private String userid;
        public String remainder = "";

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public boolean getIsRead() {
            return isRead;
        }

        public void setIsRead(boolean isRead) {
            this.isRead = isRead;
        }

        public String getNotificationid() {
            return notificationid;
        }

        public void setNotificationid(String notificationid) {
            this.notificationid = notificationid;
        }

        public String getOppid() {
            return oppid;
        }

        public void setOppid(String oppid) {
            this.oppid = oppid;
        }

        public String getOppname() {
            return oppname;
        }

        public void setOppname(String oppname) {
            this.oppname = oppname;
        }

        public String getPublishedDate() {
            return publishedDate;
        }

        public void setPublishedDate(String publishedDate) {
            this.publishedDate = publishedDate;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }
    }
}