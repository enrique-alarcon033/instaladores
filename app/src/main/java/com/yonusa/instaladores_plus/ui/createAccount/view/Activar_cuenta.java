package com.yonusa.instaladores_plus.ui.createAccount.view;

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
import com.yonusa.instaladores_plus.ui.login.view.Loguin_new;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class Activar_cuenta extends AppCompatActivity {


    EditText codigo;
    Button activar;
    LottieAnimationView loader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activar_cuenta);

        codigo = (EditText) findViewById(R.id.editText_activation_code);
        activar = (Button) findViewById(R.id.activation_count_btn);
        loader = (LottieAnimationView) findViewById(R.id.loader_create_form);
        String uuid= getIntent().getStringExtra("uuid");

        activar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    loader.setVisibility(View.VISIBLE);
                    activar_cuenta(uuid,codigo.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean activar_cuenta(String uuid,String codigo) throws JSONException, UnsupportedEncodingException {
        //  Toast.makeText(mContextCol," solicitud", Toast.LENGTH_LONG).show();


        String aplicacion = "application/json";
        JSONObject obj = new JSONObject();
        obj.put("usuarioId",uuid);
        //  obj.put("priceId","price_1J5XnhFHxE39JvKKqXYCZJUL");
        obj.put("codigoActivacion",codigo);




        //   oJSONObject.put("coordenates",_contrasena);
        ByteArrayEntity oEntity = new ByteArrayEntity(obj.toString().getBytes("UTF-8"));
        oEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        //  oEntity.setContentEncoding(new BasicHeader(HttpHeaders.AUTHORIZATION,  token));

        //  Toast.makeText(getApplicationContext(), oEntity.toString(), Toast.LENGTH_LONG).show();
        //    Toast.makeText(getApplicationContext(), oEntity.toString(), Toast.LENGTH_LONG).show();
        //      oEntity.setContentType("Authorization", "Bearer "+token);
        // Toast.makeText(mContextCol,obj.toString(), Toast.LENGTH_LONG).show();Toast.makeText(mContextCol," solicitud", Toast.LENGTH_LONG).show();
        AsyncHttpClient oHttpClient = new AsyncHttpClient();
        //cambiar varible
        RequestHandle requestHandle = oHttpClient.post(getApplicationContext(),
                "http://payonusa.com/paniagua/instalador/api/v1/ActivarCuentaUsuario",(HttpEntity) oEntity, "application/json" ,new AsyncHttpResponseHandler() {

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
                                loader.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "Cuenta Activada Exitosamente", Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(), "Inicia Sesión con tus nuevos datos", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), Loguin_new.class);
                                startActivity(intent);
                                finish();
                            }else{
                                loader.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "Datos incorrectos, por favor verifica tu número de activación", Toast.LENGTH_LONG).show();

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

                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
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