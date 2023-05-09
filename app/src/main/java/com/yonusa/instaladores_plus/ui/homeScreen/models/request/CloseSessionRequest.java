
package com.yonusa.instaladores_plus.ui.homeScreen.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CloseSessionRequest {

    @SerializedName("usuarioId")
    @Expose
    private String usuarioId;
    @SerializedName("dispositivoId")
    @Expose
    private String dispositivoId;

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getDispositivoId() {
        return dispositivoId;
    }

    public void setDispositivoId(String dispositivoId) {
        this.dispositivoId = dispositivoId;
    }

}
