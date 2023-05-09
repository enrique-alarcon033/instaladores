
package com.yonusa.instaladores_plus.ui.view.models.request.update_permissions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpdateUserPermissionsRequest {

    @SerializedName("usuarioAdministradorId")
    @Expose
    private String usuarioAdministradorId;
    @SerializedName("usuarioInvitadoId")
    @Expose
    private String usuarioInvitadoId;
    @SerializedName("cercaId")
    @Expose
    private String cercaId;
    @SerializedName("permisoControles")
    @Expose
    private List<PermisoControles> permisoControles = null;

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

    public List<PermisoControles> getPermisoControles() {
        return permisoControles;
    }

    public void setPermisoControles(List<PermisoControles> permisoControles) {
        this.permisoControles = permisoControles;
    }

}
