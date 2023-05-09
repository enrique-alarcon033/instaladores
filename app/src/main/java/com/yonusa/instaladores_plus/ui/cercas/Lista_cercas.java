package com.yonusa.instaladores_plus.ui.cercas;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.yonusa.instaladores_plus.R;
import com.yonusa.instaladores_plus.ui.add_devices.list_of_devices.AddANewDeviceActivity;
import com.yonusa.instaladores_plus.ui.cercas.adapter.cercas_adapter_completo;
import com.yonusa.instaladores_plus.ui.cercas.modelo.cercas_model_completo;
import com.yonusa.instaladores_plus.ui.login.view.Loguin_new;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class Lista_cercas extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    FloatingActionButton agregarcerca;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mRecyclerViewAceptadas;
    private RecyclerView.Adapter mAdapterco;
    private RecyclerView.LayoutManager mLayoutManagerco;
    TextView texto,correo,nombre,apellido;
    private RecyclerView.Adapter mAdaptercoAcep;
    private RecyclerView.LayoutManager mLayoutManagercoAcep;
    //El dataset de tipo Photo
    private ArrayList<cercas_model_completo> myDatasetCoAcep;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)){
return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_cercas);
        SharedPreferences misPreferencias = getSharedPreferences("Datos_usuario", Context.MODE_PRIVATE);
        String email =  misPreferencias.getString("email","0");
        String nombre_t =  misPreferencias.getString("nombre","0");
        String apellido_t =  misPreferencias.getString("apellido","0");
        texto= (TextView) findViewById(R.id.agrega_tu_cerca);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView= findViewById(R.id.nav_view);

        View header = navigationView.getHeaderView(0);
        correo = (TextView) header.findViewById(R.id.user_correo);
        nombre = (TextView) header.findViewById(R.id.nombre);
        apellido = (TextView) header.findViewById(R.id.apellido);
        correo.setText(email);
        nombre.setText(nombre_t);
        apellido.setText(apellido_t);


        drawerToggle= new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_jugadores:
                    {
                        Toast.makeText(Lista_cercas.this,"Proximamente",Toast.LENGTH_LONG).show();
                        break;
                    }
                    case R.id.nav_albitros:
                    {
                        Toast.makeText(Lista_cercas.this,"Proximamente",Toast.LENGTH_LONG).show();
                        break;
                    }
                    case R.id.nav_jornadas:
                    {
                        Toast.makeText(Lista_cercas.this,"Proximamente",Toast.LENGTH_LONG).show();
                        break;
                    }

                    case R.id.nav_cerrar:
                    {
                       // Toast.makeText(Lista_cercas.this,"Cerrando Sesión...",Toast.LENGTH_LONG).show();
                        try {
                            CerrarSesion();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return false;
            }
        });
        navigationView.setItemIconTintList(null);



        mRecyclerViewAceptadas = (RecyclerView) findViewById(R.id.lista_cercas);
        mRecyclerViewAceptadas.setHasFixedSize(true);
        mLayoutManagercoAcep = new GridLayoutManager(getApplication(), 1);
        mRecyclerViewAceptadas.setLayoutManager(mLayoutManagercoAcep);

        agregarcerca = (FloatingActionButton) findViewById(R.id.add_nuevo_wifi);
        agregarcerca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addDevicesIntent = new Intent(Lista_cercas.this, AddANewDeviceActivity.class);
                startActivity(addDevicesIntent);
            }
        });

        try {
            obtener_cercas();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.white);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cargarDatos();
            }
        });

    }

    private void cargarDatos() {
        new UnaTarea().execute();
    }
    private class UnaTarea extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            swipeRefreshLayout.setRefreshing(false);
        }
    }
    private void refreshDataset_aceptadas () {
        if (mRecyclerViewAceptadas == null)
            return;

        if (mAdaptercoAcep == null) {
            mAdaptercoAcep = new cercas_adapter_completo(getApplication(), myDatasetCoAcep);
            mRecyclerViewAceptadas.setAdapter(mAdaptercoAcep);
        } else {
            mAdaptercoAcep.notifyDataSetChanged();
        }
    }
    public boolean obtener_cercas() throws JSONException, UnsupportedEncodingException {
        myDatasetCoAcep = new ArrayList<cercas_model_completo>();

        SharedPreferences misPreferencias = getSharedPreferences("Datos_usuario", Context.MODE_PRIVATE);

        String id_user =  misPreferencias.getString("usuarioId","0");
        String token =  misPreferencias.getString("accessToken","0");
        String aplicacion = "application/json";
        JSONObject oJSONObject = new JSONObject();
          oJSONObject.put("usuarioId",id_user);
        //   oJSONObject.put("coordenates",_contrasena);
        ByteArrayEntity oEntity = new ByteArrayEntity(oJSONObject.toString().getBytes("UTF-8"));
        oEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        //  oEntity.setContentEncoding(new BasicHeader(HttpHeaders.AUTHORIZATION,  token));

        //  Toast.makeText(getApplicationContext(), oEntity.toString(), Toast.LENGTH_LONG).show();
        //    Toast.makeText(getApplicationContext(), oEntity.toString(), Toast.LENGTH_LONG).show();
        //      oEntity.setContentType("Authorization", "Bearer "+token);

        AsyncHttpClient oHttpClient = new AsyncHttpClient();
        oHttpClient.addHeader(
                "Authorization",
                token);
        //cambiar varible
        RequestHandle requestHandle = oHttpClient.post(getApplicationContext(),
                "http://gateway.payonusa.com/api/ObtenerCercasUsuario",(HttpEntity) oEntity, "application/json" ,new AsyncHttpResponseHandler() {

                    @Override
                    public void onStart() {
                        // called before request is started

                    }
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody){
                        System.out.println(statusCode);
                        System.out.println(responseBody);

                        try {
                            String content = new String(responseBody, "UTF-8");
                            JSONObject obj = new JSONObject(content);

                            JSONArray jsonArray = obj.getJSONArray("cercas");
                            for (int i = 0; i < jsonArray.length(); i++)
                            {
                                try {
                                    JSONObject jsonObjectHijo = jsonArray.getJSONObject(i);
                                    if (jsonObjectHijo != null) {
                                        //Armamos un objeto Photo con el Title y la URL de cada JSONObject
                                        cercas_model_completo photo = new cercas_model_completo();


                                        if (jsonObjectHijo.has("rol"))
                                            photo.setRol(jsonObjectHijo.getString("rol"));

                                        if (jsonObjectHijo.has("aliasCerca"))
                                            photo.setAliasCerca(jsonObjectHijo.getString("aliasCerca"));

                                        if (jsonObjectHijo.has("cercaId"))
                                            photo.setCercaId(jsonObjectHijo.getString("cercaId"));

                                        if (jsonObjectHijo.has("MAC"))
                                            photo.setMAC(jsonObjectHijo.getString("MAC"));

                                        if (jsonObjectHijo.has("modelo"))
                                            photo.setModelo(jsonObjectHijo.getString("modelo"));

                                        if (jsonObjectHijo.has("estadoBateria"))
                                            photo.setEstadoBateria(jsonObjectHijo.getString("estadoBateria"));

                                        if (jsonObjectHijo.has("estadoConexionCorriente"))
                                            photo.setEstadoConexionCorriente(jsonObjectHijo.getString("estadoConexionCorriente"));

                                        if (jsonObjectHijo.has("estadoConexionAlSistema"))
                                            photo.setEstadoConexionAlSistema(jsonObjectHijo.getString("estadoConexionAlSistema"));

                                        if (jsonObjectHijo.has("estadoAlarma"))
                                            photo.setEstadoAlarma(jsonObjectHijo.getString("estadoAlarma"));
                                        //Agreagamos el objeto Photo al Dataset
                                        //Agreagamos el objeto Photo al Dataset{

                                        myDatasetCoAcep.add(photo);


                                    }
                                    //   Toast.makeText(getApplicationContext(), String.valueOf(jsonObjectHijo), Toast.LENGTH_LONG).show();

                                } catch (JSONException e) {
                                    Log.e("Parser JSON", e.toString());
                                }finally {
                                    //Finalmente si hemos cargado datos en el Dataset
                                    // entonces refrescamos
                                    //progressplanes.setVisibility(View.GONE);
                                    if (myDatasetCoAcep.size() > 0){
                                        //  texto2.setVisibility(View.VISIBLE);
                                        //   aceptadas_s.setVisibility(View.GONE);
                                        refreshDataset_aceptadas();


                                    }
                                    else{

                                        //  texto2.setVisibility(View.VISIBLE);
                                        mRecyclerViewAceptadas.setVisibility(View.GONE);
                                        //     aceptadas_s.setVisibility(View.GONE);
                                    }
                                }
                            }if (jsonArray.length()>0){

                            }else{
                                texto.setVisibility(View.VISIBLE);
                            }

                            //  Toast.makeText(getApplicationContext(), obj.getString("listConsents"), Toast.LENGTH_LONG).show();
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

    public boolean CerrarSesion() throws JSONException, UnsupportedEncodingException {
        myDatasetCoAcep = new ArrayList<cercas_model_completo>();

        SharedPreferences misPreferencias = getSharedPreferences("Datos_usuario", Context.MODE_PRIVATE);

        String id_user = misPreferencias.getString("User_id", "0");
        String aplicacion = "application/json";
        JSONObject oJSONObject = new JSONObject();
        oJSONObject.put("usuarioId", id_user);
        //   oJSONObject.put("coordenates",_contrasena);
        ByteArrayEntity oEntity = new ByteArrayEntity(oJSONObject.toString().getBytes("UTF-8"));
        oEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        //  oEntity.setContentEncoding(new BasicHeader(HttpHeaders.AUTHORIZATION,  token));

        //  Toast.makeText(getApplicationContext(), oEntity.toString(), Toast.LENGTH_LONG).show();
        //    Toast.makeText(getApplicationContext(), oEntity.toString(), Toast.LENGTH_LONG).show();
        //      oEntity.setContentType("Authorization", "Bearer "+token);

        AsyncHttpClient oHttpClient = new AsyncHttpClient();
     //cambiar varible
        RequestHandle requestHandle = oHttpClient.post(getApplicationContext(),
                "http://payonusa.com/instaladores/api/v1/CerrarSesion",(HttpEntity) oEntity, "application/json" ,new AsyncHttpResponseHandler() {

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

                                SharedPreferences sharedPref2 =getSharedPreferences("Datos_acceso",Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor2 = sharedPref2.edit();
                                editor2.putString("correo", "");
                                editor2.putString("password", "");
                                editor2.putString("recordar","0");
                                editor2.commit();
                                Toast.makeText(getApplicationContext(), "Sesión Cerrada", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), Loguin_new.class);
                                startActivity(intent);
                                finish();
                            }

                            if (valor.equals("-2")){

                                Toast.makeText(getApplicationContext(), "El usuario no existe", Toast.LENGTH_LONG).show();
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



    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

    }

    private void  Cerrar(){
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("¿Esta seguro de Cerrar su Sesión?, al hacerlo dejará de recibir notificaciones");
        dialog.setTitle("Cerrar Sesión");
        setTitleColor(R.color.colorPrimary);
        dialog.setPositiveButton("Sí",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        // Toast.makeText(co(),"Yes is clicked",Toast.LENGTH_LONG).show();
                        try {
                          CerrarSesion();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                    }
                });
        dialog.setNegativeButton("Cancelar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {


                    }
                });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }
}