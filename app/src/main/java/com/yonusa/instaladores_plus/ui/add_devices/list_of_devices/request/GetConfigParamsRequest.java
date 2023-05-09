
package com.yonusa.instaladores_plus.ui.add_devices.list_of_devices.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetConfigParamsRequest {

    @SerializedName("MAC")
    @Expose
    private String mAC;
    @SerializedName("modeloId")
    @Expose
    private String modeloId;

    public String getMAC() {
        return mAC;
    }

    public void setMAC(String mAC) {
        this.mAC = mAC;
    }

    public String getModeloId() {
        return modeloId;
    }

    public void setModeloId(String modeloId) {
        this.modeloId = modeloId;
    }

}
