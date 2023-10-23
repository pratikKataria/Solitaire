package com.stetig.solitaire.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

public class CallDuration {

    static ArrayList<ContactInfo> contactInfoArrayList = new ArrayList<>();

    static public String getLastCallDetails(Context context) {
        contactInfoArrayList.clear();

        Uri contacts = CallLog.Calls.CONTENT_URI;
        try {
            Cursor managedCursor;
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                Bundle bundle = new Bundle();
                bundle.putInt(ContentResolver.EXTRA_TOTAL_COUNT, 2);
                bundle.putStringArray(ContentResolver.QUERY_ARG_SORT_COLUMNS, new String[]{CallLog.Calls.DEFAULT_SORT_ORDER});
                managedCursor = context.getContentResolver().query(contacts, null, bundle, null);
            } else
                managedCursor = context.getContentResolver().query(contacts, null, null, null, CallLog.Calls.DATE + " DESC limit 5;");

            int phoneNumberIndex = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
            int durationIndex = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
            int dateIndex = managedCursor.getColumnIndex(CallLog.Calls.DATE);
            int callTypeIndex = managedCursor.getColumnIndex(CallLog.Calls.TYPE);

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                int limit = 10;
                int count = 0;
                while (managedCursor.moveToNext()) {
                    String phoneNumber = managedCursor.getString(phoneNumberIndex);
                    int callTypeInt = Integer.parseInt(managedCursor.getString(callTypeIndex));
                    String callDate = new Date(Long.parseLong(managedCursor.getString(dateIndex))).toString();
                    String callDuration = managedCursor.getString(durationIndex);

                    String callType;
                    callType = "Non";
                    if (callTypeInt == CallLog.Calls.INCOMING_TYPE) callType = "Incoming";
                    if (callTypeInt == CallLog.Calls.OUTGOING_TYPE) callType = "Outgoing";
                    if (callTypeInt == CallLog.Calls.MISSED_TYPE) callType = "Missed";

                    ContactInfo contactInfo = new ContactInfo(
                            phoneNumber,
                            callDuration,
                            callType,
                            callDate);

                    contactInfoArrayList.add(contactInfo);

                    count++;
                    if (count == limit) break;
                }
            } else {
                while (managedCursor.moveToNext()) {
                    String phoneNumber = managedCursor.getString(phoneNumberIndex);
                    int callTypeInt = Integer.parseInt(managedCursor.getString(callTypeIndex));
                    String callDate = new Date(Long.parseLong(managedCursor.getString(dateIndex))).toString();
                    String callDuration = managedCursor.getString(durationIndex);

                    String callType;
                    callType = "Non";
                    if (callTypeInt == CallLog.Calls.INCOMING_TYPE) callType = "Incoming";
                    if (callTypeInt == CallLog.Calls.OUTGOING_TYPE) callType = "Outgoing";
                    if (callTypeInt == CallLog.Calls.MISSED_TYPE) callType = "Missed";

                    ContactInfo contactInfo = new ContactInfo(
                            phoneNumber,
                            callDuration,
                            callType,
                            callDate);

                    contactInfoArrayList.add(contactInfo);
                }
            }

            managedCursor.close();
        } catch (Exception e) {
//            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("Security Exception", "User denied call log permission");
            Log.e("Security Exception", e.getMessage());

            return "0 sec";
        }
        return "0 sec";
    }

    static public String getCallDurationByMobileNo(Context context, String mobileNo) {
        getLastCallDetails(context);
        String duration = "0";

        if (!contactInfoArrayList.isEmpty()) {
            for (ContactInfo ci : contactInfoArrayList) {
                String number = CountryCodeRemover.Companion.numberFormatter(ci.number);
                if (number.equals(mobileNo)) {
                    duration = ci.duration;
                    break;
                }
            }
        }

        return duration;
    }

    public static String getFormattedCallDuration(String duration) {
        String formattedDuration = "";
        try {
            int totalDuration = Integer.parseInt(duration);
            int minutes = (int) totalDuration / 60;
            int secs = (int) totalDuration % 60;

            if (minutes > 0) {
                formattedDuration = formattedDuration + minutes + (minutes > 1 ? " mins " : " min ");
            } else {
                formattedDuration = "0 min ";
            }
            formattedDuration = formattedDuration + secs + (secs > 1 ? " secs " : " sec");
        } catch (NumberFormatException e) {
            return formattedDuration;
        }

        return formattedDuration;
    }
}


class ContactInfo {
    String number;
    String duration;
    String incomingType;
    String date;

    public ContactInfo(String number, String duration, String incomingType, String date) {
        this.number = number;
        this.duration = duration;
        this.incomingType = incomingType;
        this.date = date;
    }

    @Override
    public String toString() {
        return "ContactInfo{" +
                "number='" + number + '\'' +
                ", duration='" + duration + '\'' +
                ", incomingType='" + incomingType + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
/*

    getLastCallDetails(context);
    String duration = "0";

        if (!contactInfoArrayList.isEmpty()) {

                for (ContactInfo ci : contactInfoArrayList)
                System.out.println(ci.toString() + "\n\n");

                for (ContactInfo ci : contactInfoArrayList) {
                System.out.println(ci.number + "===" + mobileNo + "\n\n");
                String number = CountryCodeRemover.Companion.numberFormatter(ci.number);
                if (number.equals(mobileNo)) {
                System.out.println(ci.toString());
                duration = ci.duration;
                break;
                }
                }

            *//*ContactInfo contactInfo = contactInfoArrayList.stream()
                    .filter((s) -> s.number.equals(mobileNo))
                    .findFirst().orElse(null);

            if (contactInfo != null) {
                duration = contactInfo.duration;
            }*//*
                }

                return duration;*/
