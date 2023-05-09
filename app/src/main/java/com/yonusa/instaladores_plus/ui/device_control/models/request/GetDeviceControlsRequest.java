
package com.yonusa.instaladores_plus.ui.device_control.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetDeviceControlsRequest {

    @SerializedName("usuarioId")
    @Expose
    private String usuarioId;
    @SerializedName("cercaId")
    @Expose
    private String cercaId;

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getCercaId() {
        return cercaId;
    }

    public void setCercaId(String cercaId) {
        this.cercaId = cercaId;
    }

}
