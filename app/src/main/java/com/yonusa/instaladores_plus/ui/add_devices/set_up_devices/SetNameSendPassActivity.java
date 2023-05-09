package com.yonusa.instaladores_plus.ui.add_devices.set_up_devices;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.yonusa.instaladores_plus.R;
import com.yonusa.instaladores_plus.api.ApiConstants;
import com.yonusa.instaladores_plus.utilities.catalogs.SP_Dictionary;
import com.yonusa.instaladores_plus.utilities.socket.SocketTCP;

public class SetNameSendPassActivity extends AppCompatActivity implements SocketTCP.setResultTCP {
    private EditText et_Password, et_DeviceName;
    private String SSID;
    private SocketTCP socket;
    LottieAnimationView loader;
    SocketTCP.setResultTCP callback = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device_step3);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        SSID = intent.getStringExtra("SSID"); //if it's a string you stored.

        TextView tvWifiName = findViewById(R.id.tv_wifi_name);
        tvWifiName.setText(SSID);

        et_Password = findViewById(R.id.et_password);

        et_DeviceName = findViewById(R.id.et_name_device);
        loader = findViewById(R.id.loader_step3);
        Button btn_Send = findViewById(R.id.btn_send_register);
        btn_Send.setOnClickListener(v -> sendWifiRegister());

    }

    void sendWifiRegister() {

        loaderInteractionRestrict();
        String device_name = et_DeviceName.getText().toString();
        String password = et_Password.getText().toString();

        SharedPreferences prefs2 = getSharedPreferences(SP_Dictionary.USER_INFO, MODE_PRIVATE);
        String uuid = prefs2.getString("UUID", "No username defined");


        SharedPreferences prefs = getSharedPreferences("Datos_usuario", MODE_PRIVATE);
        String mqttHost = prefs.getString("MQTT_SERVER", "No username defined");
        String mqttPort = prefs.getString("MQTT_PORT", "No username defined");
        String mqttUser = prefs.getString("MQTT_USER", "No username defined");
        String mqttPass = prefs.getString("MQTT_PWD", "No username defined");
        String userId = prefs.getString("usuarioId", "No username defined");


        final String cmd = "CONFIG|" + SSID + "|" + password + "|" + mqttHost + "|" + mqttPort +
                "|" + mqttUser + "|" + mqttPass + "|" +
                uuid + "|" + device_name + "|" + userId;


        launch_register(cmd);

    }

    private void launch_register(String register_cmd){

        new Thread() {
            public void run() {
                try {

                    socket = SocketTCP.getSocketConnection(ApiConstants.CONFIG_WI_FI, true,callback);
                    socket.connectWithServer();
                    socket.SendRegister(register_cmd);
                    Thread.sleep(300);
                    String result = socket.receiveDataFromServer();

                    if (!result.isEmpty()) {
                        if (result.contains("CONFIG")) {//Si la respuesta es Success
                             runOnUiThread(() -> {
                                Log.d("CONFIG RESPONSE", "Contains successs: " + result);
                                toFinshScreen();
                            });

                        } else {
                            Log.i("Result don't contains", "CONFIG");
                            loaderInteractionEnable();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void toFinshScreen(){
        Intent i = new Intent(SetNameSendPassActivity.this, FinishAddingDeviceActivity.class);
        i.putExtra("SSID", SSID);
        loaderInteractionEnable();
        socket.disConnectWithServer();
        startActivity(i);
        finish();
    }

    public void loaderInteractionRestrict(){
        loader.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public void loaderInteractionEnable(){
        loader.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void onResultTCP(boolean b) {

    }
}
