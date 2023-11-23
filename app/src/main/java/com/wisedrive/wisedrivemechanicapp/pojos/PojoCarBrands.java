package com.wisedrive.wisedrivemechanicapp.pojos;

import android.widget.ImageView;

import com.google.gson.annotations.SerializedName;

public class PojoCarBrands {

    @SerializedName("brand_icon")
    String brand_icon;
    @SerializedName("id")
    String id;
    @SerializedName("car_brand")
    String car_brand;

    public String getBrand_icon() {
        return brand_icon;
    }

    public void setBrand_icon(String brand_icon) {
        this.brand_icon = brand_icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCar_brand() {
        return car_brand;
    }

    public void setCar_brand(String car_brand) {
        this.car_brand = car_brand;
    }


}
