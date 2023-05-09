package com.yonusa.instaladores_plus.ui.createAccount.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountActivationRequest {

    @SerializedName("usuarioId")
    @Expose
    private String usuarioId;
    @SerializedName("codigoActivacion")
    @Expose
    private String codigoActivacion;

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getCodigoActivacion() {
        return codigoActivacion;
    }

    public void setCodigoActivacion(String codigoActivacion) {
        this.codigoActivacion = codigoActivacion;
    }

}