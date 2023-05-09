
package com.yonusa.instaladores_plus.ui.password_recovery.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetRecoveryCodeRequest {

    @SerializedName("email")
    @Expose
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
