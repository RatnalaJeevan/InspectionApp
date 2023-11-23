package com.wisedrive.wisedrivemechanicapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoCarModels {
    @SerializedName("car_model")
    String car_model;
    @SerializedName("model_id")
    String model_id;

    public String getCar_model() {
        return car_model;
    }

    public void setCar_model(String car_model) {
        this.car_model = car_model;
    }

    public String getModel_id() {
        return model_id;
    }

    public void setModel_id(String model_id) {
        this.model_id = model_id;
    }
}
