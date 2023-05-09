
package com.yonusa.instaladores_plus.ui.view.models.request.get_device_users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetDeviceUsersRequest {

    @SerializedName("cercaId")
    @Expose
    private String cercaId;
    @SerializedName("usuarioAdministradorId")
    @Expose
    private String usuarioAdministradorId;

    public String getCercaId() {
        return cercaId;
    }

    public void setCercaId(String cercaId) {
        this.cercaId = cercaId;
    }

    public String getUsuarioAdministradorId() {
        return usuarioAdministradorId;
    }

    public void setUsuarioAdministradorId(String usuarioAdministradorId) {
        this.usuarioAdministradorId = usuarioAdministradorId;
    }

}
