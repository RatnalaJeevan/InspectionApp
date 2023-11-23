package com.wisedrive.wisedrivemechanicapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoModuleList {

    @SerializedName("module_id")
    String module_id;
    @SerializedName("audio")
    String audio;
    @SerializedName("module_name")
    String module_name;

    public String getModule_id() {
        return module_id;
    }

    public void setModule_id(String module_id) {
        this.module_id = module_id;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getModule_name() {
        return module_name;
    }

    public void setModule_name(String module_name) {
        this.module_name = module_name;
    }
}
