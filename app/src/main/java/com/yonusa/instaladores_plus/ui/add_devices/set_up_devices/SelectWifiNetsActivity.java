package com.yonusa.instaladores_plus.ui.add_devices.set_up_devices;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.yonusa.instaladores_plus.R;
import com.yonusa.instaladores_plus.api.ApiConstants;
import com.yonusa.instaladores_plus.ui.add_devices.controller.ListaWifiAdapter;
import com.yonusa.instaladores_plus.ui.add_devices.models.ListaWifi;
import com.yonusa.instaladores_plus.utilities.socket.SocketTCP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 *
 */
public class SelectWifiNetsActivity extends AppCompatActivity implements SocketTCP.setResultTCP {

    private static String TAG = SelectWifiNetsActivity.class.getSimpleName();
    ImageView ivAddDevices, ivCloseSession;
    private RecyclerView recyclerView;
    private ListaWifiAdapter adapter;
    private List<ListaWifi> wifiList;
    private Context context;
    private String mac;
    private SocketTCP socket;
    LottieAnimationView loader;
    SocketTCP.setResultTCP callback = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_device_step2);
        getSupportActionBar().hide();

        context = this;

        ivAddDevices = findViewById(R.id.iv_add_devices);
        ivAddDevices.setOnClickListener(v -> {
            Intent i = new Intent(context, SetNameSendPassActivity.class);
            startActivity(i);
            finish();
        });

        recyclerView = findViewById(R.id.rv_networks);

        wifiList = new ArrayList<>();
        Intent myIntent = new Intent(context, SetNameSendPassActivity.class);
        adapter = new ListaWifiAdapter(this, wifiList, myIntent);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        loader = findViewById(R.id.loader_step2);

        Button btn_Reloader = findViewById(R.id.btn_reloader);
        btn_Reloader.setOnClickListener(v -> launch_scann());
    }

    @Override
    protected void onResume() {
        super.onResume();
        launch_scann();
    }

    private void launch_scann(){
        loaderInteractionRestrict();
        wifiList.clear();

        new Thread() {
            public void run() {
                try {
                    socket = SocketTCP.getSocketConnection(ApiConstants.CONFIG_WI_FI, true,callback);
                    socket.connectWithServer();
                    socket.sendCMD1();
                    String result = socket.receiveDataFromServer();

                    if (!result.isEmpty()) {
                        if (!result.contains("NOT-FOUND")) {//Si la respuesta es Success

                            String[] network_array;
                            network_array = result.split("\\|");
                            mac = network_array[0];
                            String[] modified_networks = Arrays.copyOfRange(network_array, 0, network_array.length);
                            Log.i("Hijole"+modified_networks.toString(),"");
                            Arrays.sort(modified_networks);
                            runOnUiThread(() -> {
                                networkConstructor(network_array);
                            });

                        } else {
                            loaderInteractionEnable();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void networkConstructor(String[] array_networks) {
        int[] covers = new int[]{
                R.drawable.signal_low,
                R.drawable.signal_medium,
                R.drawable.signal_high,
                R.drawable.signal_full};

        for (int x = 1; x <= array_networks.length - 1; x++) {

            int signal_index = 0;

            String[] data = array_networks[x].split(",");

            int signal_strength = Integer.parseInt(data[1]) * -1;

            if (signal_strength > 80 && signal_strength <= 100) signal_index = 0;
            if (signal_strength > 70 && signal_strength <= 80) signal_index = 1;
            if (signal_strength > 50 && signal_strength <= 70) signal_index = 2;
            if (signal_strength > 0 && signal_strength <= 50) signal_index = 3;

            ListaWifi a = new ListaWifi(data[0], data[1], "", covers[signal_index]);

            wifiList.add(a);
        }
        loaderInteractionEnable();
        socket.disConnectWithServer();
        adapter.notifyDataSetChanged();
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
        Toast.makeText(context, "No se encuentran las redes", Toast.LENGTH_LONG).show();

    }
}
