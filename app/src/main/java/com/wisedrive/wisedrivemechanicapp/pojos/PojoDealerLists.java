package com.wisedrive.wisedrivemechanicapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoDealerLists {

    @SerializedName("dealer_name")
    String dealer_name;
    @SerializedName("dealer_logo")
    String dealer_logo;
    @SerializedName("dealer_id")
    String dealer_id;
    @SerializedName("phone_no")
    String phone_no;

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getDealer_name() {
        return dealer_name;
    }

    public void setDealer_name(String dealer_name) {
        this.dealer_name = dealer_name;
    }

    public String getDealer_logo() {
        return dealer_logo;
    }

    public void setDealer_logo(String dealer_logo) {
        this.dealer_logo = dealer_logo;
    }

    public String getDealer_id() {
        return dealer_id;
    }

    public void setDealer_id(String dealer_id) {
        this.dealer_id = dealer_id;
    }
}
