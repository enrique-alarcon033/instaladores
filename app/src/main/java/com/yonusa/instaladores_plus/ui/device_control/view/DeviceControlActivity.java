package com.yonusa.instaladores_plus.ui.device_control.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yonusa.instaladores_plus.R;
import com.yonusa.instaladores_plus.api.ApiConstants;
import com.yonusa.instaladores_plus.api.ApiManager;
import com.yonusa.instaladores_plus.api.BaseResponse;
import com.yonusa.instaladores_plus.databinding.DeviceControlMainBinding;
import com.yonusa.instaladores_plus.mqtt.Publisher;
import com.yonusa.instaladores_plus.ui.device_control.models.request.GetDeviceControlsRequest;
import com.yonusa.instaladores_plus.ui.device_control.models.request.GetEventsByDateRequest;
import com.yonusa.instaladores_plus.ui.device_control.models.request.SetNewControlNameRequest;
import com.yonusa.instaladores_plus.ui.device_control.models.response.Controls;
import com.yonusa.instaladores_plus.ui.device_control.models.response.GetDeviceControlsResponse;
import com.yonusa.instaladores_plus.ui.device_control.models.response.GetEventsByDateResponse;
import com.yonusa.instaladores_plus.ui.device_control.view.Adapters.DeviceControlAdapter;
import com.yonusa.instaladores_plus.ui.view.view.userList.userAdministration;
import com.yonusa.instaladores_plus.utilities.catalogs.Constants;
import com.yonusa.instaladores_plus.utilities.catalogs.ErrorCodes;
import com.yonusa.instaladores_plus.utilities.catalogs.Mqtt_CMD;
import com.yonusa.instaladores_plus.utilities.catalogs.SP_Dictionary;
import com.yonusa.instaladores_plus.utilities.helpers.GeneralHelpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeviceControlActivity extends AppCompatActivity implements View.OnClickListener, eventDate.OnFragmentInteractionListener, DeviceControlAdapter.onDataControlListener {

    private static final String TAG = DeviceControlActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private GetDeviceControlsResponse deviceControlsResponse;
    private ApiManager apiManager;
    private ImageView userAdm;
    private TextView tvEventDesc, tvEventName, tvDate;
    private RelativeLayout rlControlDescription;
    private SharedPreferences prefs;
    private MediaPlayer mediaPlayer;
    private int lyControlHeight;
    private ArrayList<Controls> controlsResp = new ArrayList<>();
    private DeviceControlMainBinding binding;
    private String uuid;
    private Integer rol;
    private String cercaId;
    private String deviceName;
    private String model;
    private String mac;
    private Boolean status, isLongPress = false;
    private SimpleDateFormat sdfYMDTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy / MM / dd");
    private Context context;


    enum ControlOptions {
        SET_DATE(9);
        public final int option;

        ControlOptions(int option) {
            this.option = option;
        }

        int getValue() {
            return option;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_control_main);
        this.context = this;

        binding = DataBindingUtil.setContentView(this, R.layout.device_control_main);

        prefs = this.getSharedPreferences(SP_Dictionary.USER_INFO, Context.MODE_PRIVATE);

        Log.i(TAG, "On Create");
        setTitle("Device control");
        getSupportActionBar().hide();

        uuid = getIntent().getStringExtra("USER_ID");
        rol = getIntent().getIntExtra("USER_ROL", 1);
        model = getIntent().getStringExtra("MODEL_ID");
        cercaId = getIntent().getStringExtra("DEVICE_ID");
        deviceName = getIntent().getStringExtra("DEVICE_NAME");
        mac = getIntent().getStringExtra("DEVICE_MAC");
        status = getIntent().getBooleanExtra("DEVICE_STATUS", false);

        mediaPlayer = MediaPlayer.create(this, R.raw.opendoor);

        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        lyControlHeight = (point.y / 5);

        getControls();

        userAdm = findViewById(R.id.iv_user_configuration);
        userAdm.setOnClickListener(this);

        rlControlDescription = findViewById(R.id.relative_control_description);
        rlControlDescription.setOnClickListener(this);

        recyclerView = findViewById(R.id.recycler_device_control);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this, 2);
        adapter = new DeviceControlAdapter(controlsResp, status, this, lyControlHeight);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        statusCheck();
    }

    @Override
    public void onClick(View v) {
        if ((deviceControlsResponse.getControles() != null || !deviceControlsResponse.getControles().isEmpty()) && !isLongPress) {
            switch (v.getId()) {
                case R.id.iv_user_configuration:
                    Intent userAdmin = new Intent(this, userAdministration.class);
                    userAdmin.putExtra("DEVICE_ID", cercaId);
                    startActivity(userAdmin);
                    break;
                case R.id.relative_control_description:
                    Intent intent = new Intent(this, EventsByDate.class);
                    intent.putExtra("DEVICE_ID", cercaId);
                    startActivity(intent);
                    break;
            }
        } else {
            Toast.makeText(this, "No se pueden mostrar dispositivos", Toast.LENGTH_SHORT).show();
        }
    }

    public void changeStatusControl(int controld) {
        if ((deviceControlsResponse.getControles() != null || !deviceControlsResponse.getControles().isEmpty()) && !isLongPress) {
            mediaPlayer.start();
            if (deviceControlsResponse.getControles().get(controld).getEstadoPermiso()) {
                changeStatusControl(deviceControlsResponse.getControles().get(controld));
            } else {
                Toast.makeText(this, getString(R.string.action_denied), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void changeNameControl(String aliasControl, int controlId) {
        final EditText edittext = new EditText(this);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getString(R.string.rename_control_title).concat(" ").concat(aliasControl));
        alert.setMessage(getString(R.string.new_control_name_description));

        alert.setView(edittext);

        alert.setPositiveButton(getString(R.string.save), (dialog, whichButton) -> {
            String newNameControl = edittext.getText().toString();
            if (newNameControl.equals("")) {
                Toast.makeText(this, getString(R.string.invalid_control_name), Toast.LENGTH_SHORT).show();
            } else {
                setNewControlName(newNameControl, controlId);
            }

        });

        alert.setNegativeButton(getString(R.string.back), (dialog, whichButton) -> {
        });

        alert.show();

        isLongPress = false;
    }

    public void setNewControlName(String name, int controlId) {
        SetNewControlNameRequest newNamecontrol = new SetNewControlNameRequest();
        newNamecontrol.setCercaId(cercaId);
        newNamecontrol.setControlId(controlId);
        newNamecontrol.setNuevoAliasControl(name);
        newNamecontrol.setUsuarioId(uuid);

        apiManager = ApiConstants.getAPIManager();
        Call<BaseResponse> call = apiManager.setControlName(newNamecontrol);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCodigo() == ErrorCodes.SUCCESS) {
                        Toast.makeText(DeviceControlActivity.this, getString(R.string.success_rename_control), Toast.LENGTH_SHORT).show();
                        getControls();
                    } else {
                        GeneralHelpers.singleMakeAlert(DeviceControlActivity.this, getString(R.string.error_title), response.body().getMensaje());
                    }
                } else {
                    GeneralHelpers.singleMakeAlert(DeviceControlActivity.this, getString(R.string.error_title), getString(R.string.an_error_ocurred_text));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                GeneralHelpers.singleMakeAlert(DeviceControlActivity.this, getString(R.string.error_title), getString(R.string.an_error_ocurred_text));
            }
        });

    }

    public void changeStatusControl(Controls control) {
        sendMqttMessage(control.getControlId(), control.getEstadoControl());
        if (control.getEstadoControl()) {
            control.setEstadoControl(false);
            updateView();
        } else {
            control.setEstadoControl(true);
            updateView();
            if (control.getControlId() == Constants.DOOR_ID) {
                Toast.makeText(DeviceControlActivity.this, getString(R.string.door_on_message_seconds), Toast.LENGTH_LONG).show();
                new doorInteraction(control).start();
            } else {

            }
        }
    }

    public void getControls() {
        GetDeviceControlsRequest getDiv = new GetDeviceControlsRequest();
        getDiv.setCercaId(cercaId);
        getDiv.setUsuarioId(uuid);
        apiManager = ApiConstants.getAPIManager();
        Call<GetDeviceControlsResponse> call = apiManager.getDeviceControls(getDiv);
        call.enqueue(new Callback<GetDeviceControlsResponse>() {
            @Override
            public void onResponse(Call<GetDeviceControlsResponse> call, Response<GetDeviceControlsResponse> response) {
                if (response.body().getCodigo() == ErrorCodes.SUCCESS) {
                    deviceControlsResponse = response.body();
                    showControlsView(deviceControlsResponse);
                } else if (response.body().getCodigo() == ErrorCodes.FAILURE) {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_get_controls), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GetDeviceControlsResponse> call, Throwable t) {
                Log.e("User reg. error", t.getMessage());
                Toast.makeText(getApplicationContext(), getString(R.string.error_get_controls), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showControlsView(GetDeviceControlsResponse controlsResponse) {

        if (controlsResponse.getUltimoEvento().getTextoMostrar() != null && controlsResponse.getUltimoEvento().getNombre() != null
                && controlsResponse.getUltimoEvento().getApellidos() != null && controlsResponse.getUltimoEvento().getFechaRegistroDato() != null) {

            tvEventDesc = findViewById(R.id.tv_control_description);
            tvEventDesc.setText(controlsResponse.getUltimoEvento().getTextoMostrar());

            String nombreEvento = controlsResponse.getUltimoEvento().getNombre().concat(" " + controlsResponse.getUltimoEvento().getApellidos());
            tvEventName = findViewById(R.id.tv_control_name);
            tvEventName.setText(nombreEvento);


            SimpleDateFormat output = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
            Date d = null;
            try {
                // TODO: 16/06/2020 change time source
                d = sdfYMDTime.parse(controlsResponse.getUltimoEvento().getFechaRegistroDato());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String formattedTime = output.format(d);

            tvDate = findViewById(R.id.tv_control_date);
            tvDate.setText(formattedTime);
        }
        controlsResp.clear();
        if (controlsResponse.getControles() != null) {
            for (Controls control : controlsResponse.getControles()) {
                controlsResp.add(control);
            }
            updateView();
        } else {
            Toast.makeText(this, getString(R.string.empty_control), Toast.LENGTH_LONG).show();
        }
    }

    public void updateView() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    class doorInteraction extends Thread {

        Controls control;

        public doorInteraction(Controls control) {
            this.control = control;
        }

        @Override
        public void run() {
            try {
                //TODO
                Thread.sleep(5000);
                runOnUiThread(() -> {
                    mediaPlayer.start();
                    sendMqttMessage(control.getControlId(), control.getEstadoControl());
                    control.setEstadoControl(false);
                    updateView();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void dataJunit(String mac, String uuid) {
        this.mac = mac;
        this.uuid = uuid;


    }

    public void sendMqttMessage(int controlId, boolean isOn) { //CMD|MacAddress|userId
        String msg = "";
        switch (controlId) {
            case 1:
                if (!isOn) {
                    msg = Mqtt_CMD.CMD_FENCE_ON;
                } else {
                    msg = Mqtt_CMD.CMD_FENCE_OFF;
                }
                break;
            case 2:
                if (!isOn) {
                    msg = Mqtt_CMD.CMD_PANIC_ON;
                } else {
                    msg = Mqtt_CMD.CMD_PANIC_OFF;
                }
                break;
            case 3:
                if (!isOn) {
                    msg = Mqtt_CMD.CMD_DOOR_OPEN;
                } else {
                    msg = Mqtt_CMD.CMD_DOOR_CLOSE;
                }
                break;
            case 4:
                if (!isOn) {
                    msg = Mqtt_CMD.CMD_LIGHT_ON;
                } else {
                    msg = Mqtt_CMD.CMD_LIGHT_OFF;
                }
                break;
            case 5:
                if (!isOn) {
                    msg = Mqtt_CMD.CMD_AUX_1_ON;
                } else {
                    msg = Mqtt_CMD.CMD_AUX_1_OFF;
                }
                break;
            case 6:
                if (!isOn) {
                    msg = Mqtt_CMD.CMD_AUX_2_ON;
                } else {
                    msg = Mqtt_CMD.CMD_AUX_2_OFF;
                }
                break;
            case 7:
                if (!isOn) {
                    msg = Mqtt_CMD.CMD_PANEL_ON;
                } else {
                    msg = Mqtt_CMD.CMD_PANEL_OFF;
                }
                break;
            case 8:
                if (!isOn) {
                    msg = Mqtt_CMD.CMD_ZONE_ON;
                } else {
                    msg = Mqtt_CMD.CMD_ZONE_OFF;
                }
                break;
            case 9:
                if (!isOn) {
                    msg = Mqtt_CMD.CMD_DATE_SET;
                } else {
                    msg = Mqtt_CMD.CMD_DATE_ERASE;
                }
                break;
        }

        msg = msg.concat("|").concat(mac).concat("|").concat(uuid);
        if (msg.contains("DATE")) {
            String currentDay = getCurrenDateWithTime();
            msg = msg.concat("|").concat(getCurrenDateWithTime());
            Log.d(TAG, "mensaje enviado: " + msg);
        }

        Publisher publisher = new Publisher();
        publisher.SendMessage(this, msg, mac);
    }

    private String getCurrenDateWithTime() {
        return sdfYMDTime.format(new Date());
    }

    private String getCurrentDate() {
        return sdfYMD.format(new Date());
    }

    public void statusCheck() {
        if (!status) {
            GeneralHelpers.singleMakeAlert(this, getString(R.string.conection_title), getString(R.string.conection_message).concat(" ").concat(deviceName));
        }
        if (rol == 1) {
            userAdm.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDataControlClick(int position, boolean isLongPressAction) {
        if (isLongPressAction) {
            //Toast.makeText(this, "LongPress al control: " + this.controlsResp.get(position).getAliasControl(), Toast.LENGTH_SHORT).show();
            changeNameControl(this.controlsResp.get(position).getAliasControl(), this.controlsResp.get(position).getControlId());
        } else {
            String nextStatus = "";
            if (this.controlsResp.get(position).getEstadoControl()) {
                nextStatus = "OFF";
            } else {
                nextStatus = "ON";
            }
            Toast.makeText(this, this.controlsResp.get(position).getAliasControl().concat(" : ").concat(nextStatus), Toast.LENGTH_SHORT).show();
            changeStatusControl(position);
        }

    }

    public void onClickSetCurrentDate(View v) {
        final ControlOptions controlOptions = ControlOptions.SET_DATE;

        Log.d(TAG, "onClickGetCurrentDate: ");
        getEvents(controlOptions);
    }

    public void getEvents(ControlOptions controlOptions) {

        new AlertDialog.Builder(context)
                .setTitle(R.string.dev_control_event_dialog_title)
                .setMessage(R.string.dev_control_event_dialog_msg)
                .setCancelable(true)
                .setPositiveButton(context.getString(R.string.ok_alert_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendMqttMessage(controlOptions.getValue(), true);
                        GetEventsByDateRequest eventsRequest = new GetEventsByDateRequest();
                        eventsRequest.setCercaId(cercaId);
                        eventsRequest.setFechaInicial(getCurrentDate());
                        eventsRequest.setFechaFinal(getCurrentDate());
                        eventsRequest.setUsuarioId(uuid);
                        apiManager = ApiConstants.getAPIManager();
                        Call<GetEventsByDateResponse> call = apiManager.getEventsByDate(eventsRequest);
                        call.enqueue(new Callback<GetEventsByDateResponse>() {
                            @Override
                            public void onResponse(Call<GetEventsByDateResponse> call, Response<GetEventsByDateResponse> response) {
                                if (response.body().getCodigo() == ErrorCodes.SUCCESS) {
                                    //TODO get current
                                    String date = response.body()
                                            .getHistorial()
                                            .get(response.body()
                                                    .getHistorial().size() - 1)
                                            .getFechaRegistroDato();
                                    Log.d(TAG, "onResponse: " + date);
                                    tvDate.setText(date);
                                    GeneralHelpers.singleMakeAlert(context, getString(R.string.set_data_title), "");
                                } else if (response.body().getCodigo() == ErrorCodes.FAILURE) {

                                    GeneralHelpers.singleMakeAlert(context,
                                            getString(R.string.error_title),
                                            getString(R.string.error_getting_events).concat(" ")
                                                    .concat(response.body().getMensaje() != null ? response.body().getMensaje() : "No se pudo conectar"));
                                }
                            }

                            @Override
                            public void onFailure(Call<GetEventsByDateResponse> call, Throwable t) {
                                Log.e("User reg. error", t.getMessage());
                                GeneralHelpers.singleMakeAlert(context, getString(R.string.error_title), getString(R.string.error_getting_events));
                            }
                        });


                    }

                })
                .setNegativeButton(context.getString(R.string.cancel_alert_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GeneralHelpers.singleMakeAlert(context, getString(R.string.date_not_updated_title), "");
                    }
                })
                .show();

    }


}
