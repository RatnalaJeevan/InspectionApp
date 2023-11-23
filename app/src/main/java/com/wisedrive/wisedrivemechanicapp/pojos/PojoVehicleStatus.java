package com.wisedrive.wisedrivemechanicapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoVehicleStatus {

    @SerializedName("inspection_status_id")
    String inspection_status_id;
    @SerializedName("vehicle_id")
    String vehicle_id;
    @SerializedName("vehicle_make")
    String vehicle_make;
    @SerializedName("vehicle_model")
    String vehicle_model;
    @SerializedName("brand_icon")
    String brand_icon;
    @SerializedName("vehicle_no")
    String vehicle_no;
    @SerializedName("fuel_type")
    String fuel_type;
    @SerializedName("transmission_type")
    String transmission_type;
    @SerializedName("manufacturing_year")
    String manufacturing_year;
    @SerializedName("inspection_status")
    String inspection_status;

    public String getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(String vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public String getInspection_status_id() {
        return inspection_status_id;
    }

    public void setInspection_status_id(String inspection_status_id) {
        this.inspection_status_id = inspection_status_id;
    }

    public String getVehicle_make() {
        return vehicle_make;
    }

    public void setVehicle_make(String vehicle_make) {
        this.vehicle_make = vehicle_make;
    }

    public String getVehicle_model() {
        return vehicle_model;
    }

    public void setVehicle_model(String vehicle_model) {
        this.vehicle_model = vehicle_model;
    }

    public String getBrand_icon() {
        return brand_icon;
    }

    public void setBrand_icon(String brand_icon) {
        this.brand_icon = brand_icon;
    }

    public String getVehicle_no() {
        return vehicle_no;
    }

    public void setVehicle_no(String vehicle_no) {
        this.vehicle_no = vehicle_no;
    }

    public String getInspection_status() {
        return inspection_status;
    }

    public void setInspection_status(String inspection_status) {
        this.inspection_status = inspection_status;
    }

    public String getFuel_type() {
        return fuel_type;
    }

    public void setFuel_type(String fuel_type) {
        this.fuel_type = fuel_type;
    }

    public String getTransmission_type() {
        return transmission_type;
    }

    public void setTransmission_type(String transmission_type) {
        this.transmission_type = transmission_type;
    }

    public String getManufacturing_year() {
        return manufacturing_year;
    }

    public void setManufacturing_year(String manufacturing_year) {
        this.manufacturing_year = manufacturing_year;
    }
}
