package com.yonusa.instaladores_plus.ui.view.view.user_add;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.yonusa.instaladores_plus.R;
import com.yonusa.instaladores_plus.api.ApiConstants;
import com.yonusa.instaladores_plus.api.ApiManager;
import com.yonusa.instaladores_plus.api.BaseResponse;
import com.yonusa.instaladores_plus.ui.view.models.request.add_user.AddUserRequest;
import com.yonusa.instaladores_plus.ui.view.models.request.add_user.PermisoControle;
import com.yonusa.instaladores_plus.utilities.Validations;
import com.yonusa.instaladores_plus.utilities.catalogs.ErrorCodes;
import com.yonusa.instaladores_plus.utilities.catalogs.SP_Dictionary;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class User_Add_Activity extends AppCompatActivity {

    private static final String TAG = User_Add_Activity.class.getSimpleName();

    ApiManager apiManager;

    String userId;
    String deviceId;

    EditText et_GuestAlias;
    EditText et_GuestEmail;

    Button btn_Send_Invitation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_user);

        Log.i(TAG, "On Create");
        setTitle("Add Users");
        getSupportActionBar().hide();

        SharedPreferences prefs = getSharedPreferences(SP_Dictionary.USER_INFO, MODE_PRIVATE);
        userId = prefs.getString(SP_Dictionary.USER_ID, "No user id definded");

        deviceId = getIntent().getStringExtra("DEVICE_ID");

        et_GuestAlias = findViewById(R.id.et_guest_alias);
        et_GuestEmail = findViewById(R.id.et_guest_email);
        btn_Send_Invitation = findViewById(R.id.btn_send_user_invitation);
        btn_Send_Invitation.setOnClickListener(v -> sendInvitation(et_GuestAlias.getText().toString(), et_GuestEmail.getText().toString()));

    }

    private void sendInvitation(String guestAlias, String guestEmail){

        guestEmail = guestEmail.trim();
        Boolean isEmailValid = Validations.isValidEmail(guestEmail);

        if (!guestAlias.isEmpty()) {
            if (isEmailValid) {

                AddUserRequest addUserRequest = requestConstructor(guestAlias, guestEmail,userId, deviceId);
                sendRequest(addUserRequest);

            } else {
                Log.e(TAG, "Email error");
                String text = getResources().getString(R.string.enter_valid_email);
                Toast.makeText(getApplicationContext(),text, Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.e(TAG, "Alias error");
            String text = getResources().getString(R.string.guest_alias_error);
            Toast.makeText(getApplicationContext(),text, Toast.LENGTH_SHORT).show();
        }


    }

    private AddUserRequest requestConstructor(String alias, String email, String userId, String deviceId){

        AddUserRequest addUserRequest = new AddUserRequest();
        PermisoControle permisoControl1 = new PermisoControle();
        PermisoControle permisoControl2 = new PermisoControle();
        PermisoControle permisoControl3 = new PermisoControle();
        PermisoControle permisoControl4 = new PermisoControle();
        PermisoControle permisoControl5 = new PermisoControle();
        PermisoControle permisoControl6 = new PermisoControle();

        List<PermisoControle> listPermissions = new ArrayList<>();


        addUserRequest.setAliasUsuarioInvitado(alias);
        addUserRequest.setUsuarioInvitadoEmail(email);
        addUserRequest.setUsuarioAdministradorId(userId);
        addUserRequest.setCercaId(deviceId);

        permisoControl1.setControlId(1);
        permisoControl1.setEstadoPermiso(true);
        listPermissions.add(permisoControl1);

        permisoControl2.setControlId(2);
        permisoControl2.setEstadoPermiso(true);
        listPermissions.add(permisoControl2);

        permisoControl3.setControlId(3);
        permisoControl3.setEstadoPermiso(true);
        listPermissions.add(permisoControl3);

        permisoControl4.setControlId(4);
        permisoControl4.setEstadoPermiso(true);
        listPermissions.add(permisoControl4);

        permisoControl5.setControlId(5);
        permisoControl5.setEstadoPermiso(false);
        listPermissions.add(permisoControl5);

        permisoControl6.setControlId(6);
        permisoControl6.setEstadoPermiso(false);
        listPermissions.add(permisoControl6);

        Log.i("Add user permissions", listPermissions.toString());
        addUserRequest.setPermisoControles(listPermissions);

        //sendRequest(addUserRequest);

        return addUserRequest;
    }

    private void sendRequest(AddUserRequest addUserRequest){

        //Inicializa la construcción de la petición para que "apiManager" traiga la URL Base
        apiManager = ApiConstants.getAPIManager();

        //Una vez que apiManager es asignado, se crea el "call" y se ocupa el api "login" contenido en ApiManager que es la interface de retrofit.
        Call<BaseResponse> call = apiManager.postAddUserToFence(addUserRequest);

        //Se ejecuta la petición y se asigna el objeto LogIn_Response para recibir el Callback del servicio.
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                int typeCode = response.body().getCodigo();
                switch (typeCode){
                    case (ErrorCodes.SUCCESS):
                        //FIXME: No me envía hacia atrás.
                        finish();
                        break;
                    case (ErrorCodes.FAILURE):
                        //TODO: Agregar alertas de error.
                        finish();
                        Toast.makeText(User_Add_Activity.this, "Error al invitar usuario", Toast.LENGTH_SHORT).show();

                        break;

                    default:
                        Log.i("Peticion login", "Algo pasó");
                }


            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
                //TODO: Agregar alerta de error de conexión.
            }
        });

    }

    private void goBack(){
        finish();
    }

}

