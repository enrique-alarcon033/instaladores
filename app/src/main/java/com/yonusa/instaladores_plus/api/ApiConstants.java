package com.yonusa.instaladores_plus.api;

public class ApiConstants {

    private ApiConstants() {
    }

    //Production/Development flag
    private static final Boolean IS_PRO = true;

    //region Production/Development Base Urls
    private static final String PRO_URL = "http://payonusa.com/api/";
    private static final String PRO_URL_OLD = "http://payonusa.com/api/http://payonusa.com/paniagua/instalador/api/v1/";
    private static final String DEV_URL = "http://dev.payonusa.com/api";

    public static final String API_URL = (IS_PRO) ? PRO_URL : DEV_URL;

    public static ApiManager getAPIManager() {
        return RetrofitClient.getClient(API_URL).create(ApiManager.class);
    }

    //endregion

    //region User Account
    public static final String URN_REGISTER_USER = "instaladores"; //Crear cuenta de usuario instalador... cambiar la ruta del end point por el nombre del archivo corrrespondiente
    public static final String URN_REQUEST_ACTIVATION_CODE = "SolicitarCodigoActivacion";//Se solicita al entrar a la segunda pantalla sin esperar interacción del usuario
    public static final String URN_ACTIVATE_ACCOUNT = "ActivarCuentaUsuario";//Se envian
    public static final String URL_TERMINOS_CONDICIONES = "file:///android_asset/terminos.htm"; //politicas de privacidad
    //endregion


    public static final String URN_LOG_IN = "IniciarSesion";
    public static final String URN_LOG_OUT = "CerrarSesion";

    //region Password recovery
    public static final String URN_GET_RECOVERY_CODE = "SolicitarCodigoRecuperacionCuenta";
    public static final String URN_UPDATE_PASS = "ActualizarPassword";
    //endregion

    //region Device Register
    public static final String URN_GET_CONFIG_PARAMS = "ObtenerParametrosConfiguracion"; //Este servicio se utiliza para obtener el UUID del Hardware y los parámetros de configuración de MQTT.
    public static final String URN_REGISTER_DEVICE = "RegistrarCerca";
    public static final String URN_FINAL_STEP_ETHERNET = "FinalizarRegistroCercaEthernet";

    public static final String CONFIG_WI_FI = "192.168.4.1";
    public static final int PORT_WI_FI = 9001;
    public static final String CMD_1 = "_j0Hef76EMe9N_cmd1_";

    public static final String SEND_PARAMS_WIFI = "_j0Hef76EMe9N_cmd2_";
    //endregion

    //region User Devices.
    public static final String URN_GET_DEVICES = "ObtenerCercasUsuario";
    //endregion

    //region Device's controls
    public static final String URN_GET_CONTROLS = "ObtenerControlesCerca";
    public static final String URN_SET_NEW_CONTROL_NAME = "CambiarAliasControl";
    //endregion

    //region History
    public static final String URN_GET_HISTORY = "ObtenerHistorialPorFecha";
    //endregion

    //Events region
    public static final String URN_GET_EVENT_BY_DATE = "ObtenerHistorialPorFecha";
    //endregion

    public static final String URN_REGISTER_ID_NOTIFICATION = "RegistrarDispositivo";
    public static final String URN_CONFIRM_NOTIFICATION = "ConfirmarNotificacion";

    //region User Administration
    public static final String URN_GET_USERS_FENCE = "ObtenerUsuariosCerca";
    public static final String URN_DELETE_USER_FENCE = "EliminarUsuarioCerca";
    public static final String URN_ADD_USER_FENCE = "AgregarUsuarioCerca";
    public static final String URN_GET_USERS_PERMISSIONS = "ObtenerControlesUsuario";
    public static final String URN_USERS_CHANGE_PERMISSIONS = "CambiarPermisosControles";
    //endregion

    //region device
    public static final String URN_CHANGE_DEVICE_NAME = "CambiarAliasCerca";
    //endregion
}
