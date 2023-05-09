package com.yonusa.instaladores_plus.ui.login.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LogIn_response {

    @SerializedName("usuarioId")
    @Expose
    private String usuarioId;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("apellido")
    @Expose
    private String apellido;
    @SerializedName("telefono")
    @Expose
    private String telefono;
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

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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
