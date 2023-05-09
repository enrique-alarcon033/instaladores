package com.yonusa.instaladores_plus.ui.password_recovery.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.yonusa.instaladores_plus.R;
import com.yonusa.instaladores_plus.api.ApiConstants;
import com.yonusa.instaladores_plus.api.ApiManager;
import com.yonusa.instaladores_plus.api.BaseResponse;
import com.yonusa.instaladores_plus.ui.login.view.LogInActivity;
import com.yonusa.instaladores_plus.ui.password_recovery.models.GetRecoveryCodeRequest;
import com.yonusa.instaladores_plus.utilities.Validations;
import com.yonusa.instaladores_plus.utilities.catalogs.ErrorCodes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Password_Recovery_Activity extends AppCompatActivity {

    private static final String TAG = Password_Recovery_Activity.class.getSimpleName();

    TextView tv_email;
    Button btn_send;
    LottieAnimationView loader;


    ApiManager apiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_password_recovery);

        Log.i(TAG, "On create");
        setTitle("Revovery password Yonusa");
        getSupportActionBar().hide();

        tv_email = findViewById(R.id.ti_email);
        btn_send = findViewById(R.id.btn_send_recovery_request);

        btn_send.setOnClickListener(v -> sendRecoveryRequest(tv_email.getText().toString().trim()));

        loader = findViewById(R.id.loader_recovery_pass);


    }

    private void sendRecoveryRequest(String email){

        Boolean isEmailValid = Validations.isValidEmail(email);

        if (isEmailValid) {
            loaderInteractionRestrict();
            GetRecoveryCodeRequest getRecoveryCodeRequest = new GetRecoveryCodeRequest();
            getRecoveryCodeRequest.setEmail(email);

                //Inicializa la construcción de la petición para que "apiManager" traiga la URL Base
                apiManager = ApiConstants.getAPIManager();

                //Una vez que apiManager es asignado, se crea el "call" y se ocupa el api "login" contenido en ApiManager que es la interface de retrofit.
                Call<BaseResponse> call = apiManager.getRecoveryCode(getRecoveryCodeRequest);

                //Se ejecuta la petición y se asigna el objeto LogIn_Response para recibir el Callback del servicio.
                call.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                        int typeCode = response.body().getCodigo();
                        switch (typeCode){
                            case (ErrorCodes.SUCCESS):

                                loaderInteractionEnable();
                                goToSendRecovery(email);

                                break;

                            case (ErrorCodes.FAILURE):

                                loaderInteractionEnable();
                                //TODO: Agregar alertas de error.
                                Toast.makeText(Password_Recovery_Activity.this, R.string.alert_email_error1, Toast.LENGTH_SHORT).show();

                                break;

                            default:
                                Log.i("Peticion login", "Algo paso");
                        }

                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        Log.e("ERROR: ", t.getMessage());
                        loaderInteractionEnable();
                        //TODO: Agregar alerta de error de conexión.
                    }
                });
            } else {

        }
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

    private void goToSendRecovery(String email){

        Intent newPasswordIntent = new Intent(Password_Recovery_Activity.this, Password_Recovery_2_Activity.class);
        newPasswordIntent.putExtra("RECOVERY_EMAIL", email);
        startActivity(newPasswordIntent);


    }
    @Override
    public void onBackPressed() {
        Intent createAccountIntent = new Intent(Password_Recovery_Activity.this, LogInActivity.class);
        startActivity(createAccountIntent);
        finish();
    }


}
