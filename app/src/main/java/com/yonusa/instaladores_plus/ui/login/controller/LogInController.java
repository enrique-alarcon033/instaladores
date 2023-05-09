package com.yonusa.instaladores_plus.ui.login.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.yonusa.instaladores_plus.R;
import com.yonusa.instaladores_plus.api.ApiConstants;
import com.yonusa.instaladores_plus.api.ApiManager;
import com.yonusa.instaladores_plus.ui.login.models.LogIn_request;
import com.yonusa.instaladores_plus.ui.login.models.LogIn_response;
import com.yonusa.instaladores_plus.utilities.Validations;
import com.yonusa.instaladores_plus.utilities.catalogs.ErrorCodes;
import com.yonusa.instaladores_plus.utilities.catalogs.SP_Dictionary;
import com.yonusa.instaladores_plus.utilities.helpers.SP_Helper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogInController extends AppCompatActivity {

    ApiManager apiManager;

    public LogInController(){

    }

    public void setLogInRequest(String TAG, Context context, LogIn_request logInRequest){

        Boolean isEmailValid = Validations.isValidEmail(logInRequest.getEmail());
        Boolean isPassValid = Validations.isValidPassword(logInRequest.getPassword());

        if(isEmailValid){
            if(isPassValid) {

                //Inicializa la construcción de la petición para que "apiManager" traiga la URL Base
                apiManager = ApiConstants.getAPIManager();

                //Una vez que apiManager es asignado, se crea el "call" y se ocupa el api "login" contenido en ApiManager que es la interface de retrofit.
                Call<LogIn_response> call = apiManager.logIn(logInRequest);

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
                                        Toast.makeText(context, "Log in complete, Welcome madafuker " + name, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(context, "Log in error, user information could not be saved.", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                break;

                            case (ErrorCodes.FAILURE):
                                //TODO: Agregar alertas de error.
                                Toast.makeText(context, "Revisa tus credenciales", Toast.LENGTH_SHORT).show();

                                break;

                            default:
                                Log.i("Peticion login", "ALgo paso");
                        }


                    }

                    @Override
                    public void onFailure(Call<LogIn_response> call, Throwable t) {
                        Log.e("ERROR: ", t.getMessage());
                        //TODO: Agregar alerta de error de conexión.
                    }
                });
            } else {
                Log.e(TAG, "Password error");
                String text = getResources().getString(R.string.enter_valid_password);
                Toast.makeText(getApplicationContext(),text, Toast.LENGTH_SHORT).show();
            }
        } else {

            Log.e(TAG, "Email error");
            String text = getResources().getString(R.string.enter_valid_email);
            Toast.makeText(getApplicationContext(),text, Toast.LENGTH_SHORT).show();

        }
    }
}
