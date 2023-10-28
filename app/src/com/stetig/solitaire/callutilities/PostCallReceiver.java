package com.stetig.solitaire.callutilities;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.stetig.solitaire.activity.MainActivity;
import com.stetig.solitaire.api.Keys;
import com.stetig.solitaire.utils.Constant;
import com.stetig.solitaire.utils.SharedPrefs;

public class PostCallReceiver extends BroadcastReceiver {
    Context context;

    @Override
    public void onReceive(final Context context, Intent intent) {
        String action = intent.getAction();
        this.context = context;
        switch (action) {
            case "opp_id":
                break;
            case "call_ended":
                try {
                    String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

                    String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                } catch (Exception e) {
                    Log.e("TAG", " Exception " + e);
                }

                SharedPrefs.saveData(context, Keys.CALL_DETECTED, true);

                final String number = intent.getStringExtra("number");
                final String filePath = intent.getStringExtra("call_record_path");
                final String commType = intent.getStringExtra("commType");
                Log.e("PostCallReceiver", "onReceive: " + commType);
                final long callReceivedTime = intent.getLongExtra(Keys.CALL_RECEIVED_TIME, 0);
                final long callEndedTime = intent.getLongExtra(Keys.CALL_ENDED_TIME, 0);

                final String minutesSeconds = differenceResult(callEndedTime - callReceivedTime);
                final String seconds = differenceResultInSeconds(callEndedTime - callReceivedTime);

                SharedPrefs.saveData(context, SharedPrefs.RECORED_FILE_PATH, filePath);
                SharedPrefs.saveData(context, SharedPrefs.LAST_MOBILE_NUMBER, number);
                SharedPrefs.saveData(context, SharedPrefs.CALL_DURATION_SECONDS_MINUTES, minutesSeconds);
                SharedPrefs.saveData(context, SharedPrefs.CALL_DURATION_SECONDS, seconds);

                SharedPrefs.saveData(context, Keys.LAST_CALL_STATUS, commType);
                SharedPrefs.saveData(context, Keys.CALL_RECEIVED_TIME, callReceivedTime);
                SharedPrefs.saveData(context, Keys.CALL_ENDED_TIME, callEndedTime);

                Log.e(getClass().getName(), "onReceive: communication type" + commType);
                Log.e(getClass().getName(), "onReceive: mobile number" + number);
                Log.e(getClass().getName(), "Time Difference" + minutesSeconds);

                Intent postCall = new Intent(context, MainActivity.class);

                postCall.putExtra("oppId", "");
                postCall.putExtra("number", number);
                postCall.putExtra("commType", commType);
                postCall.putExtra(Constant.SHOW_POP_UP, true);
                postCall.putExtra(Constant.CALL_DURATION, differenceResult(callEndedTime - callReceivedTime));
                postCall.putExtra(Constant.CALL_DURATION_SECONDS, differenceResultInSeconds(callEndedTime - callReceivedTime));
                postCall.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(postCall);
                break;
        }
    }

    @SuppressLint("DefaultLocale")
    String differenceResult(Long time) {
        double x = time / 1000;

        double seconds = x % 60;
        x /= 60;
        double minutes = x % 60;
        x /= 60;
        int hours = (int) (x % 24);
        return ((int) minutes) + " mins " + ((int) seconds) + " secs";
    }

    @SuppressLint("DefaultLocale")
    String differenceResultInSeconds(Long time) {
        double x = time / 1000;

        double seconds = x % 60;
        x /= 60;
        double minutes = x % 60;
        x /= 60;


        double totalSeconds = seconds + (((int) minutes) * 60);
        return (((int) totalSeconds) + " secs");
    }
}