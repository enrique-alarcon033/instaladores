
package com.yonusa.instaladores_plus.ui.view.models.request.add_user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddUserRequest {

    @SerializedName("usuarioAdministradorId")
    @Expose
    private String usuarioAdministradorId;
    @SerializedName("usuarioInvitadoEmail")
    @Expose
    private String usuarioInvitadoEmail;
    @SerializedName("aliasUsuarioInvitado")
    @Expose
    private String aliasUsuarioInvitado;
    @SerializedName("cercaId")
    @Expose
    private String cercaId;
    @SerializedName("PermisoControles")
    @Expose
    private List<PermisoControle> permisoControles = null;

    public String getUsuarioAdministradorId() {
        return usuarioAdministradorId;
    }

    public void setUsuarioAdministradorId(String usuarioAdministradorId) {
        this.usuarioAdministradorId = usuarioAdministradorId;
    }

    public String getUsuarioInvitadoEmail() {
        return usuarioInvitadoEmail;
    }

    public void setUsuarioInvitadoEmail(String usuarioInvitadoEmail) {
        this.usuarioInvitadoEmail = usuarioInvitadoEmail;
    }

    public String getAliasUsuarioInvitado() {
        return aliasUsuarioInvitado;
    }

    public void setAliasUsuarioInvitado(String aliasUsuarioInvitado) {
        this.aliasUsuarioInvitado = aliasUsuarioInvitado;
    }

    public String getCercaId() {
        return cercaId;
    }

    public void setCercaId(String cercaId) {
        this.cercaId = cercaId;
    }

    public List<PermisoControle> getPermisoControles() {
        return permisoControles;
    }

    public void setPermisoControles(List<PermisoControle> permisoControles) {
        this.permisoControles = permisoControles;
    }

}
