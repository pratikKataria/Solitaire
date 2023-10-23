package com.stetig.solitaire.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.util.Log;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Pratik Katariya on 02-10-2020
 */
public class GetInCommingNumber {

    public static String getLastCallDetails(Context context) {
        String phoneNumber = "";

        Uri contacts = CallLog.Calls.CONTENT_URI;
        try {

            Cursor managedCursor = context.getContentResolver().query(contacts, null, null, null, CallLog.Calls.DATE + " DESC limit 1;");

            int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
            int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
            int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
            int incomingtype = managedCursor.getColumnIndex(String.valueOf(CallLog.Calls.INCOMING_TYPE));

            while (managedCursor.moveToNext()) {
                String callType;
                String phNumber = managedCursor.getString(number);
                if (incomingtype == -1) {
                    callType = "incoming";
                } else {
                    callType = "outgoing";
                }
                String callDate = managedCursor.getString(date);
                String callDayTime = new Date(Long.valueOf(callDate)).toString();

                String callDuration = managedCursor.getString(duration);

                Log.e("GetInCommingNumber ", "getLastCallDetails: 208 " + phNumber + "  cal type " + callType);

                if (!phNumber.isEmpty()) {
                    phoneNumber = phNumber;
                }
            }
            managedCursor.close();

        } catch (Exception e) {
            Log.e("Security Exception", "User denied call log permission");
            return "";
        }
        return phoneNumber;
    }

    public static String getSharePrefsPhoneNumber(Context context) {
        String lastPhoneNumber = SharedPrefs.getStringData(context, SharedPrefs.LAST_MOBILE_NUMBER);
        return lastPhoneNumber == null ? "" : lastPhoneNumber;
    }

    public static String getNumberFromIntent(Intent context) {
        if (context == null) return "";

        String phoneNumber = context.getStringExtra("number");
        return phoneNumber == null ? " " : phoneNumber;
    }

    public static String getBulletProofNumber(Context context, Intent intent) {
        String phoneNumber = getLastCallDetails(context);

        if (phoneNumber == null) {
            phoneNumber = getSharePrefsPhoneNumber(context);
            if (phoneNumber.trim().isEmpty()) {
                phoneNumber = getNumberFromIntent(intent);
                if (phoneNumber.isEmpty()) {
                    return "";
                }
            }
        }

        if (phoneNumber.trim().isEmpty()) {
            phoneNumber = getSharePrefsPhoneNumber(context);
            if (phoneNumber.trim().isEmpty()) {
                phoneNumber = getNumberFromIntent(intent);
                if (phoneNumber.isEmpty()) {
                    return "";
                }
            }
        }
        return phoneNumber;
    }

    public static String callType(Context context) {
//        return SharedPrefs.getStringData(context, Keys.LAST_CALL_STATUS);
        return "";
    }

    public static long getLastCallDurationInSecs(Context context) {
        long _duration = -1;

        Uri contacts = CallLog.Calls.CONTENT_URI;
        try {
            Cursor managedCursor = context.getContentResolver().query(contacts, null, null, null, CallLog.Calls.DATE + " DESC limit 1;");
            int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
            int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
            while (managedCursor.moveToNext()) {
                _duration = Long.parseLong(managedCursor.getString(duration));
            }
        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().log("User denied call log permission");
            Log.e("Security Exception", "User denied call log permission");
            return _duration;
        }
        return _duration;
    }

    /*
     * format seconds into 12mins 20secs
     * */
    public static String getFormattedCallDuration(Context context, Intent intent) {
        long callDurationInSecs = getLastCallDurationInSecs(context);

        if (callDurationInSecs <= 0) {
            String callDurationString = intent != null ? intent.getStringExtra(Constant.CALL_DURATION) + "" : "";
            return callDurationString.equals("null") ? "" : callDurationString;
        }


        int minutes = (int) callDurationInSecs / 60;
        int secs = (int) callDurationInSecs % 60;
        String formattedDuration = "";

        if (minutes > 0) {
            formattedDuration = formattedDuration + minutes + (minutes > 1 ? " mins " : " min ");
        } else {
            formattedDuration = "0 min ";
        }


        formattedDuration = formattedDuration + secs + (secs > 1 ? " secs " : " sec");

        return formattedDuration;
    }

    public static String getTimeInSecondsFromString(String duration) {
        try {
            String[] durationSplit = duration.split(" ");
            String sMinutes = durationSplit[0];
            String sSeconds = durationSplit[2];
            int minutes = Integer.parseInt(sMinutes);
            int seconds = Integer.parseInt(sSeconds);
            return seconds + (minutes * 60) + "";

        } catch (Exception xe) {
            return " ";
        }
    }
}