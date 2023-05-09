
package com.yonusa.instaladores_plus.ui.homeScreen.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cerca {

    @SerializedName("rol")
    @Expose
    private Integer rol;
    @SerializedName("aliasCerca")
    @Expose
    private String aliasCerca;
    @SerializedName("cercaId")
    @Expose
    private String cercaId;
    @SerializedName("MAC")
    @Expose
    private String mAC;
    @SerializedName("modelo")
    @Expose
    private String modelo;
    @SerializedName("estadoBateria")
    @Expose
    private Integer estadoBateria;
    @SerializedName("estadoConexionCorriente")
    @Expose
    private Boolean estadoConexionCorriente;
    @SerializedName("estadoConexionAlSistema")
    @Expose
    private Boolean estadoConexionAlSistema;
    @SerializedName("estadoAlarma")
    @Expose
    private Boolean estadoAlarma;
    @SerializedName("IP")
    @Expose
    private String iP;
    @SerializedName("puertaEnlace")
    @Expose
    private String puertaEnlace;
    @SerializedName("mascaraSubred")
    @Expose
    private String mascaraSubred;
    @SerializedName("DNS1")
    @Expose
    private String dNS1;
    @SerializedName("DNS2")
    @Expose
    private String dNS2;
    @SerializedName("SSID")
    @Expose
    private String sSID;
    @SerializedName("latitud")
    @Expose
    private String latitud;
    @SerializedName("longitud")
    @Expose
    private String longitud;

    public Integer getRol() {
        return rol;
    }

    public void setRol(Integer rol) {
        this.rol = rol;
    }

    public Cerca withRol(Integer rol) {
        this.rol = rol;
        return this;
    }

    public String getAliasCerca() {
        return aliasCerca;
    }

    public void setAliasCerca(String aliasCerca) {
        this.aliasCerca = aliasCerca;
    }

    public Cerca withAliasCerca(String aliasCerca) {
        this.aliasCerca = aliasCerca;
        return this;
    }

    public String getCercaId() {
        return cercaId;
    }

    public void setCercaId(String cercaId) {
        this.cercaId = cercaId;
    }

    public Cerca withCercaId(String cercaId) {
        this.cercaId = cercaId;
        return this;
    }

    public String getMAC() {
        return mAC;
    }

    public void setMAC(String mAC) {
        this.mAC = mAC;
    }

    public Cerca withMAC(String mAC) {
        this.mAC = mAC;
        return this;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Cerca withModelo(String modelo) {
        this.modelo = modelo;
        return this;
    }

    public Integer getEstadoBateria() {
        return estadoBateria;
    }

    public void setEstadoBateria(Integer estadoBateria) {
        this.estadoBateria = estadoBateria;
    }

    public Cerca withEstadoBateria(Integer estadoBateria) {
        this.estadoBateria = estadoBateria;
        return this;
    }

    public Boolean getEstadoConexionCorriente() {
        return estadoConexionCorriente;
    }

    public void setEstadoConexionCorriente(Boolean estadoConexionCorriente) {
        this.estadoConexionCorriente = estadoConexionCorriente;
    }

    public Cerca withEstadoConexionCorriente(Boolean estadoConexionCorriente) {
        this.estadoConexionCorriente = estadoConexionCorriente;
        return this;
    }

    public Boolean getEstadoConexionAlSistema() {
        return estadoConexionAlSistema;
    }

    public void setEstadoConexionAlSistema(Boolean estadoConexionAlSistema) {
        this.estadoConexionAlSistema = estadoConexionAlSistema;
    }

    public Cerca withEstadoConexionAlSistema(Boolean estadoConexionAlSistema) {
        this.estadoConexionAlSistema = estadoConexionAlSistema;
        return this;
    }

    public Boolean getEstadoAlarma() {
        return estadoAlarma;
    }

    public void setEstadoAlarma(Boolean estadoAlarma) {
        this.estadoAlarma = estadoAlarma;
    }

    public Cerca withEstadoAlarma(Boolean estadoAlarma) {
        this.estadoAlarma = estadoAlarma;
        return this;
    }

    public String getIP() {
        return iP;
    }

    public void setIP(String iP) {
        this.iP = iP;
    }

    public Cerca withIP(String iP) {
        this.iP = iP;
        return this;
    }

    public String getPuertaEnlace() {
        return puertaEnlace;
    }

    public void setPuertaEnlace(String puertaEnlace) {
        this.puertaEnlace = puertaEnlace;
    }

    public Cerca withPuertaEnlace(String puertaEnlace) {
        this.puertaEnlace = puertaEnlace;
        return this;
    }

    public String getMascaraSubred() {
        return mascaraSubred;
    }

    public void setMascaraSubred(String mascaraSubred) {
        this.mascaraSubred = mascaraSubred;
    }

    public Cerca withMascaraSubred(String mascaraSubred) {
        this.mascaraSubred = mascaraSubred;
        return this;
    }

    public String getDNS1() {
        return dNS1;
    }

    public void setDNS1(String dNS1) {
        this.dNS1 = dNS1;
    }

    public Cerca withDNS1(String dNS1) {
        this.dNS1 = dNS1;
        return this;
    }

    public String getDNS2() {
        return dNS2;
    }

    public void setDNS2(String dNS2) {
        this.dNS2 = dNS2;
    }

    public Cerca withDNS2(String dNS2) {
        this.dNS2 = dNS2;
        return this;
    }

    public String getSSID() {
        return sSID;
    }

    public void setSSID(String sSID) {
        this.sSID = sSID;
    }

    public Cerca withSSID(String sSID) {
        this.sSID = sSID;
        return this;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public Cerca withLatitud(String latitud) {
        this.latitud = latitud;
        return this;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public Cerca withLongitud(String longitud) {
        this.longitud = longitud;
        return this;
    }

}
