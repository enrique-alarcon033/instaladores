package com.yonusa.instaladores_plus.ui.view.view.user_permissions;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yonusa.instaladores_plus.R;
import com.yonusa.instaladores_plus.api.ApiConstants;
import com.yonusa.instaladores_plus.api.ApiManager;
import com.yonusa.instaladores_plus.api.BaseResponse;
import com.yonusa.instaladores_plus.ui.view.adapter.Adapter_UserPermissions;
import com.yonusa.instaladores_plus.ui.view.models.request.get_users_permissions.GetUserPermissionsRequest;
import com.yonusa.instaladores_plus.ui.view.models.request.update_permissions.PermisoControles;
import com.yonusa.instaladores_plus.ui.view.models.request.update_permissions.UpdateUserPermissionsRequest;
import com.yonusa.instaladores_plus.ui.view.models.response.Controle;
import com.yonusa.instaladores_plus.ui.view.models.response.GetUserPermissionsResponse;
import com.yonusa.instaladores_plus.utilities.catalogs.ErrorCodes;
import com.yonusa.instaladores_plus.utilities.catalogs.SP_Dictionary;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class User_Edit_Permissions extends AppCompatActivity {

    private static final String TAG = User_Edit_Permissions.class.getSimpleName();

    private Adapter_UserPermissions mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    List<Controle> mControlList = new ArrayList<>();
    ApiManager apiManager;

    TextView guestUserName;
    String userId;
    String userAlias;
    String deviceId;
    String guestUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_edit_permissions);

        Log.i(TAG, "On Create");
        setTitle("Add Users");
        getSupportActionBar().hide();

        guestUserName = findViewById(R.id.tv_guestName);

        SharedPreferences prefs = getSharedPreferences(SP_Dictionary.USER_INFO, MODE_PRIVATE);
        userId = prefs.getString(SP_Dictionary.USER_ID, "No user id definded");

        deviceId = getIntent().getStringExtra("DEVICE_ID");
        guestUserId = getIntent().getStringExtra("GUEST_USER_ID");
        userAlias = getIntent().getStringExtra("GUEST_USER_NAME");

        guestUserName.setText(userAlias);

    }

    @Override
    protected void onResume() {
        super.onResume();

        toGetUserPermissions();

    }

    public void buildRecyclerView(List<Controle> controlList){

        Log.i("Devices: ------------>",controlList.toString());
        RecyclerView recyclerView = findViewById(R.id.rv_user_permissions);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(User_Edit_Permissions.this);
        mAdapter = new Adapter_UserPermissions((ArrayList<Controle>) controlList);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(position -> toUpdatePermissions(controlList, position));

    }

    private void toUpdatePermissions(List<Controle> controlList, Integer position){

        Log.i("To User Permissions", "User Selected");

        Integer controlId = controlList.get(position).getControlId();
        Boolean permissionState = controlList.get(position).getEstadoPermiso();

        UpdateUserPermissionsRequest updateUserPermissionsRequest = new UpdateUserPermissionsRequest();

        updateUserPermissionsRequest.setUsuarioAdministradorId(userId);
        updateUserPermissionsRequest.setUsuarioInvitadoId(guestUserId);
        updateUserPermissionsRequest.setCercaId(deviceId);

        List<PermisoControles> listPermissions = new ArrayList<>();

        PermisoControles permisoControles = new PermisoControles();
        permisoControles.setControlId(controlId);
        permisoControles.setEstadoPermiso(!permissionState);

        listPermissions.add(permisoControles);

        updateUserPermissionsRequest.setPermisoControles(listPermissions);

        Log.i("Update Construction", updateUserPermissionsRequest.toString());
        updatePermissions(updateUserPermissionsRequest);

    }

    private void updatePermissions(UpdateUserPermissionsRequest updateUserPermissionsRequest){

        //Inicializa la construcción de la petición para que "apiManager" traiga la URL Base
        apiManager = ApiConstants.getAPIManager();

        //Una vez que apiManager es asignado, se crea el "call" y se ocupa el api "login" contenido en ApiManager que es la interface de retrofit.
        Call<BaseResponse> call = apiManager.updateUserPermissions(updateUserPermissionsRequest);

        //Se ejecuta la petición y se asigna el objeto LogIn_Response para recibir el Callback del servicio.
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                int typeCode = response.body().getCodigo();
                switch (typeCode){
                    case (ErrorCodes.SUCCESS):
                        toGetUserPermissions();
                        Toast.makeText(User_Edit_Permissions.this, "Cambió el estado del permiso", Toast.LENGTH_LONG).show();
                        break;
                    case (ErrorCodes.FAILURE):
                        //TODO: Agregar alertas de error.
                        Toast.makeText(User_Edit_Permissions.this, "Ocurrió un error", Toast.LENGTH_SHORT).show();

                        break;

                    default:
                        Log.i("Peticion update", "Algo pasó");
                }


            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
                Toast.makeText(User_Edit_Permissions.this, "Ocurrió un error", Toast.LENGTH_SHORT).show();
                //TODO: Agregar alerta de error de conexión.
            }
        });


    }



    private void toGetUserPermissions(){

        GetUserPermissionsRequest getUserPermissionsRequest = new GetUserPermissionsRequest();
        getUserPermissionsRequest.setUsuarioAdministradorId(userId);
        getUserPermissionsRequest.setUsuarioInvitadoId(guestUserId);
        getUserPermissionsRequest.setCercaId(deviceId);

        getUserPermissions(getUserPermissionsRequest);

    }

    private void getUserPermissions(GetUserPermissionsRequest getUserPermissionsRequest){


        //Inicializa la construcción de la petición para que "apiManager" traiga la URL Base
        apiManager = ApiConstants.getAPIManager();

        //Una vez que apiManager es asignado, se crea el "call" y se ocupa el api "login" contenido en ApiManager que es la interface de retrofit.
        Call<GetUserPermissionsResponse> call = apiManager.getUserPermissions(getUserPermissionsRequest);

        //Se ejecuta la petición y se asigna el objeto LogIn_Response para recibir el Callback del servicio.
        call.enqueue(new Callback<GetUserPermissionsResponse>() {
            @Override
            public void onResponse(Call<GetUserPermissionsResponse> call, Response<GetUserPermissionsResponse> response) {

                int typeCode = response.body().getCodigo();
                switch (typeCode){
                    case (ErrorCodes.SUCCESS):

                        int controls = response.body().getControles().size();

                        if (controls != 0) {

                            mControlList = response.body().getControles();
                            buildRecyclerView(mControlList);

                        } else {

                            Toast.makeText(User_Edit_Permissions.this, "No hay dispositivos", Toast.LENGTH_SHORT).show();

                        }

                        break;
                    case (ErrorCodes.FAILURE):
                        //TODO: Agregar alertas de error.
                        Toast.makeText(User_Edit_Permissions.this, "Revisa tus credenciales", Toast.LENGTH_SHORT).show();

                        break;

                    default:
                        Log.i("Peticion login", "Algo pasó");
                }


            }

            @Override
            public void onFailure(Call<GetUserPermissionsResponse> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
                //TODO: Agregar alerta de error de conexión.
            }
        });


    }

}
