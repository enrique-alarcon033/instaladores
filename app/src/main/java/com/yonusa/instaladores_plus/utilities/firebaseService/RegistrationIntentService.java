package com.yonusa.instaladores_plus.utilities.firebaseService;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.microsoft.windowsazure.messaging.NotificationHub;
import com.yonusa.instaladores_plus.MainActivity;
import com.yonusa.instaladores_plus.utilities.catalogs.SP_Dictionary;

import java.util.concurrent.TimeUnit;

public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegIntentService";
    String FCM_token = null;

    private NotificationHub hub;

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String resultString = null;
        String regID = null;
        String storedToken = null;
        String uniqueId = null;

        SharedPreferences prefs = getSharedPreferences(SP_Dictionary.USER_INFO, MODE_PRIVATE);

        try {
            FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                @Override
                public void onSuccess(InstanceIdResult instanceIdResult) {
                    FCM_token = instanceIdResult.getToken();
                    prefs.edit().putString("FCMtoken", FCM_token ).apply();
                    Log.d(TAG, "FCM Registration Token: " + FCM_token);
                }
            });
            TimeUnit.SECONDS.sleep(1);

            FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnCompleteListener(task -> {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        prefs.edit().putString("UniqueId", token).apply();

                        // Log and toast
                        String msg = "DeviceId from Firebase: " + token;
                        Log.d(TAG, msg);
                        //Toast.makeText(LogInActivity.this, msg, Toast.LENGTH_SHORT).show();
                    });
            // Storing the registration ID that indicates whether the generated token has been
            // sent to your server. If it is not stored, send the token to your server.
            // Otherwise, your server should have already received the token.
            if (((regID=sharedPreferences.getString("registrationID", null)) == null)){

                NotificationHub hub = new NotificationHub(NotificationSettings.HubName,
                        NotificationSettings.HubListenConnectionString, this);
                Log.d(TAG, "Attempting a new registration with NH using FCM token : " + FCM_token);
                regID = hub.register(FCM_token).getRegistrationId();

                // If you want to use tags...
                // Refer to : https://azure.microsoft.com/documentation/articles/notification-hubs-routing-tag-expressions/
                // regID = hub.register(token, "tag1,tag2").getRegistrationId();


                resultString = "New NH Registration Successfully - RegId : " + regID;
                Log.d(TAG, resultString);


                prefs.edit().putString("registrationID", regID ).apply();

            }

            // Check to see if the token has been compromised and needs refreshing.
            else if ((storedToken=sharedPreferences.getString("FCMtoken", "")) != FCM_token) {

                NotificationHub hub = new NotificationHub(NotificationSettings.HubName,
                        NotificationSettings.HubListenConnectionString, this);
                Log.d(TAG, "NH Registration refreshing with token : " + FCM_token); // token de firebase para push notifications
                regID = hub.register(FCM_token).getRegistrationId();

                // If you want to use tags...
                // Refer to : https://azure.microsoft.com/documentation/articles/notification-hubs-routing-tag-expressions/
                // regID = hub.register(token, "tag1,tag2").getRegistrationId();

                resultString = "New NH Registration Successfully - RegId : " + regID;
                Log.d(TAG, resultString);

                sharedPreferences.edit().putString("registrationID", regID ).apply();
                sharedPreferences.edit().putString("FCMtoken", FCM_token ).apply();

            }

            else {
                resultString = "Previously Registered Successfully - RegId : " + regID;
            }
        } catch (Exception e) {
            Log.e(TAG, resultString="Failed to complete registration", e);
            // If an exception happens while fetching the new token or updating registration data
            // on a third-party server, this ensures that we'll attempt the update at a later time.
        }

        // Notify UI that registration has completed.
        if (MainActivity.isVisible) {
            //MainActivity.mainActivity.ToastNotify(resultString);
        }
    }

}