
package com.yonusa.instaladores_plus.ui.view.models.request.get_users_permissions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetUserPermissionsRequest {

    @SerializedName("usuarioAdministradorId")
    @Expose
    private String usuarioAdministradorId;
    @SerializedName("usuarioInvitadoId")
    @Expose
    private String usuarioInvitadoId;
    @SerializedName("cercaId")
    @Expose
    private String cercaId;

    public String getUsuarioAdministradorId() {
        return usuarioAdministradorId;
    }

    public void setUsuarioAdministradorId(String usuarioAdministradorId) {
        this.usuarioAdministradorId = usuarioAdministradorId;
    }

    public String getUsuarioInvitadoId() {
        return usuarioInvitadoId;
    }

    public void setUsuarioInvitadoId(String usuarioInvitadoId) {
        this.usuarioInvitadoId = usuarioInvitadoId;
    }

    public String getCercaId() {
        return cercaId;
    }

    public void setCercaId(String cercaId) {
        this.cercaId = cercaId;
    }

}
