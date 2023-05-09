package com.yonusa.instaladores_plus.ui.cercas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.yonusa.instaladores_plus.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class Asignar_Cerca extends AppCompatActivity {

    ConstraintLayout configurado, no_configurado;
    Button asignar,regresar;
    TextView email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignar_cerca);

        String rol = getIntent().getStringExtra("rol");
        String cerca = getIntent().getStringExtra("cerca_id");


        configurado = (ConstraintLayout) findViewById(R.id.layout_configurado);
        no_configurado=(ConstraintLayout) findViewById(R.id.layout_no_configurado);
        asignar = (Button) findViewById(R.id.btn_asignar);
        regresar = (Button) findViewById(R.id.btn_regresar);
        email = (TextView) findViewById(R.id.editTextTextEmailAddress);

        if (rol.equals("3")){
            configurado.setVisibility(View.VISIBLE);
            no_configurado.setVisibility(View.GONE);

        }else{
            configurado.setVisibility(View.GONE);
            no_configurado.setVisibility(View.VISIBLE);
        }

        asignar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Asignar_admin(cerca,email.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private boolean Asignar_admin(String cerca,String email) throws JSONException, UnsupportedEncodingException {
        //  Toast.makeText(mContextCol," solicitud", Toast.LENGTH_LONG).show();

        SharedPreferences misPreferencias = getSharedPreferences("Datos_usuario", Context.MODE_PRIVATE);
        String token =  misPreferencias.getString("Token_peticiones","S/T");
        String user_id =  misPreferencias.getString("usuarioId","0");
        String aplicacion = "application/json";
        JSONObject obj = new JSONObject();
        obj.put("usuarioAdministradorId",user_id);
        obj.put("usuarioInvitadoEmail",email);
        obj.put("aliasUsuarioInvitado","Administrador");
        obj.put("cercaId",cerca);


        JSONObject co1= new JSONObject();
        co1.put("controlId",1);
        co1.put("estadoPermiso",true);

        JSONObject co2= new JSONObject();
        co2.put("controlId",2);
        co2.put("estadoPermiso",true);

        JSONObject co3= new JSONObject();
        co3.put("controlId",3);
        co3.put("estadoPermiso",true);

        JSONObject co4= new JSONObject();
        co4.put("controlId",4);
        co4.put("estadoPermiso",true);

        JSONObject co5= new JSONObject();
        co5.put("controlId",5);
        co5.put("estadoPermiso",true);

        JSONObject co6= new JSONObject();
        co6.put("controlId",6);
        co6.put("estadoPermiso",true);

        JSONArray array = new JSONArray();
        array.put(co1);
        array.put(co2);
        array.put(co3);
        array.put(co4);
        array.put(co5);
        array.put(co6);

        obj.put("PermisoControles",array);
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
                "http://payonusa.com/instaladores/api/v1/AgregarUsuarioCerca",(HttpEntity) oEntity, "application/json" ,new AsyncHttpResponseHandler() {

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

                                Toast.makeText(getApplicationContext(), "Asignación correcta", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(Asignar_Cerca.this, Lista_cercas.class);
                                startActivity(i);
                                finish();
                            }

                            if (valor.equals("-1")){

                                Toast.makeText(getApplicationContext(), "La cerca no se pudo asignar, favor de verifiar el correo", Toast.LENGTH_LONG).show();
                                finish();
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