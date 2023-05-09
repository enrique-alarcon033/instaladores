
package com.yonusa.instaladores_plus.ui.login.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterNotificationsRequest {

    @SerializedName("usuarioId")
    @Expose
    private String usuarioId;
    @SerializedName("dispositivoId")
    @Expose
    private String dispositivoId;
    @SerializedName("notificacionesId")
    @Expose
    private String notificacionesId;
    @SerializedName("hubNotificationId")
    @Expose
    private String hubNotificationId;

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getDispositivoId() {
        return dispositivoId;
    }

    public void setDispositivoId(String dispositivoId) {
        this.dispositivoId = dispositivoId;
    }

    public String getNotificacionesId() {
        return notificacionesId;
    }

    public void setNotificacionesId(String notificacionesId) {
        this.notificacionesId = notificacionesId;
    }

    public String getHubNotificationId() {
        return hubNotificationId;
    }

    public void setHubNotificationId(String hubNotificationId) {
        this.hubNotificationId = hubNotificationId;
    }

}
