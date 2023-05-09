
package com.yonusa.instaladores_plus.ui.homeScreen.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeScreenResponse {

    @SerializedName("codigo")
    @Expose
    private Integer codigo;
    @SerializedName("codigoEspecifico")
    @Expose
    private Integer codigoEspecifico;
    @SerializedName("mensaje")
    @Expose
    private String mensaje;
    @SerializedName("cercas")
    @Expose
    private List<Cerca> cercas = null;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public HomeScreenResponse withCodigo(Integer codigo) {
        this.codigo = codigo;
        return this;
    }

    public Integer getCodigoEspecifico() {
        return codigoEspecifico;
    }

    public void setCodigoEspecifico(Integer codigoEspecifico) {
        this.codigoEspecifico = codigoEspecifico;
    }

    public HomeScreenResponse withCodigoEspecifico(Integer codigoEspecifico) {
        this.codigoEspecifico = codigoEspecifico;
        return this;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public HomeScreenResponse withMensaje(String mensaje) {
        this.mensaje = mensaje;
        return this;
    }

    public List<Cerca> getCercas() {
        return cercas;
    }

    public void setCercas(List<Cerca> cercas) {
        this.cercas = cercas;
    }

    public HomeScreenResponse withCercas(List<Cerca> cercas) {
        this.cercas = cercas;
        return this;
    }

}
