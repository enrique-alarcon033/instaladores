package com.yonusa.instaladores_plus.ui.device_control.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetEventsByDateRequest {

    @SerializedName("fechaInicial")
    @Expose
    private String fechaInicial;
    @SerializedName("fechaFinal")
    @Expose
    private String fechaFinal;
    @SerializedName("usuarioId")
    @Expose
    private String usuarioId;
    @SerializedName("cercaId")
    @Expose
    private String cercaId;

    public String getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(String fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public String getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

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
