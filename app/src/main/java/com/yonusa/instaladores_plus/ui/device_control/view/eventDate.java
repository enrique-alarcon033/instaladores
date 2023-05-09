package com.yonusa.instaladores_plus.ui.device_control.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class eventDate extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    private EditText startDate, endDate;
    private LottieAnimationView loader;
    private Button btnFindEvent;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ApiManager apiManager;
    private String cercaId,userId;
    private SharedPreferences prefs;

    public eventDate() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_date, container, false);

        cercaId = getArguments().getString("cercaId");

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prefs = getContext().getSharedPreferences(SP_Dictionary.USER_INFO, Context.MODE_PRIVATE);
        userId = prefs.getString(SP_Dictionary.USER_ID, "No userId defined");

        startDate = view.findViewById(R.id.et_date_start);
        startDate.setOnClickListener(this);

        endDate = view.findViewById(R.id.et_date_end);
        endDate.setOnClickListener(this);

        loader = view.findViewById(R.id.loader);

        btnFindEvent = view.findViewById(R.id.btn_event_date);
        btnFindEvent.setOnClickListener(this);

        recyclerView = view.findViewById(R.id.rv_date_events);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());

    }

    private void showDatePickerDialog(boolean isStart) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance((datePicker, year, month, day) -> {
            // +1 because January is zero
            final String selectedDate = year + " / " + (month+1) + " / " + day;
            if (isStart){
                startDate.setText(selectedDate);
            }else{
                endDate.setText(selectedDate);
            }
        });

        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
                    GeneralHelpers.singleMakeAlert(getContext(),getString(R.string.empty_dates),getString(R.string.empty_dates_description));
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
                    GeneralHelpers.singleMakeAlert(getContext(),getString(R.string.error_title), getString(R.string.error_getting_events).concat(" ").concat(response.body().getMensaje()));
                }
            }

            @Override
            public void onFailure(Call<GetEventsByDateResponse> call, Throwable t) {
                Log.e("User reg. error",t.getMessage());
                loader.setVisibility(View.GONE);
                GeneralHelpers.singleMakeAlert(getContext(),getString(R.string.error_title), getString(R.string.error_getting_events));
            }
        });
    }

    public void showEventsInView(GetEventsByDateResponse response){
        if (response.getHistorial().isEmpty()){
            loader.setVisibility(View.GONE);
            GeneralHelpers.singleMakeAlert(getContext(), getString(R.string.empty_history_title), getString(R.string.empty_event_message));
        }else{
            loader.setVisibility(View.GONE);
            adapter = new eventsDateAdapter(response.getHistorial());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
