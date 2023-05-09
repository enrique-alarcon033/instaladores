package com.yonusa.instaladores_plus.ui.password_recovery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.yonusa.instaladores_plus.R;
import com.yonusa.instaladores_plus.api.ApiManager;
import com.yonusa.instaladores_plus.ui.login.view.Loguin_new;
import com.yonusa.instaladores_plus.utilities.Validations;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class Recovery_two extends AppCompatActivity {

    EditText et_recovery_code;
    EditText et_new_password;
    EditText et_new_password_confirmation;
    Button btn_update_password;

    String email;
    String recovery_code;
    String newPass;
    String confirmPass;

    Context context = this;
    ApiManager apiManager;

    LottieAnimationView loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery_two);

        setContentView(R.layout.activity_password_recovery_2);
        email = getIntent().getStringExtra("RECOVERY_EMAIL");


        et_recovery_code = findViewById(R.id.et_recovery_code);
        et_new_password = findViewById(R.id.et_password_recovery);
        et_new_password_confirmation = findViewById(R.id.et_password_recovery_confirm);

        btn_update_password = findViewById(R.id.btn_update_password);
        btn_update_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_recovery_code.getText().toString().isEmpty()) {

                    Toast.makeText(Recovery_two.this, "Código de recuperación vacío", Toast.LENGTH_SHORT).show();

                } else {

                    recovery_code = et_recovery_code.getText().toString();
                    newPass = et_new_password.getText().toString();
                    confirmPass = et_new_password_confirmation.getText().toString();

                    Boolean isPassValid = Validations.isValidPassword(newPass);

                    if (isPassValid) {

                        if (newPass.equals(confirmPass)){
                            loader.setVisibility(View.VISIBLE);
                            try {
                                Cambiar_contraseña(email, recovery_code, newPass);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }

                        } else {

                            Toast.makeText(Recovery_two.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();

                        }

                    } else {

                        Toast.makeText(Recovery_two.this, "Contraseña inválida", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        loader = findViewById(R.id.loader_set_new_pass);
    }


    public boolean Cambiar_contraseña(String email,String codigo, String pass) throws JSONException, UnsupportedEncodingException {

        JSONObject oJSONObject = new JSONObject();
        oJSONObject.put("email", email);
        oJSONObject.put("codigoRecuperacionCuenta", codigo);
        oJSONObject.put("password", pass);
        ByteArrayEntity oEntity = new ByteArrayEntity(oJSONObject.toString().getBytes("UTF-8"));
        oEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        AsyncHttpClient oHttpClient = new AsyncHttpClient();
        //cambiar varible
        RequestHandle requestHandle = oHttpClient.post(getApplicationContext(),
                "http://payonusa.com/paniagua/instalador/api/v1/ActualizarPassword",(HttpEntity) oEntity, "application/json" ,new AsyncHttpResponseHandler() {

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


                                Intent intent = new Intent(getApplicationContext(), Loguin_new.class);
                                startActivity(intent);
                                loader.setVisibility(View.GONE);
                                finish();
                            }

                            if (valor.equals("-1")){

                                Toast.makeText(getApplicationContext(), "El correo no existe", Toast.LENGTH_LONG).show();
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
}