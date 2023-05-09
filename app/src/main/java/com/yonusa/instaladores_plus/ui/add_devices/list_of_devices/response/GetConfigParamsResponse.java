
package com.yonusa.instaladores_plus.ui.add_devices.list_of_devices.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetConfigParamsResponse {

    @SerializedName("UUID")
    @Expose
    private String uUID;
    @SerializedName("MQTT_SERVER")
    @Expose
    private String mQTTSERVER;
    @SerializedName("MQTT_PORT")
    @Expose
    private String mQTTPORT;
    @SerializedName("MQTT_USER")
    @Expose
    private String mQTTUSER;
    @SerializedName("MQTT_PWD")
    @Expose
    private String mQTTPWD;
    @SerializedName("codigo")
    @Expose
    private Integer codigo;
    @SerializedName("codigoEspecifico")
    @Expose
    private Integer codigoEspecifico;
    @SerializedName("mensaje")
    @Expose
    private String mensaje;

    public String getUUID() {
        return uUID;
    }

    public void setUUID(String uUID) {
        this.uUID = uUID;
    }

    public String getMQTTSERVER() {
        return mQTTSERVER;
    }

    public void setMQTTSERVER(String mQTTSERVER) {
        this.mQTTSERVER = mQTTSERVER;
    }

    public String getMQTTPORT() {
        return mQTTPORT;
    }

    public void setMQTTPORT(String mQTTPORT) {
        this.mQTTPORT = mQTTPORT;
    }

    public String getMQTTUSER() {
        return mQTTUSER;
    }

    public void setMQTTUSER(String mQTTUSER) {
        this.mQTTUSER = mQTTUSER;
    }

    public String getMQTTPWD() {
        return mQTTPWD;
    }

    public void setMQTTPWD(String mQTTPWD) {
        this.mQTTPWD = mQTTPWD;
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
