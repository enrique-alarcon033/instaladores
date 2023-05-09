package com.yonusa.instaladores_plus.ui.device_control.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SetNewControlNameRequest {

    @SerializedName("cercaId")
    @Expose
    private String cercaId;
    @SerializedName("usuarioId")
    @Expose
    private String usuarioId;
    @SerializedName("controlId")
    @Expose
    private Integer controlId;
    @SerializedName("nuevoAliasControl")
    @Expose
    private String nuevoAliasControl;

    public String getCercaId() {
        return cercaId;
    }

    public void setCercaId(String cercaId) {
        this.cercaId = cercaId;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getControlId() {
        return controlId;
    }

    public void setControlId(Integer controlId) {
        this.controlId = controlId;
    }

    public String getNuevoAliasControl() {
        return nuevoAliasControl;
    }

    public void setNuevoAliasControl(String nuevoAliasControl) {
        this.nuevoAliasControl = nuevoAliasControl;
    }

}
