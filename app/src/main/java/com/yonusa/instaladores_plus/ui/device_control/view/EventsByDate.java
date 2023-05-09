package com.yonusa.instaladores_plus.ui.device_control.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.yonusa.instaladores_plus.R;
import com.yonusa.instaladores_plus.api.ApiConstants;
import com.yonusa.instaladores_plus.api.ApiManager;
import com.yonusa.instaladores_plus.ui.device_control.models.request.GetEventsByDateRequest;
import com.yonusa.instaladores_plus.ui.device_control.models.response.GetEventsByDateResponse;
import com.yonusa.instaladores_plus.ui.device_control.view.Adapters.eventsDateAdapter;
import com.yonusa.instaladores_plus.utilities.catalogs.ErrorCodes;
import com.yonusa.instaladores_plus.utilities.catalogs.SP_Dictionary;
import com.yonusa.instaladores_plus.utilities.helpers.GeneralHelpers;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsByDate extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = DeviceControlActivity.class.getSimpleName();
    private EditText startDate, endDate;
    private LottieAnimationView loader;
    private Button btnFindEvent;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ApiManager apiManager;
    private String cercaId,userId;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_by_date);

        Log.i(TAG, "On Create");
        setTitle("Events by date");
        getSupportActionBar().hide();

        cercaId = getIntent().getStringExtra("DEVICE_ID");

        prefs = getSharedPreferences(SP_Dictionary.USER_INFO, Context.MODE_PRIVATE);
        userId = prefs.getString(SP_Dictionary.USER_ID, "No userId defined");

        startDate = findViewById(R.id.et_date_start);
        startDate.setOnClickListener(this);

        endDate = findViewById(R.id.et_date_end);
        endDate.setOnClickListener(this);

        loader = findViewById(R.id.loader);

        btnFindEvent = findViewById(R.id.btn_event_date);
        btnFindEvent.setOnClickListener(this);

        recyclerView = findViewById(R.id.rv_date_events);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
    }

    private void showDatePickerDialog(boolean isStart) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance((datePicker, year, month, day) -> {
            final String selectedDate = year + " / " + (month+1) + " / " + day;
            if (isStart){
                startDate.setText(selectedDate);
            }else{
                endDate.setText(selectedDate);
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.et_date_start:
                showDatePickerDialog(true);
                break;
            case R.id.et_date_end:
                showDatePickerDialog(false);
                break;
            case R.id.btn_event_date:
                if (endDate.getText().toString().equals("") || startDate.getText().toString().equals("")){
                    GeneralHelpers.singleMakeAlert(this,getString(R.string.empty_dates),getString(R.string.empty_dates_description));
                }else{
                    loader.setVisibility(View.VISIBLE);
                    getEvents();
                }
                break;
        }
    }

    public void getEvents(){
        GetEventsByDateRequest eventsRequest = new GetEventsByDateRequest();
        eventsRequest.setCercaId(cercaId);
        eventsRequest.setFechaInicial(startDate.getText().toString());
        eventsRequest.setFechaFinal(endDate.getText().toString());
        eventsRequest.setUsuarioId(userId);
        apiManager = ApiConstants.getAPIManager();
        Call<GetEventsByDateResponse> call = apiManager.getEventsByDate(eventsRequest);
        call.enqueue(new Callback<GetEventsByDateResponse>() {
            @Override
            public void onResponse(Call<GetEventsByDateResponse> call, Response<GetEventsByDateResponse> response) {
                if (response.body().getCodigo() == ErrorCodes.SUCCESS){
                    showEventsInView(response.body());
                }else if (response.body().getCodigo() == ErrorCodes.FAILURE){
                    loader.setVisibility(View.GONE);
                    GeneralHelpers.singleMakeAlert(getApplicationContext(),getString(R.string.error_title), getString(R.string.error_getting_events).concat(" ").concat(response.body().getMensaje()));
                }
            }

            @Override
            public void onFailure(Call<GetEventsByDateResponse> call, Throwable t) {
                Log.e("User reg. error",t.getMessage());
                loader.setVisibility(View.GONE);
                GeneralHelpers.singleMakeAlert(getApplicationContext(),getString(R.string.error_title), getString(R.string.error_getting_events));
            }
        });
    }

    public void showEventsInView(GetEventsByDateResponse response){
        if (response.getHistorial().isEmpty()){
            loader.setVisibility(View.GONE);
            GeneralHelpers.singleMakeAlert(this, getString(R.string.empty_history_title), getString(R.string.empty_event_message));
        }else{
            loader.setVisibility(View.GONE);
            adapter = new eventsDateAdapter(response.getHistorial());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }
    }
}
