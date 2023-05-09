package com.yonusa.instaladores_plus.ui.login.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.yonusa.instaladores_plus.R;
import com.yonusa.instaladores_plus.api.ApiConstants;
import com.yonusa.instaladores_plus.api.ApiManager;
import com.yonusa.instaladores_plus.api.BaseResponse;
import com.yonusa.instaladores_plus.ui.cercas.Lista_cercas;
import com.yonusa.instaladores_plus.ui.createAccount.view.Create_Account_Activity;
import com.yonusa.instaladores_plus.ui.login.models.LogIn_request;
import com.yonusa.instaladores_plus.ui.login.models.LogIn_response;
import com.yonusa.instaladores_plus.ui.login.models.RegisterNotificationsRequest;
import com.yonusa.instaladores_plus.ui.password_recovery.view.Password_Recovery_Activity;
import com.yonusa.instaladores_plus.utilities.catalogs.Constants;
import com.yonusa.instaladores_plus.utilities.catalogs.ErrorCodes;
import com.yonusa.instaladores_plus.utilities.catalogs.SP_Dictionary;
import com.yonusa.instaladores_plus.utilities.firebaseService.FirebaseCloudMessagingService;
import com.yonusa.instaladores_plus.utilities.firebaseService.RegistrationIntentService;
import com.yonusa.instaladores_plus.utilities.helpers.SP_Helper;

import java.security.MessageDigest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogInActivity extends AppCompatActivity {

    private EditText edtEmail;
    private EditText edtPass;
    private Button btnShow;
    private Button btnLogin;
    private Button btnRecoveryPassword;
    private Button btnCreateAccount;

    private String email;
    private String pass;

    Context context = this;
    ApiManager apiManager;

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;


    private static final String TAG = LogInActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.i(TAG, "On ");
        setTitle("LogIn Yonusa");

        getSupportActionBar().hide();

        edtEmail = findViewById(R.id.email_editText);
        edtPass = findViewById(R.id.pass_editText);

        btnLogin = findViewById(R.id.login_button);
        btnLogin.setOnClickListener(view -> loginConstruction());

        btnCreateAccount = findViewById(R.id.btn_create_account);
        btnCreateAccount.setOnClickListener(view -> goToCreateAccount());

        btnShow = findViewById(R.id.btn_show_pass);
        btnShow.setTag("hide");
        btnShow.setOnClickListener(view -> showPassword());

        btnRecoveryPassword = findViewById(R.id.btn_recovery_pass);
        btnRecoveryPassword.setOnClickListener(view -> goToRecoveryPass());

        SP_Helper sp_helper = new SP_Helper();

        //Persistencia de sesión
        try {

            SharedPreferences prefs = getSharedPreferences(SP_Dictionary.USER_INFO, MODE_PRIVATE);
            String userId = prefs.getString(SP_Dictionary.USER_ID, "No userId defined");

            if (userId == "No userId defined"){
                //Si no existe userId se borra la informacion del usuario si existe.
                sp_helper.cleanUserInfo(context);
            } else {
                //Si existe userId guardado se registra el dispositivo para recibir notificaciones y despues se envía directo a Home screen.

                String notificationId = prefs.getString("FCMtoken", "No notificationId defined");
                String uniqueId = prefs.getString("UniqueId: ", "No UniqueId defined");

                Log.i(TAG, notificationId);
                //String uniqueId = "TESTING-UNIQUEID080890";
                uniqueId = md5(uniqueId);
                Log.i(TAG, "Md5 UniqueId" + uniqueId);
                String registrationID = prefs.getString("registrationID", "No notificationId defined");

                if (!notificationId.equals("No notificationId defined")) {

                    registerUserWithDeviceToNotifications(userId, notificationId, uniqueId, Constants.HUB_NOTIFICATION_ID);
                    goToHomeScreen();

                } else {
                    registerWithNotificationHubs();
                    FirebaseCloudMessagingService.createChannelAndHandleNotifications(getApplicationContext());

                    Thread.sleep(400);

                    registerUserWithDeviceToNotifications(userId, notificationId, uniqueId, Constants.HUB_NOTIFICATION_ID);
                    goToHomeScreen();
                    //Toast.makeText(LogInActivity.this, "Restart your application", Toast.LENGTH_SHORT).show();

                }

            }

        } catch(Exception e){
            //Si algo falla en la carga del shared preference no se muestra nada y se permanece en log in.
            Toast.makeText(LogInActivity.this, "User id inexistente", Toast.LENGTH_SHORT).show();

        }

    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();

            } else {
                Log.i(TAG, "This device is not supported by Google Play Services.");
                Toast.makeText(LogInActivity.this, "This device is not supported by Google Play Services.", Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }

    public void registerWithNotificationHubs()
    {
        if (checkPlayServices()) {
            // Start IntentService to register this application with FCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
    }

    public void registerUserWithDeviceToNotifications(String userId, String notificationsId, String deviceId, String hubNotificationId){

        RegisterNotificationsRequest registerNotificationsRequest= new RegisterNotificationsRequest();
        registerNotificationsRequest.setUsuarioId(userId);
        registerNotificationsRequest.setDispositivoId(deviceId);
        registerNotificationsRequest.setNotificacionesId(notificationsId);
        registerNotificationsRequest.setHubNotificationId(hubNotificationId);


        Log.i(TAG, "NOTIFICATION ID: ------>" + registerNotificationsRequest.getNotificacionesId());
        Log.i(TAG, "NOTIFICATION DEVICE ID: ------>" + registerNotificationsRequest.getDispositivoId());

        apiManager = ApiConstants.getAPIManager();

        Call<BaseResponse> call = apiManager.postRegisterIdNotification(registerNotificationsRequest);

        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {


               /* int typeCode = response.body().getCodigo();

                switch (typeCode) {
                    case (ErrorCodes.SUCCESS):
                        Log.i(TAG,"Dispositivo registrado para notificaciones.");
                        Log.i(TAG, response.body().getMensaje());
                        break;
                    case (ErrorCodes.FAILURE):
                        Log.e(TAG,"Dispositivo NO registrado para notificaciones.");
                        Log.e(TAG, response.body().getMensaje());
                        break;
                } */
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        registerWithNotificationHubs();
        FirebaseCloudMessagingService.createChannelAndHandleNotifications(getApplicationContext());
    }

    public void loginConstruction() {

        LogIn_request logIn_request = new LogIn_request();
        email = edtEmail.getText().toString();
        pass = edtPass.getText().toString();
        logIn_request.setEmail(email);
        logIn_request.setPassword(pass);
        setLogInRequest(logIn_request);

    }

    public void setLogInRequest(LogIn_request logInRequest){

        //Lo que se comentó en este método es para evitar hacer validaciiones de Email, o contraseña, cualquier error que salga de aquí es mostrado directamente por backEnd
        //Boolean isEmailValid = Validations.isValidEmail(logInRequest.getEmail());
        //Boolean isPassValid = Validations.isValidPassword(logInRequest.getPassword());

        //if(isEmailValid){
          //  if(isPassValid) {

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

                                        edtEmail.setText("");
                                        edtPass.setText("");

                                        SharedPreferences prefs = getSharedPreferences(SP_Dictionary.USER_INFO, MODE_PRIVATE);

                                        String notificationId = prefs.getString("FCMtoken", "No notificationId defined");
                                        String uniqueId = prefs.getString("UniqueId", "No UniqueId defined");

                                        //String uniqueId = "TESTING-UNIQUEID080890";
                                        uniqueId = md5(uniqueId);
                                        Log.i(TAG, "Md5 UniqueId" + uniqueId);
                                        String registrationID = prefs.getString("registrationID", "No notificationId defined");
                                        String userId = prefs.getString(SP_Dictionary.USER_ID, "No userId defined");

                                        registerUserWithDeviceToNotifications(userId, notificationId, uniqueId, Constants.HUB_NOTIFICATION_ID);

                                        String name = prefs.getString(SP_Dictionary.USER_NAME, "No name defined");

                                        Toast.makeText(LogInActivity.this, "Log in complete, Welcome " + name, Toast.LENGTH_SHORT).show();

                                        goToHomeScreen();

                                    } else {
                                        Toast.makeText(LogInActivity.this, "Log in error, user information could not be saved.", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                break;

                            case (ErrorCodes.FAILURE):
                                //TODO: Agregar alertas de error.
                                Toast.makeText(LogInActivity.this, "Revisa tus credenciales", Toast.LENGTH_SHORT).show();

                                break;

                            default:
                                Log.i("Peticion login", "Algo paso");
                        }

                    }

                    @Override
                    public void onFailure(Call<LogIn_response> call, Throwable t) {
                        Log.e("ERROR: ", t.getMessage());
                        //TODO: Agregar alerta de error de conexión.
                    }
                });
            //} else {
              //  Log.e(TAG, "Password error");
                //String text = getResources().getString(R.string.enter_valid_password);
                //Toast.makeText(getApplicationContext(),text, Toast.LENGTH_SHORT).show();
           // }
        //} else {

          //  Log.e(TAG, "Email error");
            //String text = getResources().getString(R.string.enter_valid_email);
            //Toast.makeText(getApplicationContext(),text, Toast.LENGTH_SHORT).show();
//        }
    }

    public static final String md5(final String toEncrypt) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("md5");
            digest.update(toEncrypt.getBytes());
            final byte[] bytes = digest.digest();
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(String.format("%02X", bytes[i]));
            }
            return sb.toString().toLowerCase();
        } catch (Exception exc) {
            return ""; // Impossibru!
        }
    }

    public void showPassword(){

        int pass_length = edtPass.getText().length();

        if(btnShow.getTag() == "hide"){
            edtPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            btnShow.setTag("show");
            edtPass.setSelection(pass_length);
        } else {
            edtPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            btnShow.setTag("hide");
            edtPass.setSelection(pass_length);
        }

    }

    public void goToRecoveryPass(){
        Intent createAccountIntent = new Intent(LogInActivity.this, Password_Recovery_Activity.class);
        startActivity(createAccountIntent);
    }

    public void goToCreateAccount(){
        Intent createAccountIntent = new Intent(LogInActivity.this, Create_Account_Activity.class);
        startActivity(createAccountIntent);
    }

    public void goToHomeScreen(){
        Intent createAccountIntent = new Intent(LogInActivity.this, Lista_cercas.class);

        startActivity(createAccountIntent);
    }

    private Boolean exit = false;

    public void onBackPressed() {
        if (exit) {
        //    finish(); // finish activity
        } else {

            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    Intent a = new Intent(Intent.ACTION_MAIN);
                    a.addCategory(Intent.CATEGORY_HOME);
                    a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(a);
                }
            }, 1000);
        }
    }
}
