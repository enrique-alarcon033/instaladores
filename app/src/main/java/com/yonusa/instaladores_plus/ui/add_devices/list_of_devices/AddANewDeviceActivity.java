package com.yonusa.instaladores_plus.ui.add_devices.list_of_devices;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.yonusa.instaladores_plus.R;
import com.yonusa.instaladores_plus.api.ApiConstants;
import com.yonusa.instaladores_plus.api.ApiManager;
import com.yonusa.instaladores_plus.ui.add_devices.list_of_devices.request.GetConfigParamsRequest;
import com.yonusa.instaladores_plus.ui.add_devices.list_of_devices.response.GetConfigParamsResponse;
import com.yonusa.instaladores_plus.ui.add_devices.set_up_devices.GetYonusaWifiActivity;
import com.yonusa.instaladores_plus.utilities.catalogs.Constants;
import com.yonusa.instaladores_plus.utilities.catalogs.ErrorCodes;
import com.yonusa.instaladores_plus.utilities.catalogs.SP_Dictionary;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddANewDeviceActivity extends AppCompatActivity {

    ApiManager apiManager;
    Button btnWifi;
    Button btnMonitor;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_of_devices);
        getSupportActionBar().hide();

        btnWifi = findViewById(R.id.btn_to_wifi);
        btnWifi.setOnClickListener(v -> toWifi());

        btnMonitor = findViewById(R.id.btn_to_monitor);
        btnMonitor.setOnClickListener(v -> toMonitor());

    }

    private void toWifi() {

        getDeviceConfigParameters(Constants.WI_FI_MODEL_03);

    }

    private void toMonitor() {

        getDeviceConfigParameters(Constants.MONITOR_MODEL);

    }

    public void getDeviceConfigParameters(String model){
        //FIXME
        String mac = "00:00:00:00:00:00";

        GetConfigParamsRequest getConfigParamsRequest = new GetConfigParamsRequest();

        getConfigParamsRequest.setMAC(mac);
        getConfigParamsRequest.setModeloId(model);

        apiManager = ApiConstants.getAPIManager();

        Call<GetConfigParamsResponse> call = apiManager.getDeviceConfigParameters(getConfigParamsRequest);

        call.enqueue(new Callback<GetConfigParamsResponse>() {
            @Override
            public void onResponse(Call<GetConfigParamsResponse> call, Response<GetConfigParamsResponse> response) {

                int typeCode = response.body().getCodigo();
                switch (typeCode) {
                    case (ErrorCodes.SUCCESS):

                        String UUID = response.body().getUUID();

                        Intent intentAddDeviceStep1 = new Intent(AddANewDeviceActivity.this, GetYonusaWifiActivity.class);
                        intentAddDeviceStep1.putExtra("UUID", UUID);

                        String preferenceContainer = SP_Dictionary.USER_INFO;
                        SharedPreferences.Editor editor = getSharedPreferences(preferenceContainer, MODE_PRIVATE).edit();
                        editor.putString("UUID", UUID);

                        editor.apply();
                        intentAddDeviceStep1.putExtra("MODEL", model);
                        startActivity(intentAddDeviceStep1);
                        finish();
                        break;

                    case (ErrorCodes.FAILURE):
                        //TODO: Agregar alertas de error.
                        Toast.makeText(AddANewDeviceActivity.this, "Ocurrio un error.", Toast.LENGTH_SHORT).show();

                        break;

                    default:
                        Log.i("Peticion login", "Algo paso");
                }
            }
            @Override
            public void onFailure(Call<GetConfigParamsResponse> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
                //TODO: Agregar alerta de error de conexión.
            }
        });
    }

}
