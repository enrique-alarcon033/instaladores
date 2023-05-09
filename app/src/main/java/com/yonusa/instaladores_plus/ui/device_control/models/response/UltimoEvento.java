
package com.yonusa.instaladores_plus.ui.device_control.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UltimoEvento {

    @SerializedName("HistorialId")
    @Expose
    private Integer historialId;
    @SerializedName("TextoMostrar")
    @Expose
    private String textoMostrar;
    @SerializedName("Nombre")
    @Expose
    private String nombre;
    @SerializedName("Apellidos")
    @Expose
    private String apellidos;
    @SerializedName("FechaRegistroDato")
    @Expose
    private String fechaRegistroDato;

    public Integer getHistorialId() {
        return historialId;
    }

    public void setHistorialId(Integer historialId) {
        this.historialId = historialId;
    }

    public String getTextoMostrar() {
        return textoMostrar;
    }

    public void setTextoMostrar(String textoMostrar) {
        this.textoMostrar = textoMostrar;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getFechaRegistroDato() {
        return fechaRegistroDato;
    }

    public void setFechaRegistroDato(String fechaRegistroDato) {
        this.fechaRegistroDato = fechaRegistroDato;
    }

}
