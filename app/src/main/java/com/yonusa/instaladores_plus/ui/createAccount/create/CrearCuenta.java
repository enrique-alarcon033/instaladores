package com.yonusa.instaladores_plus.ui.createAccount.create;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.hbb20.CountryCodePicker;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.yonusa.instaladores_plus.R;
import com.yonusa.instaladores_plus.api.ApiManager;
import com.yonusa.instaladores_plus.ui.createAccount.view.Create_Account_Activity;
import com.yonusa.instaladores_plus.utilities.Validations;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class CrearCuenta extends AppCompatActivity {

    private EditText name, lastName, email, passwrd, confPassword, cellPhone;
    private CountryCodePicker countryCode;
    private Button createAcBtn, mostrarPswrd, mostrarConfirmPswrd, btnShowPrivacy;
    private CheckBox chkPrivacy;
    ApiManager apiManager;

    private static final String TAG = Create_Account_Activity.class.getSimpleName();
    LottieAnimationView loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);

        loader = findViewById(R.id.loader_create_form);

        name = findViewById(R.id.nombre_editText_cc);
        lastName = findViewById(R.id.apellidos_editText_cc);
        email = findViewById(R.id.email_editText_cc);
        passwrd = findViewById(R.id.passwrd_editText_cc);
       confPassword = findViewById(R.id.passwrd_confirm_editText_cc);
        cellPhone = findViewById(R.id.celphone_editText_cc);
        countryCode = findViewById(R.id.phoneCodeInput);
        createAcBtn = findViewById(R.id.create_account_btn);
        chkPrivacy = findViewById(R.id.check_privacy);
        btnShowPrivacy = findViewById(R.id.btn_privacy);

        mostrarPswrd = findViewById(R.id.btn_show_pass_cc);
        mostrarPswrd.setTag("hide");
        mostrarPswrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPassword();
            }
        });

        mostrarConfirmPswrd = findViewById(R.id.btn_show_pass_confirm_cc);
        mostrarConfirmPswrd.setTag("hide");
        mostrarConfirmPswrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPasswordConfirm();
            }
        });

        createAcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    boolean complete = validarDatos(name.getText().toString(), lastName.getText().toString(),email.getText().toString(),passwrd.getText().toString(), confPassword.getText().toString(), cellPhone.getText().toString());
                    if (complete){
                        loader.setVisibility(View.VISIBLE);
                        Crear_cuenta(name.getText().toString().trim(),lastName.getText().toString(),email.getText().toString().trim(),passwrd.getText().toString(),cellPhone.getText().toString());
                    }
                 } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public boolean validarDatos(String name, String lastName, String email, String password, String confPassword, String celPhone){
        String text;
        if (name.equals("")){
            Log.e(TAG, "Empty name error");
            text = getResources().getString(R.string.empty_name);
            Toast.makeText(CrearCuenta.this,text, Toast.LENGTH_SHORT).show();
            return false;
        }else if (lastName.equals("")){
            Log.e(TAG, "Empty lastName error");
            text = getResources().getString(R.string.empty_lastname);
            Toast.makeText(CrearCuenta.this,text, Toast.LENGTH_SHORT).show();
            return false;
        }else if (email.equals("") || !Validations.isValidEmail(email)){
            Log.e(TAG, "Email not valid error");
            text = getResources().getString(R.string.enter_valid_email);
            Toast.makeText(CrearCuenta.this,text, Toast.LENGTH_SHORT).show();
            return false;
        }else if (password.equals("") || !Validations.isValidPassword(password)){
            Log.e(TAG, "Password not valid error");
            text = getResources().getString(R.string.invalid_password_lenght);
            Toast.makeText(CrearCuenta.this,text, Toast.LENGTH_LONG).show();
            return false;
        }else if (confPassword.equals("")){
            Log.e(TAG, "Password confirm is empty valid error");
            text = getResources().getString(R.string.empty_password_confirm);
            Toast.makeText(CrearCuenta.this,text, Toast.LENGTH_SHORT).show();
            return false;
        }else if (!confPassword.equals(password)){
            Log.e(TAG, "Passwords are not equals error");
            text = getResources().getString(R.string.passwords_not_equals);
            Toast.makeText(CrearCuenta.this,text, Toast.LENGTH_SHORT).show();
            return false;
        }else if (celPhone.equals("")){
            Log.e(TAG, "Empty phone number error");
            text = getResources().getString(R.string.empty_phone);
            Toast.makeText(CrearCuenta.this,text, Toast.LENGTH_SHORT).show();
            return false;
        }else if (!chkPrivacy.isChecked()){
            Log.e(TAG, "No privacy policy accept error");
            text = getResources().getString(R.string.privacy_policy_not_checked);
            Toast.makeText(CrearCuenta.this,text, Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }

    public boolean Crear_cuenta(String nombre,String apellidos, String email,String password, String telefono) throws JSONException, UnsupportedEncodingException {

        JSONObject oJSONObject = new JSONObject();
        oJSONObject.put("nombre", nombre);
        oJSONObject.put("apellidos", apellidos);
        oJSONObject.put("email", email);
        oJSONObject.put("password", password);
        oJSONObject.put("telefono", telefono);

        ByteArrayEntity oEntity = new ByteArrayEntity(oJSONObject.toString().getBytes("UTF-8"));
        oEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        AsyncHttpClient oHttpClient = new AsyncHttpClient();
        //cambiar varible
        RequestHandle requestHandle = oHttpClient.post(getApplicationContext(),
                "http://payonusa.com/paniagua/instalador/api/v1/instaladores",(HttpEntity) oEntity, "application/json" ,new AsyncHttpResponseHandler() {

                    @Override
                    public void onStart() {
                        // called before request is started

                    }
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody){
                        System.out.println(statusCode);
                        System.out.println(responseBody);
                        //     mMap = googleMap;d

                        try {
                            String content = new String(responseBody, "UTF-8");
                            JSONObject obj = new JSONObject(content);
                            String valor = String.valueOf(obj.get("codigo"));

                            if (valor.equals("0")){
                                Enviar_codigo(String.valueOf(obj.get("usuarioId")),email);
                            }

                            if (valor.equals("-1")){

                                Toast.makeText(getApplicationContext(), "El correo proporcionado ya se encuentra registrado", Toast.LENGTH_LONG).show();
                                loader.setVisibility(View.GONE);
                            }
                            // Toast.makeText(getApplicationContext(), String.valueOf(names), Toast.LENGTH_LONG).show();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        if (statusCode == 404) {
                            Toast.makeText(getApplicationContext(), "404 !", Toast.LENGTH_LONG).show();
                        } else if (statusCode == 500) {
                            Toast.makeText(getApplicationContext(), "500 !", Toast.LENGTH_LONG).show();
                            //sin_tarjetas();
                        } else if (statusCode == 403) {
                            Toast.makeText(getApplicationContext(), "403 !", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public boolean getUseSynchronousMode() {
                        return false;
                    }
                    @Override
                    public void onRetry(int retryNo) {
                        // called when request is retried
                        System.out.println(retryNo);
                    }
                });

        return false;
    }


    public boolean Enviar_codigo(String userid,String emailt) throws JSONException, UnsupportedEncodingException {

        JSONObject oJSONObject = new JSONObject();
        oJSONObject.put("usuarioId", userid);
        oJSONObject.put("email", emailt);


        ByteArrayEntity oEntity = new ByteArrayEntity(oJSONObject.toString().getBytes("UTF-8"));
        oEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        AsyncHttpClient oHttpClient = new AsyncHttpClient();
        //cambiar varible
        RequestHandle requestHandle = oHttpClient.post(getApplicationContext(),
                "http://payonusa.com/paniagua/instalador/api/v1/SolicitarCodigoActivacion",(HttpEntity) oEntity, "application/json" ,new AsyncHttpResponseHandler() {

                    @Override
                    public void onStart() {
                        // called before request is started

                    }
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody){
                        System.out.println(statusCode);
                        System.out.println(responseBody);
                        //     mMap = googleMap;d

                        try {
                            String content = new String(responseBody, "UTF-8");
                            JSONObject obj = new JSONObject(content);
                            String valor = String.valueOf(obj.get("codigo"));

                            if (valor.equals("0")){

                                Intent intent = new Intent(getApplicationContext(), Activar_cuenta.class);
                                intent.putExtra("uuid",userid);
                                startActivity(intent);
                                finish();

                                loader.setVisibility(View.GONE);

                            }

                            if (valor.equals("-1")){

                                Toast.makeText(getApplicationContext(), "El correo proporcionado ya se encuentra registrado", Toast.LENGTH_LONG).show();
                                loader.setVisibility(View.GONE);
                            }
                            // Toast.makeText(getApplicationContext(), String.valueOf(names), Toast.LENGTH_LONG).show();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        if (statusCode == 404) {
                            Toast.makeText(getApplicationContext(), "404 !", Toast.LENGTH_LONG).show();
                        } else if (statusCode == 500) {
                            Toast.makeText(getApplicationContext(), "500 !", Toast.LENGTH_LONG).show();
                            //sin_tarjetas();
                        } else if (statusCode == 403) {
                            Toast.makeText(getApplicationContext(), "403 !", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public boolean getUseSynchronousMode() {
                        return false;
                    }
                    @Override
                    public void onRetry(int retryNo) {
                        // called when request is retried
                        System.out.println(retryNo);
                    }
                });

        return false;
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
}