
package com.yonusa.instaladores_plus.ui.device_control.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetDeviceControlsResponse {

    @SerializedName("controles")
    @Expose
    private List<Controls> controles = null;
    @SerializedName("UltimoEvento")
    @Expose
    private UltimoEvento ultimoEvento;
    @SerializedName("codigo")
    @Expose
    private Integer codigo;
    @SerializedName("codigoEspecifico")
    @Expose
    private Integer codigoEspecifico;
    @SerializedName("mensaje")
    @Expose
    private String mensaje;

    public List<Controls> getControles() {
        return controles;
    }

    public void setControles(List<Controls> controles) {
        this.controles = controles;
    }

    public UltimoEvento getUltimoEvento() {
        return ultimoEvento;
    }

    public void setUltimoEvento(UltimoEvento ultimoEvento) {
        this.ultimoEvento = ultimoEvento;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigoEspecifico() {
        return codigoEspecifico;
    }

    public void setCodigoEspecifico(Integer codigoEspecifico) {
        this.codigoEspecifico = codigoEspecifico;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}
