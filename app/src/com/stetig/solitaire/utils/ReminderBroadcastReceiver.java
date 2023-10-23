package com.stetig.solitaire.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.stetig.solitaire.api.Keys;

public class ReminderBroadcastReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: 29-08-2020 add task id to notify
        Notifications.notify(context,"Reminder to complete an pending event", intent.getStringExtra(Keys.TASK_ID), intent.getStringExtra(Keys.TASK_TYPE), intent.getStringExtra(Keys.OPP_NAME));
    }


}
