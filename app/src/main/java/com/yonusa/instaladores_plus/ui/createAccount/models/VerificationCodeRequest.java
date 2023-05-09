package com.yonusa.instaladores_plus.ui.createAccount.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerificationCodeRequest {

    @SerializedName("usuarioId")
    @Expose
    private String usuarioId;
    @SerializedName("telefono")
    @Expose
    private String telefono;
    @SerializedName("email")
    @Expose
    private String email;

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}