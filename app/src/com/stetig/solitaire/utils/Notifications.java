package com.stetig.solitaire.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.stetig.solitaire.R;
import com.stetig.solitaire.activity.MainActivity;
import com.stetig.solitaire.api.Keys;


public class Notifications {

    private static final int REMINDER_NOTIFICATION_ID = 2000;
    private static final int REMINDER_PENDING_INTENT_ID = 2001;
    private static final String REMINDER_NOTIFICATION_CHANNEL_ID = "reminder_notif_channel";

    public static void notify(Context context, String text, String taskID, String task, String opportunityName) {

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    REMINDER_NOTIFICATION_CHANNEL_ID,
                    "notification",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }

        /*     .setStyle(new NotificationCompat.BigTextStyle().bigText(
                context.getString(R.string.reminder_notification_body)))*/
        String optyName = opportunityName == null ? "" : opportunityName;
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, REMINDER_NOTIFICATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_small_icon)
                .setContentTitle("You have an new upcoming task - " + optyName)
                .setContentText(text)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context, taskID, task))
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }
        notificationManager.notify(REMINDER_NOTIFICATION_ID, notificationBuilder.build());

    }

    private static PendingIntent contentIntent(Context context, String taskID, String task) {

        if (taskID != null && task != null) {
            SharedPrefs.saveData(context, SharedPrefs.NAVIGATE_TO, true);
            String data = UpcommingNotificationPrefs.getStringData(context, taskID);
            NotificationPrefs.saveData(context, taskID, data);
            SharedPrefs.saveData(context, SharedPrefs.IS_ACTIVE, true);
        }

        Intent startActivityIntent;
        if (taskID != null && !taskID.isEmpty() && !task.equals("null")) {
            startActivityIntent = new Intent(context, MainActivity.class);
            startActivityIntent.putExtra("opId", taskID);
            startActivityIntent.putExtra(Keys.TASK_TYPE, task);
            startActivityIntent.putExtra(Keys.FROM_NOTIFICATION, true);
        } else {
            startActivityIntent = new Intent(context, MainActivity.class);
            startActivityIntent.putExtra("cal", "cal");
            startActivityIntent.putExtra(Keys.TASK_ID, taskID);
            startActivityIntent.putExtra(Keys.TASK_TYPE, task);
            startActivityIntent.putExtra(Keys.FROM_NOTIFICATION, true);
        }


        return PendingIntent.getActivity(
                context,
                REMINDER_PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_IMMUTABLE);
    }
}
