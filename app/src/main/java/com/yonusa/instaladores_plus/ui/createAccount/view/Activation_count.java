package com.yonusa.instaladores_plus.ui.createAccount.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.yonusa.instaladores_plus.R;
import com.yonusa.instaladores_plus.api.ApiConstants;
import com.yonusa.instaladores_plus.api.ApiManager;
import com.yonusa.instaladores_plus.ui.createAccount.models.CountActivationRequest;
import com.yonusa.instaladores_plus.ui.login.models.LogIn_response;
import com.yonusa.instaladores_plus.ui.login.view.LogInActivity;
import com.yonusa.instaladores_plus.utilities.catalogs.ErrorCodes;
import com.yonusa.instaladores_plus.utilities.helpers.SP_Helper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class Activation_count extends Fragment {

    private OnFragmentInteractionListener mListener;
    private static final String TAG = Create_Account_Activity.class.getSimpleName();
    SP_Helper spH = new SP_Helper();
    Button btnActivate;
    TextView tvVerficationCode, tvEmail;
    Activity activity;
    Bundle data;
    ApiManager apiManager;
    LottieAnimationView loader;

    public Activation_count() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activation_count, container, false);
        activity = getActivity();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loader = view.findViewById(R.id.loader_activation_code);

        tvEmail = view.findViewById(R.id.tv_activation_count_description);
        data = getArguments();
        if (data != null){
            String tempText = tvEmail.getText().toString();
            tempText = tempText.concat(data.getString("email"));
            tvEmail.setText(tempText);
        }

        btnActivate = view.findViewById(R.id.activation_count_btn);
        tvVerficationCode = view.findViewById(R.id.editText_activation_code);

        btnActivate.setOnClickListener(v -> {
            String code = tvVerficationCode.getText().toString();
            if (code.equals("")){
                Log.e(TAG, "Verification code is empty");
                String text = getResources().getString(R.string.empty_verification_code);
                Toast.makeText(activity,text, Toast.LENGTH_SHORT).show();
            }else{
                loaderInteractionRestrict();
                Toast.makeText(activity, R.string.activation_code_inprocess, Toast.LENGTH_LONG).show();
                sendActivationCode(data.getString("uuid"), tvVerficationCode.getText().toString());
            }

        });

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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void sendActivationCode(String uuid, String code){
        CountActivationRequest countActivationR = new CountActivationRequest();
        countActivationR.setCodigoActivacion(code);
        countActivationR.setUsuarioId(uuid);
        apiManager = ApiConstants.getAPIManager();
        Call<LogIn_response> call = apiManager.CountActivation(countActivationR);

        call.enqueue(new Callback<LogIn_response>() {
            @Override
            public void onResponse(Call<LogIn_response> call, Response<LogIn_response> response) {
                if (response.body().getCodigo() == ErrorCodes.SUCCESS){
                    loaderInteractionEnable();
                    Toast.makeText(activity, R.string.success_activation_code, Toast.LENGTH_LONG).show();
                    spH.fillUserInfo(getContext(),response.body());
                    //SharedPreferences prefs = getContext().getSharedPreferences(SP_Dictionary.USER_INFO, getContext().MODE_PRIVATE);
                    //String name = prefs.getString(SP_Dictionary.USER_NAME, "No name defined");
                    showHome();
                }else if (response.body().getCodigo() == ErrorCodes.FAILURE){
                    loaderInteractionEnable();
                    Toast.makeText(activity, response.body().getMensaje(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LogIn_response> call, Throwable t) {
                Log.e("Send AC. error",t.getMessage());
                loaderInteractionEnable();
            }
        });
    }

    public void loaderInteractionRestrict(){
        loader.setVisibility(View.VISIBLE);
        ((Activity) getContext()).getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public void loaderInteractionEnable(){
        loader.setVisibility(View.GONE);
        ((Activity) getContext()).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public void showHome(){
        Intent homeIntent = new Intent(activity, LogInActivity.class);
        startActivity(homeIntent);
        activity.finish();
    }
}
