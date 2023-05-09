package com.yonusa.instaladores_plus.ui.cercas.modelo;


/**
 * Created by ExpoCode Tech http://expocodetech.com
 */

public class cercas_model {


    private String UsuarioId;
    private String CercaId;
    private String RolId;
    private String AliasCerca;
    private String AliasUsuario;
    private String nombre;

    public String getUsuarioId() {
        return UsuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        UsuarioId = usuarioId;
    }

    public String getCercaId() {
        return CercaId;
    }

    public void setCercaId(String cercaId) {
        CercaId = cercaId;
    }

    public String getRolId() {
        return RolId;
    }

    public void setRolId(String rolId) {
        RolId = rolId;
    }

    public String getAliasCerca() {
        return AliasCerca;
    }

    public void setAliasCerca(String aliasCerca) {
        AliasCerca = aliasCerca;
    }

    public String getAliasUsuario() {
        return AliasUsuario;
    }

    public void setAliasUsuario(String aliasUsuario) {
        AliasUsuario = aliasUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    private String Email;


}
