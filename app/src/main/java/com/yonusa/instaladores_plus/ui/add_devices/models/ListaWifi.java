package com.yonusa.instaladores_plus.ui.add_devices.models;

public class  ListaWifi {
    private String name;
    private String  potencia;
    private String  mac;
    private int thumbnail;

    public ListaWifi(String name, String potencia, String mac, int thumbnail) {
        this.name = name;
        this.potencia = potencia;
        this.mac = mac;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPotencia() {
        return potencia;
    }

    public void setPotencia(String potencia) {
        this.potencia = potencia;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
