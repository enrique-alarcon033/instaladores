
package com.yonusa.instaladores_plus.ui.view.models.request.add_user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PermisoControle {

    @SerializedName("controlId")
    @Expose
    private Integer controlId;
    @SerializedName("estadoPermiso")
    @Expose
    private Boolean estadoPermiso;

    public Integer getControlId() {
        return controlId;
    }

    public void setControlId(Integer controlId) {
        this.controlId = controlId;
    }

    public Boolean getEstadoPermiso() {
        return estadoPermiso;
    }

    public void setEstadoPermiso(Boolean estadoPermiso) {
        this.estadoPermiso = estadoPermiso;
    }

}
