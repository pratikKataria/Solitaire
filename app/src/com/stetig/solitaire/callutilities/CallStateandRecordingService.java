package com.stetig.solitaire.callutilities;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.stetig.solitaire.R;
import com.stetig.solitaire.api.Keys;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CallStateandRecordingService extends Service {
    int popUpFlagCount = 0;
    private MediaRecorder recorder = null;
    private File audiofile;
    private boolean recordstarted = false;
    String file_name;

    private static final String ACTION_IN = "android.intent.action.PHONE_STATE";
    private static final String ACTION_OUT = "android.intent.action.NEW_OUTGOING_CALL";

    IBinder binder = new LocalBinder();

    public class LocalBinder extends Binder {
        public CallStateandRecordingService getServerInstance() {
            return CallStateandRecordingService.this;
        }

        public void checked() {
            Log.e(getClass().getName(), "checked: " + "clicked ");
        }
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("StartService", "TService");
        final IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_OUT);
        filter.addAction(ACTION_IN);
        this.registerReceiver(new CallReceiver(), filter);
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
            startMyOwnForeground();
        else
            startForeground(1, new Notification());
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void startMyOwnForeground() {
        String NOTIFICATION_CHANNEL_ID = "com.mogli.tut";
        String channelName = "Background Service";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setContentTitle("App is running in background")
                .setSmallIcon(R.drawable.ic_small_icon)
                .setPriority(NotificationManager.IMPORTANCE_HIGH)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(2, notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean record = preferences.getBoolean("record", true);

        if (!record)
            return;

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("restartcallservice");
        broadcastIntent.setClass(this, Restarter.class);
        this.sendBroadcast(broadcastIntent);
    }

    private void startRecording(Context context, String number) {
//        Toast.makeText(context, "Recording started", Toast.LENGTH_SHORT).show();
        try {
            File sampleDir1 = new File(Environment.getExternalStorageDirectory() + File.separator + "solitaire");
            if (!sampleDir1.exists()) {
                if (!sampleDir1.mkdirs()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        try {
                            Files.createDirectory(sampleDir1.toPath());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Log.v("makeDir", "Error");
                }
            }
            File sampleDir2 = new File(sampleDir1.getAbsolutePath() + File.separator + "CallRecordings");
            if (!sampleDir2.exists()) {
                if (!sampleDir2.mkdirs()) {
                    Log.v("makeDir", "Error");
                }
            }
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            Date date = new Date();
            String currDateTime = formatter.format(date);

            file_name = "CallRecord_" + currDateTime + "_" + number + ".amr";
            try {
                audiofile = new File(sampleDir2, file_name);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (recorder != null) {
                    if (recordstarted) {
                        recorder.stop();
                        recorder.release();
                    }
                    recorder = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            recorder.setOutputFile(audiofile.getAbsolutePath());

            try {
                recorder.prepare();
                recorder.start();
                recordstarted = true;
            } catch (Exception e) {
                e.printStackTrace();
                //            Toast.makeText(context, "Unable to record Call :(", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void stopRecording(Context context) {
        Log.v("Recorderstop", "once");
        try {
            if (recordstarted) {
                recorder.stop();
                recordstarted = false;
                Toast.makeText(context, "Recording stopped", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getContactName(final String phoneNumber, Context context) {
        if (phoneNumber.length() == 0)
            return phoneNumber;
        ArrayList<String> correctedPhoneNumbers = correctPhoneNumber(phoneNumber);
        String contactName = "";
        String[] projection = new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME};
        Cursor cursor = null;
        for (int i = 0; i < correctedPhoneNumbers.size(); i++) {
            String correctedPhoneNumber = correctedPhoneNumbers.get(i);
            Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(correctedPhoneNumber));
            cursor = context.getContentResolver().query(uri, projection, null, null, null);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    contactName = cursor.getString(0);
                    break;
                }
            }
        }
        if (contactName.length() != 0)
            return contactName;
        else
            return phoneNumber;
    }

    private ArrayList<String> correctPhoneNumber(String phoneNumber) {
        ArrayList<String> numberList = new ArrayList<>();
        numberList.add(phoneNumber.substring(0, 3) + " " + phoneNumber.substring(3, 9) + " " + phoneNumber.substring(9));
        numberList.add("0" + phoneNumber.substring(3, 8) + " " + phoneNumber.substring(8));

        return numberList;
    }

    MyPhoneStateListener phoneListener = new MyPhoneStateListener();
    TelephonyManager telephonyManager;

    public abstract class PhonecallReceiver extends BroadcastReceiver {

        //The receiver will be recreated whenever android feels like it.  We need a static variable to remember data between instantiations

        private int lastState = TelephonyManager.CALL_STATE_IDLE;
        private Date callStartTime;
        private boolean isIncoming;
        private String savedNumber;  //because the passed incoming is only valid in ringing
        boolean outgoing = false;


        @Override
        public void onReceive(Context context, Intent intent) {
//        startRecording();
            telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);

            //We listen to two intents.  The new outgoing call only tells us of an outgoing call.  We use it to get the number.
            if (intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL")) {
                if (intent.getExtras() != null) {
                    if (outgoing)
                        outgoing = false;
                    else {
                        savedNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
                        Log.e(getClass().getName(), "onReceive: 257" + savedNumber);
                    }
                }

            } else {
                String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
                String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                int state = 0;
                if (stateStr.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                    state = TelephonyManager.CALL_STATE_IDLE;
                } else if (stateStr.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                    state = TelephonyManager.CALL_STATE_OFFHOOK;
                } else if (stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                    state = TelephonyManager.CALL_STATE_RINGING;
                }

                Log.d(getClass().getName(), "onReceive: 283 " + " real number recieverd  " + phoneListener.phoneNumber);

                onCallStateChanged(context, state, phoneListener.phoneNumber);
            }
        }

        //Derived classes should override these to respond to specific events of interest
        protected abstract void onIncomingCallReceived(Context ctx, String number, Date start);

        protected abstract void onIncomingCallAnswered(Context ctx, String number, Date start);

        protected abstract void onIncomingCallEnded(Context ctx, String number, Date start, Date end);

        protected abstract void onOutgoingCallStarted(Context ctx, String number, Date start);

        protected abstract void onOutgoingCallEnded(Context ctx, String number, Date start, Date end);

        protected abstract void onMissedCall(Context ctx, String number, Date start);

        //Deals with actual events

        //Incoming call-  goes from IDLE to RINGING when it rings, to OFFHOOK when it's answered, to IDLE when its hung up
        //Outgoing call-  goes from IDLE to OFFHOOK when it dials out, to IDLE when hung up
        public void onCallStateChanged(Context context, int state, String number) {
            if (lastState == state) {
                //No change, debounce extras
                return;
            }
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    isIncoming = true;
                    callStartTime = new Date();
                    savedNumber = number;
                    onIncomingCallReceived(context, number, callStartTime);
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    //Transition of ringing->offhook are pickups of incoming calls.  Nothing done on them
                    if (lastState != TelephonyManager.CALL_STATE_RINGING) {
                        isIncoming = false;
                        callStartTime = new Date();
                        onOutgoingCallStarted(context, number, callStartTime);
                    } else {
                        isIncoming = true;
                        callStartTime = new Date();
                        onIncomingCallAnswered(context, number, callStartTime);
                    }
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    //Went to idle-  this is the end of a call.  What type depends on previous state(s)
                    if (lastState == TelephonyManager.CALL_STATE_RINGING) {
                        //Ring but no pickup-  a miss
                        onMissedCall(context, number, callStartTime);
                    } else if (isIncoming) {
//                        stopRecording(context);
                        onIncomingCallEnded(context, number, callStartTime, new Date());
                    } else {
//                        stopRecording(context);
                        onOutgoingCallEnded(context, number, callStartTime, new Date());
                    }
                    break;
            }
            lastState = state;
        }

    }



    public class CallReceiver extends PhonecallReceiver {
        long callReceivedTime = 0;
        long callEndedTime = 0;

        @Override
        protected void onIncomingCallReceived(Context ctx, String number, Date start) {
            Log.d("onIncomingCallReceived", number + " " + start.toString());
        }

        @Override
        protected void onIncomingCallAnswered(Context ctx, String number, Date start) {
            Log.d("onIncomingCallAnswered", number + " " + start.toString());
            callReceivedTime = System.currentTimeMillis();
            startRecording(ctx, number);
        }

        @Override
        protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end) {
            Log.d("onIncomingCallEnded", number + " " + start.toString() + "\t" + end.toString());
            callEndedTime = System.currentTimeMillis();

            stopRecording(ctx);
            Intent intent = new Intent("call_ended");
            intent.setPackage(getPackageName());
            intent.putExtra("number", number);
            Log.e(getClass().getName(), "onIncomingCallEnded: 347 " + audiofile.getAbsolutePath() + "/" + audiofile.getName() + ".amr");
            intent.putExtra("call_record_path", audiofile.getAbsolutePath());
            intent.putExtra("fileName", file_name);
            intent.putExtra("commType", "Incall");
            intent.putExtra(Keys.CALL_RECEIVED_TIME, callReceivedTime);
            intent.putExtra(Keys.CALL_ENDED_TIME, callEndedTime);
            ctx.sendBroadcast(intent);
        }

        @Override
        protected void onOutgoingCallStarted(Context ctx, String number, Date start) {
            Log.d("onOutgoingCallStarted", number + " " + start.toString());

            callReceivedTime = System.currentTimeMillis();
            startRecording(ctx, number);
        }

        @Override
        protected void onOutgoingCallEnded(Context ctx, String number, Date start, Date end) {
//            Log.d("onOutgoingCallEnded", number + " " + start.toString() + "\t" + end.toString());
            stopRecording(ctx);
            callEndedTime = System.currentTimeMillis();
            Intent intent = new Intent("call_ended");
            intent.setPackage(getPackageName());
            intent.putExtra("number", number);
            intent.putExtra("call_record_path", audiofile.getAbsolutePath());
            intent.putExtra("fileName", file_name);
            intent.putExtra("commType", "OutCall");
            intent.putExtra(Keys.CALL_RECEIVED_TIME, callReceivedTime);
            intent.putExtra(Keys.CALL_ENDED_TIME, System.currentTimeMillis());
            ctx.sendBroadcast(intent);
        }

        @Override
        protected void onMissedCall(Context ctx, String number, Date start) {
            Log.e("onMissedCall", number + " " + start.toString());
//        PostCallHandler postCallHandler = new PostCallHandler(number, "janskd" , "")
        }

    }

    class MyPhoneStateListener extends PhoneStateListener {

        public Boolean phoneRinging = false;
        public String phoneNumber = "";

        public void onCallStateChanged(int state, String incomingNumber) {

            if (incomingNumber != null) {
                phoneNumber = incomingNumber;
            }

            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    Log.d("DEBUG", "IDLE " + incomingNumber);
                    phoneRinging = false;
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    Log.d("DEBUG", "OFFHOOK" + incomingNumber);
                    phoneRinging = false;
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    Log.d("DEBUG", "RINGING" + incomingNumber);
                    phoneNumber = incomingNumber;
                    if (phoneNumber != null && !phoneNumber.isEmpty()) {
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        sharedPreferences.edit().putString("lastNumber", phoneNumber).commit();
                    }
                    phoneRinging = true;
                    break;
            }
        }

    }
}
