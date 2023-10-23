package com.stetig.solitaire.utils;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.stetig.solitaire.api.Keys;

import java.util.Calendar;
import java.util.Date;

public class Alarm {
    public static void setAlarm(Context ctx, long time, String taskId, String type, String opportunityName) {
        Intent intent = new Intent(ctx, ReminderBroadcastReceiver.class);
        intent.putExtra(Keys.TASK_ID, taskId);
        intent.putExtra(Keys.TASK_TYPE, type);
        intent.putExtra(Keys.OPP_NAME, opportunityName);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ctx, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager am = (AlarmManager) ctx.getSystemService(Activity.ALARM_SERVICE);
// time of of next reminder. Unix time.
        long timeMs = 0;
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        Log.v("alarm", "" + cal.getTimeInMillis());
        Log.v("alarm", "" + time);
        am.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent);
    }
}
