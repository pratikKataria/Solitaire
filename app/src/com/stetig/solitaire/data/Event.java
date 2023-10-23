package com.stetig.solitaire.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Event implements Parcelable {
    private String desc;
    private long time;
    private String taskId;
    private String opportunityId;

    public Event(String desc, long time) {
        this.desc = desc;
        this.time = time;
    }

    public Event(String desc, long time, String taskId) {
        this.desc = desc;
        this.time = time;
        this.taskId = taskId;
    }

    public Event(String desc, long time, String taskId, String opportunityId) {
        this.desc = desc;
        this.time = time;
        this.taskId = taskId;
        this.opportunityId = opportunityId;
    }


    protected Event(Parcel in) {
        desc = in.readString();
        time = in.readLong();
        taskId = in.readString();
        opportunityId = in.readString();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }


    public long getTime() {
        return time;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getOpportunityId() {
        return opportunityId;
    }

    public void setOpportunityId(String opportunityId) {
        this.opportunityId = opportunityId;
    }

    @Override
    public String toString() {
        return "Event{" +
                "desc='" + desc + '\'' +
                ", time=" + time +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(desc);
        dest.writeLong(time);
        dest.writeString(taskId);
        dest.writeString(opportunityId);
    }
}
