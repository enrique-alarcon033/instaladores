package com.yonusa.instaladores_plus.ui.homeScreen.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeScreenRequest {

    @SerializedName("usuarioId")
    @Expose
    private String usuarioId;

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

}