
package com.yonusa.instaladores_plus.ui.password_recovery.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdatePasswordRequest {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("codigoRecuperacionCuenta")
    @Expose
    private String codigoRecuperacionCuenta;
    @SerializedName("password")
    @Expose
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCodigoRecuperacionCuenta() {
        return codigoRecuperacionCuenta;
    }

    public void setCodigoRecuperacionCuenta(String codigoRecuperacionCuenta) {
        this.codigoRecuperacionCuenta = codigoRecuperacionCuenta;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
