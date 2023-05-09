package com.yonusa.instaladores_plus.ui.homeScreen.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangeDeviceNameRequest {

    @SerializedName("cercaId")
    @Expose
    private String cercaId;
    @SerializedName("usuarioId")
    @Expose
    private String usuarioId;
    @SerializedName("nuevoAliasCerca")
    @Expose
    private String nuevoAliasCerca;

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

    public String getNuevoAliasCerca() {
        return nuevoAliasCerca;
    }

    public void setNuevoAliasCerca(String nuevoAliasCerca) {
        this.nuevoAliasCerca = nuevoAliasCerca;
    }

}