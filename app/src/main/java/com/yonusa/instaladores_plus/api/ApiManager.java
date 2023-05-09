package com.yonusa.instaladores_plus.api;

import com.yonusa.instaladores_plus.ui.add_devices.list_of_devices.request.GetConfigParamsRequest;
import com.yonusa.instaladores_plus.ui.add_devices.list_of_devices.response.GetConfigParamsResponse;
import com.yonusa.instaladores_plus.ui.createAccount.models.CountActivationRequest;
import com.yonusa.instaladores_plus.ui.createAccount.models.RegistrarUsuarioRequest;
import com.yonusa.instaladores_plus.ui.createAccount.models.RegistrarUsuarioResponse;
import com.yonusa.instaladores_plus.ui.createAccount.models.VerificationCodeRequest;
import com.yonusa.instaladores_plus.ui.createAccount.models.VerificationCodeResponse;
import com.yonusa.instaladores_plus.ui.device_control.models.request.GetDeviceControlsRequest;
import com.yonusa.instaladores_plus.ui.device_control.models.request.GetEventsByDateRequest;
import com.yonusa.instaladores_plus.ui.device_control.models.request.SetNewControlNameRequest;
import com.yonusa.instaladores_plus.ui.device_control.models.response.GetDeviceControlsResponse;
import com.yonusa.instaladores_plus.ui.device_control.models.response.GetEventsByDateResponse;
import com.yonusa.instaladores_plus.ui.homeScreen.models.request.ChangeDeviceNameRequest;
import com.yonusa.instaladores_plus.ui.homeScreen.models.request.CloseSessionRequest;
import com.yonusa.instaladores_plus.ui.homeScreen.models.request.HomeScreenRequest;
import com.yonusa.instaladores_plus.ui.homeScreen.models.response.HomeScreenResponse;
import com.yonusa.instaladores_plus.ui.login.models.LogIn_request;
import com.yonusa.instaladores_plus.ui.login.models.LogIn_response;
import com.yonusa.instaladores_plus.ui.login.models.RegisterNotificationsRequest;
import com.yonusa.instaladores_plus.ui.password_recovery.models.GetRecoveryCodeRequest;
import com.yonusa.instaladores_plus.ui.password_recovery.models.UpdatePasswordRequest;
import com.yonusa.instaladores_plus.ui.view.models.request.add_user.AddUserRequest;
import com.yonusa.instaladores_plus.ui.view.models.request.get_device_users.GetDeviceUsersRequest;
import com.yonusa.instaladores_plus.ui.view.models.request.get_users_permissions.GetUserPermissionsRequest;
import com.yonusa.instaladores_plus.ui.view.models.request.update_permissions.UpdateUserPermissionsRequest;
import com.yonusa.instaladores_plus.ui.view.models.response.GetDeviceUsersResponse;
import com.yonusa.instaladores_plus.ui.view.models.response.GetUserPermissionsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiManager {

    //region Create Account
    @POST(ApiConstants.URN_REGISTER_USER)
    Call<RegistrarUsuarioResponse> registerUser(@Body RegistrarUsuarioRequest RegistrarUsuarioRequest);

    @POST(ApiConstants.URN_REQUEST_ACTIVATION_CODE)
    Call<VerificationCodeResponse> activationCode(@Body VerificationCodeRequest VerificationCodeRequest);

    @POST(ApiConstants.URN_ACTIVATE_ACCOUNT)
    Call<LogIn_response> CountActivation(@Body CountActivationRequest CountActivationRequest);
    //endregion

    //region Log in
    @POST(ApiConstants.URN_LOG_IN)
    Call<LogIn_response> logIn(@Body LogIn_request logInRequest);
    //endregion

    //region Recovery password
    @POST(ApiConstants.URN_GET_RECOVERY_CODE)
    Call<BaseResponse> getRecoveryCode(@Body GetRecoveryCodeRequest getRecoveryCodeRequest);

    @POST(ApiConstants.URN_UPDATE_PASS)
        //Se ocupa el mismo response por que el servicio regresa la misma respuesta por algun motivo extraño.
    Call<LogIn_response> updatePassword(@Body UpdatePasswordRequest updatePasswordRequest);
    //endregion

    //region Home Screen
    @POST(ApiConstants.URN_GET_DEVICES)
    Call<HomeScreenResponse> getDevices(@Body HomeScreenRequest homeScreenRequest);
    //endregion

    //region Device's controls
    @POST(ApiConstants.URN_GET_EVENT_BY_DATE)
    Call<GetEventsByDateResponse> getEventsByDate(@Body GetEventsByDateRequest getEventsByDateRequest);

    @POST(ApiConstants.URN_SET_NEW_CONTROL_NAME)
    Call<BaseResponse> setControlName(@Body SetNewControlNameRequest setNewControlNameRequest);
    //endregion

    //region History by date range
    @POST(ApiConstants.URN_GET_CONTROLS)
    Call<GetDeviceControlsResponse> getDeviceControls(@Body GetDeviceControlsRequest getDeviceControlsRequest);
    //endregion

    //region User Administration
    @POST(ApiConstants.URN_GET_USERS_FENCE)
    Call<GetDeviceUsersResponse> getDeviceUsers(@Body GetDeviceUsersRequest getDeviceUsersRequest);

    @POST(ApiConstants.URN_ADD_USER_FENCE)
    Call<BaseResponse> postAddUserToFence(@Body AddUserRequest addUserRequest);

    @POST(ApiConstants.URN_GET_USERS_PERMISSIONS)
    Call<GetUserPermissionsResponse> getUserPermissions(@Body GetUserPermissionsRequest getUserPermissionsRequest);

    @POST(ApiConstants.URN_USERS_CHANGE_PERMISSIONS)
    Call<BaseResponse> updateUserPermissions(@Body UpdateUserPermissionsRequest updateUserPermissionsRequest);
    //endregion

    //device region
    @POST(ApiConstants.URN_CHANGE_DEVICE_NAME)
    Call<BaseResponse> changeDeviceName(@Body ChangeDeviceNameRequest changeDeviceNameRequest);
    //end Region

    @POST(ApiConstants.URN_GET_CONFIG_PARAMS)
    Call<GetConfigParamsResponse> getDeviceConfigParameters(@Body GetConfigParamsRequest getConfigParamsRequest);

    @POST(ApiConstants.URN_REGISTER_ID_NOTIFICATION)
    Call<BaseResponse> postRegisterIdNotification(@Body RegisterNotificationsRequest registerNotificationsRequest);

    @POST(ApiConstants.URN_LOG_OUT)
    Call<BaseResponse> postCloseSession(@Body CloseSessionRequest closeSessionRequest);

}
