
package com.yonusa.instaladores_plus.ui.view.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Controle {

    @SerializedName("controlId")
    @Expose
    private Integer controlId;
    @SerializedName("aliasControl")
    @Expose
    private String aliasControl;
    @SerializedName("estadoControl")
    @Expose
    private Boolean estadoControl;
    @SerializedName("estadoPermiso")
    @Expose
    private Boolean estadoPermiso;

    public Integer getControlId() {
        return controlId;
    }

    public void setControlId(Integer controlId) {
        this.controlId = controlId;
    }

    public String getAliasControl() {
        return aliasControl;
    }

    public void setAliasControl(String aliasControl) {
        this.aliasControl = aliasControl;
    }

    public Boolean getEstadoControl() {
        return estadoControl;
    }

    public void setEstadoControl(Boolean estadoControl) {
        this.estadoControl = estadoControl;
    }

    public Boolean getEstadoPermiso() {
        return estadoPermiso;
    }

    public void setEstadoPermiso(Boolean estadoPermiso) {
        this.estadoPermiso = estadoPermiso;
    }

}
