package com.yonusa.instaladores_plus.ui.add_devices.set_up_devices;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.yonusa.instaladores_plus.R;

/**
 * conectarse a red Yonusa123
 */
public class GetYonusaWifiActivity extends AppCompatActivity {


    private TextView tv_title_step_1, tvWifiNameStep1;
    private ImageView ivAddWifi;
    private Button btnRefresh, navBarRightButton;
    private Context context;
    private Boolean locationStatus;
    private String model;
    private String UUID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_device_step_1);
        getSupportActionBar().hide();

        context = this;

        model = getIntent().getStringExtra("MODEL");
        UUID = getIntent().getStringExtra("UUID");

        tv_title_step_1 = findViewById(R.id.tv_title_step_1);
        tv_title_step_1.setText(R.string.title_step_1);

        tvWifiNameStep1 = findViewById(R.id.tv_wifi_name_step_1);
        tvWifiNameStep1.setText(R.string.yonusaNetworksName);
        ivAddWifi = findViewById(R.id.iv_add_wifi);

        btnRefresh = findViewById(R.id.btn_refresh);
        btnRefresh.setOnClickListener(v -> refreshWiFi());

        navBarRightButton = findViewById(R.id.navBarRightButton);
        navBarRightButton.setText(R.string.text_btn_next);
        navBarRightButton.setVisibility(View.GONE);

        navBarRightButton.setOnClickListener(v -> goToWiFiList());

        checkLocationPermission();

    }

    private void refreshWiFi(){
        if (locationStatus) {
            String wifi = getCurrentSsid(context);
            if (wifi.contains("YONUSA")) {
                tv_title_step_1.setText("Ya estás conectado a");
                ivAddWifi.setImageResource(R.drawable.ico_on_wifi);
                tvWifiNameStep1.setText(wifi);
                navBarRightButton.setVisibility(View.VISIBLE);
            } else {
                tvWifiNameStep1.setText("\"YONUSA\"");
            }
        } else {
            final AlertDialog dialog = new AlertDialog.Builder(context)
                    .setTitle(R.string.alert_title_atention)
                    .setMessage(R.string.alert_text_location_permissions)
                    .setPositiveButton(R.string.ok_alert_text, null)
                    .show();
            Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(view1 -> dialog.dismiss());

        }

    }

    private void goToWiFiList(){
        Intent i = new Intent(context, SelectWifiNetsActivity.class);
        i.putExtra("MODEL", model);
        i.putExtra("UUID", UUID);
        startActivity(i);
        finish();
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                new AlertDialog.Builder(this)
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.ok_alert_text, (dialogInterface, i) -> ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION))
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }

            locationStatus = false;
            return false;

        } else {
            locationStatus = true;
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {

                    locationStatus = true;

                }

            } else {
                locationStatus = false;

            }
        }
    }

    private String getCurrentSsid(Context context) {
        String ssid = "NA";
        try {

            ConnectivityManager connManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connManager.getActiveNetworkInfo();

            if (networkInfo.isConnected()) {
                final WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
                if (connectionInfo != null && !(connectionInfo.getSSID().equals(""))) {

                    ssid = connectionInfo.getSSID();

                    return ssid;
                }
            }
        } catch (Exception e) {
            Log.i("Exception", "Exception");
            new AlertDialog.Builder(this)
                    .setTitle(R.string.alert_title_error_get_ssid)
                    .setMessage(R.string.alert_text_error_get_ssid)
                    .setPositiveButton(R.string.ok_alert_text,null)
                    .show();
        }

        return ssid;
    }

}
