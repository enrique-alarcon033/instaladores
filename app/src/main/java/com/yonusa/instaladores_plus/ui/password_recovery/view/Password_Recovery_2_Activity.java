package com.yonusa.instaladores_plus.ui.password_recovery.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.yonusa.instaladores_plus.R;
import com.yonusa.instaladores_plus.api.ApiConstants;
import com.yonusa.instaladores_plus.api.ApiManager;
import com.yonusa.instaladores_plus.ui.homeScreen.view.HomeActivity;
import com.yonusa.instaladores_plus.ui.login.models.LogIn_response;
import com.yonusa.instaladores_plus.ui.password_recovery.models.UpdatePasswordRequest;
import com.yonusa.instaladores_plus.utilities.Validations;
import com.yonusa.instaladores_plus.utilities.catalogs.ErrorCodes;
import com.yonusa.instaladores_plus.utilities.catalogs.SP_Dictionary;
import com.yonusa.instaladores_plus.utilities.helpers.SP_Helper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Password_Recovery_2_Activity extends AppCompatActivity {

    private static final String TAG = Password_Recovery_2_Activity.class.getSimpleName();

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

        setContentView(R.layout.activity_password_recovery_2);
        email = getIntent().getStringExtra("RECOVERY_EMAIL");
        Log.i(TAG, "On create");
        setTitle("Revovery password Yonusa");
        getSupportActionBar().hide();

        et_recovery_code = findViewById(R.id.et_recovery_code);
        et_new_password = findViewById(R.id.et_password_recovery);
        et_new_password_confirmation = findViewById(R.id.et_password_recovery_confirm);

        btn_update_password = findViewById(R.id.btn_update_password);
        btn_update_password.setOnClickListener(v -> updatePasswordValidations(email));
        loader = findViewById(R.id.loader_set_new_pass);



    }

    private void updatePasswordValidations(String email){

        if (et_recovery_code.getText().toString().isEmpty()) {

            Toast.makeText(Password_Recovery_2_Activity.this, "Código de recuperación vacío", Toast.LENGTH_SHORT).show();

        } else {

            recovery_code = et_recovery_code.getText().toString();
            newPass = et_new_password.getText().toString();
            confirmPass = et_new_password_confirmation.getText().toString();

            Boolean isPassValid = Validations.isValidPassword(newPass);

            if (isPassValid) {

                if (newPass.equals(confirmPass)){
                    loaderInteractionRestrict();
                    sendNewPassword(email, recovery_code, newPass);

                } else {

                    Toast.makeText(Password_Recovery_2_Activity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();

                }

            } else {

                Toast.makeText(Password_Recovery_2_Activity.this, "Contraseña inválida", Toast.LENGTH_SHORT).show();
            }

        }


    }

    private void sendNewPassword(String email, String recoveryCode, String password){

        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest();
        updatePasswordRequest.setEmail(email);
        updatePasswordRequest.setCodigoRecuperacionCuenta(recoveryCode);
        updatePasswordRequest.setPassword(password);

        //Inicializa la construcción de la petición para que "apiManager" traiga la URL Base
        apiManager = ApiConstants.getAPIManager();

        //Una vez que apiManager es asignado, se crea el "call" y se ocupa el api "login" contenido en ApiManager que es la interface de retrofit.
        Call<LogIn_response> call = apiManager.updatePassword(updatePasswordRequest);

        //Se ejecuta la petición y se asigna el objeto LogIn_Response para recibir el Callback del servicio.
        call.enqueue(new Callback<LogIn_response>() {
            @Override
            public void onResponse(Call<LogIn_response> call, Response<LogIn_response> response) {

                int typeCode = response.body().getCodigo();
                switch (typeCode){
                    case (ErrorCodes.SUCCESS):
                        if (response.isSuccessful()) {

                            SP_Helper sp_helper = new SP_Helper();

                            Boolean userInfoSaved = sp_helper.fillUserInfo(context,response.body());
                            if (userInfoSaved){
                                SharedPreferences prefs = getSharedPreferences(SP_Dictionary.USER_INFO, MODE_PRIVATE);
                                String name = prefs.getString(SP_Dictionary.USER_NAME, "No name defined");
                                Toast.makeText(Password_Recovery_2_Activity.this, name + ", tu contraseña cambió.", Toast.LENGTH_SHORT).show();
                                loaderInteractionEnable();
                                goToHomeScreen();
                            } else {
                                loaderInteractionEnable();
                                Toast.makeText(Password_Recovery_2_Activity.this, "Log in error, user information could not be saved.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        break;

                    case (ErrorCodes.FAILURE):
                        loaderInteractionEnable();
                        //TODO: Agregar alertas de error.
                        Toast.makeText(Password_Recovery_2_Activity.this, "Código incorrecto", Toast.LENGTH_SHORT).show();

                        break;

                    default:
                        Log.i("Peticion login", "Algo paso");
                }

            }

            @Override
            public void onFailure(Call<LogIn_response> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
                loaderInteractionEnable();
                Toast.makeText(Password_Recovery_2_Activity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                //TODO: Agregar alerta de error de conexión.
            }
        });

    }

    public void loaderInteractionRestrict(){
        loader.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public void loaderInteractionEnable(){
        loader.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public void goToHomeScreen(){
        Intent createAccountIntent = new Intent(Password_Recovery_2_Activity.this, HomeActivity.class);
        startActivity(createAccountIntent);
    }

}
