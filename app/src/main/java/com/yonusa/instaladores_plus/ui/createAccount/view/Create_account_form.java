package com.yonusa.instaladores_plus.ui.createAccount.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.airbnb.lottie.LottieAnimationView;
import com.hbb20.CountryCodePicker;
import com.yonusa.instaladores_plus.R;
import com.yonusa.instaladores_plus.api.ApiConstants;
import com.yonusa.instaladores_plus.api.ApiManager;
import com.yonusa.instaladores_plus.ui.createAccount.models.RegistrarUsuarioRequest;
import com.yonusa.instaladores_plus.ui.createAccount.models.RegistrarUsuarioResponse;
import com.yonusa.instaladores_plus.ui.createAccount.models.VerificationCodeRequest;
import com.yonusa.instaladores_plus.ui.createAccount.models.VerificationCodeResponse;
import com.yonusa.instaladores_plus.utilities.Validations;
import com.yonusa.instaladores_plus.utilities.catalogs.ErrorCodes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class Create_account_form extends Fragment{

    private OnFragmentInteractionListener mListener;
    RegistrarUsuarioRequest regUser = new RegistrarUsuarioRequest();
    private static final String TAG = Create_Account_Activity.class.getSimpleName();
    private Activity activity;
    private EditText name, lastName, email, passwrd, confPassword, cellPhone;
    private CountryCodePicker countryCode;
    private Button createAcBtn, mostrarPswrd, mostrarConfirmPswrd, btnShowPrivacy;
    private CheckBox chkPrivacy;
    ApiManager apiManager;
    LottieAnimationView loader;

    public Create_account_form() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_account_form, container, false);

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

        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int textViewHeight = (point.y/14);

        loader = view.findViewById(R.id.loader_create_form);

        name = view.findViewById(R.id.nombre_editText_cc);
        ViewGroup.LayoutParams nameParams = name.getLayoutParams();
        nameParams.height = textViewHeight;

        lastName = view.findViewById(R.id.apellidos_editText_cc);
        ViewGroup.LayoutParams lastNameParams = lastName.getLayoutParams();
        lastNameParams.height = textViewHeight;

        email = view.findViewById(R.id.email_editText_cc);
        ViewGroup.LayoutParams emailParams = email.getLayoutParams();
        emailParams.height = textViewHeight;

        passwrd = view.findViewById(R.id.passwrd_editText_cc);
        ViewGroup.LayoutParams passwordParams = passwrd.getLayoutParams();
        passwordParams.height = textViewHeight;

        confPassword = view.findViewById(R.id.passwrd_confirm_editText_cc);
        ViewGroup.LayoutParams confPasswordParams = confPassword.getLayoutParams();
        confPasswordParams.height = textViewHeight;

        cellPhone = view.findViewById(R.id.celphone_editText_cc);
        ViewGroup.LayoutParams cellPhoneParams = cellPhone.getLayoutParams();
        cellPhoneParams.height = textViewHeight;

        countryCode = view.findViewById(R.id.phoneCodeInput);
        ViewGroup.LayoutParams countryCodeParams = countryCode.getLayoutParams();
        countryCodeParams.height = textViewHeight;

        createAcBtn = view.findViewById(R.id.create_account_btn);
        ViewGroup.LayoutParams createAcBtnParams = createAcBtn.getLayoutParams();
        createAcBtnParams.height = textViewHeight;

        chkPrivacy = view.findViewById(R.id.check_privacy);
        ViewGroup.LayoutParams chkPrivacyParams = chkPrivacy.getLayoutParams();
        chkPrivacyParams.height = textViewHeight;

        btnShowPrivacy = view.findViewById(R.id.btn_privacy);

        mostrarPswrd = view.findViewById(R.id.btn_show_pass_cc);
        mostrarPswrd.setTag("hide");
        mostrarPswrd.setOnClickListener(View -> showPassword() );

        mostrarConfirmPswrd = view.findViewById(R.id.btn_show_pass_confirm_cc);
        mostrarConfirmPswrd.setTag("hide");
        mostrarConfirmPswrd.setOnClickListener(View -> showPasswordConfirm());

        activity = getActivity();

        btnShowPrivacy.setOnClickListener(v -> {
            showPrivacyPolicy();
        });

        createAcBtn.setOnClickListener(View -> {
            boolean complete = validarDatos(name.getText().toString(), lastName.getText().toString(),email.getText().toString(),passwrd.getText().toString(), confPassword.getText().toString(), cellPhone.getText().toString());
            if (complete){
                regUser.setNombre(name.getText().toString());
                regUser.setApellidos(lastName.getText().toString());
                regUser.setEmail(email.getText().toString());
                regUser.setPassword(passwrd.getText().toString());
                String telefono = countryCode.getSelectedCountryCodeWithPlus().concat(cellPhone.getText().toString());
                regUser.setTelefono(telefono);
                loaderInteractionRestrict();
                registerUser(regUser);
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

    public boolean validarDatos(String name, String lastName, String email, String password, String confPassword, String celPhone){
        String text;
        if (name.equals("")){
            Log.e(TAG, "Empty name error");
            text = getResources().getString(R.string.empty_name);
            Toast.makeText(activity,text, Toast.LENGTH_SHORT).show();
            return false;
        }else if (lastName.equals("")){
            Log.e(TAG, "Empty lastName error");
            text = getResources().getString(R.string.empty_lastname);
            Toast.makeText(activity,text, Toast.LENGTH_SHORT).show();
            return false;
        }else if (email.equals("")){
            Log.e(TAG, "Email not valid error");
            text = getResources().getString(R.string.enter_valid_email);
            Toast.makeText(activity,text, Toast.LENGTH_SHORT).show();
            return false;
        }else if (password.equals("") || !Validations.isValidPassword(password)){
            Log.e(TAG, "Password not valid error");
            text = getResources().getString(R.string.invalid_password_lenght);
            Toast.makeText(activity,text, Toast.LENGTH_LONG).show();
            return false;
        }else if (confPassword.equals("")){
            Log.e(TAG, "Password confirm is empty valid error");
            text = getResources().getString(R.string.empty_password_confirm);
            Toast.makeText(activity,text, Toast.LENGTH_SHORT).show();
            return false;
        }else if (!confPassword.equals(password)){
            Log.e(TAG, "Passwords are not equals error");
            text = getResources().getString(R.string.passwords_not_equals);
            Toast.makeText(activity,text, Toast.LENGTH_SHORT).show();
            return false;
        }else if (celPhone.equals("")){
            Log.e(TAG, "Empty phone number error");
            text = getResources().getString(R.string.empty_phone);
            Toast.makeText(activity,text, Toast.LENGTH_SHORT).show();
            return false;
        }else if (!chkPrivacy.isChecked()){
            Log.e(TAG, "No privacy policy accept error");
            text = getResources().getString(R.string.privacy_policy_not_checked);
            Toast.makeText(activity,text, Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }

    public void showPassword(){
        int pass_length = passwrd.getText().length();

        if(mostrarPswrd.getTag() == "hide"){
            passwrd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            mostrarPswrd.setTag("show");
            passwrd.setSelection(pass_length);
        } else {
            passwrd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            mostrarPswrd.setTag("hide");
            passwrd.setSelection(pass_length);
        }
    }

    public void showPasswordConfirm(){
        int pass_length = confPassword.getText().length();

        if(mostrarConfirmPswrd.getTag() == "hide"){
            confPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            mostrarConfirmPswrd.setTag("show");
            confPassword.setSelection(pass_length);
        } else {
            confPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            mostrarConfirmPswrd.setTag("hide");
            confPassword.setSelection(pass_length);
        }
    }

    public void showActivationcountFragment(String uuid){
        Bundle data  = new Bundle();
        data.putString("email",regUser.getEmail());
        data.putString("uuid",uuid);
        Fragment frmgmtn = new Activation_count();
        frmgmtn.setArguments(data);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,frmgmtn);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void showPrivacyPolicy(){
        Fragment frmgmtn = new Privacy_policy_fragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,frmgmtn);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void registerUser(RegistrarUsuarioRequest regUser){
        //Inicializa la construcción de la petición para que "apiManager" traiga la URL Base
        apiManager = ApiConstants.getAPIManager();

        //Una vez que apiManager es asignado, se crea el "call" y se ocupa el api "login" contenido en ApiManager que es la interface de retrofit.
        Call<RegistrarUsuarioResponse> call = apiManager.registerUser(regUser);

        call.enqueue(new Callback<RegistrarUsuarioResponse>() {
            @Override
            public void onResponse(Call<RegistrarUsuarioResponse> call, Response<RegistrarUsuarioResponse> response) {
                if (response.body().getCodigo() == ErrorCodes.SUCCESS){
                    Toast.makeText(activity, R.string.register_process, Toast.LENGTH_LONG).show();
                    sendVerificationCode(regUser, response.body().getUsuarioId());
                }else if (response.body().getCodigo() == ErrorCodes.FAILURE){
                    loaderInteractionEnable();
                    Toast.makeText(activity, response.body().getMensaje(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RegistrarUsuarioResponse> call, Throwable t) {
                Log.e("User reg. error",t.getMessage());
                loaderInteractionEnable();
            }
        });
    }

    public void sendVerificationCode(RegistrarUsuarioRequest regUser, String uuid){
        VerificationCodeRequest verfCodeR = new VerificationCodeRequest();
        verfCodeR.setEmail(regUser.getEmail());
        verfCodeR.setTelefono(regUser.getTelefono());
        verfCodeR.setUsuarioId(uuid);

        //Inicializa la construcción de la petición para que "apiManager" traiga la URL Base
        apiManager = ApiConstants.getAPIManager();

        //Una vez que apiManager es asignado, se crea el "call" y se ocupa el api "login" contenido en ApiManager que es la interface de retrofit.
        Call<VerificationCodeResponse> call = apiManager.activationCode(verfCodeR);

        call.enqueue(new Callback<VerificationCodeResponse>() {
            @Override
            public void onResponse(Call<VerificationCodeResponse> call, Response<VerificationCodeResponse> response) {
                if (response.body().getCodigo() == ErrorCodes.SUCCESS){
                    loaderInteractionEnable();
                    Intent homeIntent = new Intent(activity, Activar_cuenta.class);
                    homeIntent.putExtra("uuid",uuid);
                    startActivity(homeIntent);
                  //  showActivationcountFragment(uuid);
                }else if (response.body().getCodigo() == ErrorCodes.FAILURE){
                    loaderInteractionEnable();
                    Toast.makeText(activity, response.body().getMensaje(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<VerificationCodeResponse> call, Throwable t) {
                Log.e("Activation code. error",t.getMessage());
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

}
