package com.stetig.solitaire.utils;

import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import com.stetig.solitaire.BuildConfig;
import com.stetig.solitaire.api.CommonClassForApi;
import com.stetig.solitaire.data.UpdateTokenReq;
import com.stetig.solitaire.data.UpdateTokenRes;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.salesforce.androidsdk.accounts.UserAccount;
import com.salesforce.androidsdk.app.SalesforceSDKManager;

import java.util.Map;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        //if the message contains data payload
        //It is a map of custom keyvalues
        //we can read it easily
//        if(remoteMessage.getData().size() > 0){
//            //handle the data message here
//        }
//
//        //getting the title and the body

//
//        Notifications.notify(getApplicationContext(),body, "", "");
//
//        //then here we can use the title and body to build a notification
//
//

        SharedPrefs.saveData(MyFirebaseMessagingService.this, SharedPrefs.NAVIGATE_TO, true);
        SharedPrefs.saveData(MyFirebaseMessagingService.this, SharedPrefs.IS_ACTIVE, true);

        String body = null;
        String title = remoteMessage.getNotification().getTitle();
        try {
            Log.e(getClass().getName(), "onMessageReceived: " + remoteMessage.getNotification().getTitle());

            body = remoteMessage.getNotification().getBody();
            Log.e(getClass().getName(), "onMessageReceived: " + body);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // [START_EXCLUDE]
            // There are two types of messages data messages and notification messages. Data messages are handled
            // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
            // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
            // is in the foreground. When the app is in the background an automatically generated notification is displayed.
            // When the user taps on the notification they are returned to the app. Messages containing both notification
            // and data payloads are treated as notification messages. The Firebase console always sends notification
            // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
            // [END_EXCLUDE]

            // TODO(developer): Handle FCM messages here.
            Map<String, String> data = remoteMessage.getData();
            String msgJson = data.get("body");

            Log.d("FCM", "From: " + remoteMessage.getFrom());


            // Check if message contains a data payload.
            if (remoteMessage.getData().size() > 0) {
                Log.d("TAG", "Message data payload: " + remoteMessage.getData());

            }

            // Check if message contains a notification payload.
            if (remoteMessage.getNotification() != null) {
                Log.d("TAG", "Message Notification Body: " + remoteMessage.getNotification().getBody());
            }

////            FCMbody fcMbody = new Gson().fromJson(msgJson, FCMbody.class);
//            Log.d("TAG", "Message Notification Body: " + fcMbody.toString());
//            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//            final SharedPreferences.Editor editor = preferences.edit();
//            editor.putString("fcm"+fcMbody.getId(), msgJson);
//            editor.commit();


            // Also if you intend on generating your own notifications as a result of a received FCM
            // message, here is where that should be initiated. See sendNotification method below.
            OpportunityNotifications.notify(getApplicationContext(), body, "", "", title);
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    @Override
    public void onNewToken(String token) {
        Log.d("FCM S", "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putString("fcmtoken", token);
        editor.apply();

        registerWithServer(token);
    }

    public void registerWithServer(String token) {

        Log.v("FCM S", "" + "register token");

        if (token.length() == 0)
            return;

        UserAccount account = SalesforceSDKManager.getInstance().getUserAccountManager().getCurrentUser();
        if (account == null) return;

        String salesuserid = account.getUsername();
        String authToken = "Bearer " + account.getAuthToken();


        CommonClassForApi.Companion.getInstance(null).updateTokenInSalesForce(updateTokenInSalesForce, new UpdateTokenReq(token, salesuserid), authToken);
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return model;
        } else {
            return manufacturer + " " + model;
        }
    }

    DisposableObserver<UpdateTokenRes> updateTokenInSalesForce = new DisposableObserver<UpdateTokenRes>() {
        @Override
        public void onNext(@NonNull UpdateTokenRes updateTokenReq) {
            Log.e(getClass().getName(), "onNext: " + updateTokenReq.getToken());
        }

        @Override
        public void onError(@NonNull Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    };
}
