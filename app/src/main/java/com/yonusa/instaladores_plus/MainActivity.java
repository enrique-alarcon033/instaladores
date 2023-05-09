package com.yonusa.instaladores_plus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.yonusa.instaladores_plus.ui.login.view.Loguin_new;
import com.yonusa.instaladores_plus.utilities.firebaseService.FirebaseCloudMessagingService;
import com.yonusa.instaladores_plus.utilities.firebaseService.RegistrationIntentService;


public class MainActivity extends AppCompatActivity {

    public static MainActivity mainActivity;
    public static Boolean isVisible = false;
    private static final String TAG = "MainActivity";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static String imei = null;
    private  static int SPLASH_TIME_OUT = 4000;

    private static final int REQUEST_CODE = 101;

    ImageView logo,logo2;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getActionBar().hide();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        logo = (ImageView) findViewById(R.id.img_logo);
        logo2 = (ImageView) findViewById(R.id.img_logo2);

        // Animación: Trasladar Elemento de Derecha a Izquierda
        TranslateAnimation an = new TranslateAnimation(1600.0f,  0.0f,  0.0f, 0.0f);
        an.setDuration(2000);
        logo.startAnimation(an);

        TranslateAnimation an2 = new TranslateAnimation(00.0f, 0.0f, 1600.0f, 0.0f);
        an2.setDuration(3000);
        logo2.startAnimation(an2);
        registerWithNotificationHubs();
        FirebaseCloudMessagingService.createChannelAndHandleNotifications(getApplicationContext());
        //myRegistrationToken();

        new Handler().postDelayed(new Runnable() {

            @Override

            public void run() {
                Intent homeIntent = new Intent(MainActivity.this, Loguin_new.class);
                startActivity(homeIntent);
                finish();
            }

        }, SPLASH_TIME_OUT);

    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();

            } else {
                Log.i(TAG, "This device is not supported by Google Play Services.");
                Toast.makeText(MainActivity.this, "This device is not supported by Google Play Services.", Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }

    public void registerWithNotificationHubs()
    {
        if (checkPlayServices()) {
            // Start IntentService to register this application with FCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission denied.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
