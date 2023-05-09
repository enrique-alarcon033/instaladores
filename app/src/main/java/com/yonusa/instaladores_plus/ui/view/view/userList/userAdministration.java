package com.yonusa.instaladores_plus.ui.view.view.userList;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yonusa.instaladores_plus.R;
import com.yonusa.instaladores_plus.api.ApiConstants;
import com.yonusa.instaladores_plus.api.ApiManager;
import com.yonusa.instaladores_plus.ui.device_control.view.DeviceControlActivity;
import com.yonusa.instaladores_plus.ui.view.adapter.Adapter_UserList_Items;
import com.yonusa.instaladores_plus.ui.view.models.request.get_device_users.GetDeviceUsersRequest;
import com.yonusa.instaladores_plus.ui.view.models.response.GetDeviceUsersResponse;
import com.yonusa.instaladores_plus.ui.view.models.response.Usuario;
import com.yonusa.instaladores_plus.ui.view.view.user_add.User_Add_Activity;
import com.yonusa.instaladores_plus.ui.view.view.user_permissions.User_Edit_Permissions;
import com.yonusa.instaladores_plus.utilities.catalogs.ErrorCodes;
import com.yonusa.instaladores_plus.utilities.catalogs.SP_Dictionary;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class userAdministration extends AppCompatActivity {

    private static final String TAG = DeviceControlActivity.class.getSimpleName();

    private Adapter_UserList_Items mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    String deviceId;
    String userId;
    ApiManager apiManager;
    ImageView userAdmin;

    List<Usuario> mUserList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_administration);

        Log.i(TAG, "On Create");
        setTitle("userAdministration");
        getSupportActionBar().hide();

        userAdmin = findViewById(R.id.iv_user_admin);
        userAdmin.setVisibility(View.VISIBLE);

        userAdmin.setOnClickListener(v -> goToAddUser());

        SharedPreferences prefs = getSharedPreferences(SP_Dictionary.USER_INFO, MODE_PRIVATE);
        userId = prefs.getString(SP_Dictionary.USER_ID, "No user id definded");

        deviceId = getIntent().getStringExtra("DEVICE_ID");

    }

    @Override
    protected void onResume() {
        super.onResume();

        GetDeviceUsersRequest getDeviceUsersRequest = new GetDeviceUsersRequest();

        getDeviceUsersRequest.setUsuarioAdministradorId(userId);
        getDeviceUsersRequest.setCercaId(deviceId);

        getDeviceUsers(getDeviceUsersRequest);

    }

    private void getDeviceUsers(GetDeviceUsersRequest getDeviceUsersRequest){

        //Inicializa la construcción de la petición para que "apiManager" traiga la URL Base
        apiManager = ApiConstants.getAPIManager();

        //Una vez que apiManager es asignado, se crea el "call" y se ocupa el api "login" contenido en ApiManager que es la interface de retrofit.
        Call<GetDeviceUsersResponse> call = apiManager.getDeviceUsers(getDeviceUsersRequest);

        //Se ejecuta la petición y se asigna el objeto GetDeviceUsersResponse para recibir el Callback del servicio.
        call.enqueue(new Callback<GetDeviceUsersResponse>() {
            @Override
            public void onResponse(Call<GetDeviceUsersResponse> call, Response<GetDeviceUsersResponse> response) {

                int typeCode = response.body().getCodigo();
                switch (typeCode){
                    case (ErrorCodes.SUCCESS):

                        int users = response.body().getUsuarios().size();

                        if (users != 0) {

                            mUserList = response.body().getUsuarios();
                            buildRecyclerView(mUserList);

                        } else {

                            Toast.makeText(userAdministration.this, "No hay usuarios invitados", Toast.LENGTH_LONG).show();

                        }

                        break;
                    case (ErrorCodes.FAILURE):
                        //TODO: Agregar alertas de error.
                        Toast.makeText(userAdministration.this, "Error al obtener invitados", Toast.LENGTH_SHORT).show();

                        break;

                    default:
                        Log.i("Peticion login", "Algo pasó");
                }

            }

            @Override
            public void onFailure(Call<GetDeviceUsersResponse> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
                //TODO: Agregar alerta de error de conexión.
            }
        });

    }

    public void buildRecyclerView(List<Usuario> userList){

        Log.i("Users: ------------>",userList.toString());

        RecyclerView recyclerView = findViewById(R.id.rv_userList);
        recyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(userAdministration.this);
        mAdapter = new Adapter_UserList_Items((ArrayList<Usuario>) userList);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(position -> goToUserPermissions(userList, position));

    }

    public void goToUserPermissions(List<Usuario> userList, int position) {
        Log.i("To User Permissions", "User Selected");

        String userName = userList.get(position).getAliasUsuario();
        String guestUserId = userList.get(position).getUsuarioId();
        Intent deviceControlIntent = new Intent(userAdministration.this, User_Edit_Permissions.class);

        deviceControlIntent.putExtra("USER_ID", userId);
        deviceControlIntent.putExtra("GUEST_USER_ID", guestUserId);
        deviceControlIntent.putExtra("DEVICE_ID", deviceId);
        deviceControlIntent.putExtra("GUEST_USER_NAME", userName);

        startActivity(deviceControlIntent);


    }

    private void goToAddUser(){
        Intent addUserIntent = new Intent(userAdministration.this, User_Add_Activity.class);
        addUserIntent.putExtra("DEVICE_ID", deviceId);
        startActivity(addUserIntent);
    }

}
