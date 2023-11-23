package com.wisedrive.wisedrivemechanicapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoCredentials {
    @SerializedName("aws_secret")
    String aws_secret;
    @SerializedName("aws_key")
    String aws_key;
    @SerializedName("comet_auth_key")
    String comet_auth_key;
    @SerializedName("comet_region")
    String comet_region;
    @SerializedName("comet_app_id")
    String comet_app_id;

    public String getAws_secret() {
        return aws_secret;
    }

    public void setAws_secret(String aws_secret) {
        this.aws_secret = aws_secret;
    }

    public String getAws_key() {
        return aws_key;
    }

    public void setAws_key(String aws_key) {
        this.aws_key = aws_key;
    }

    public String getComet_auth_key() {
        return comet_auth_key;
    }

    public void setComet_auth_key(String comet_auth_key) {
        this.comet_auth_key = comet_auth_key;
    }

    public String getComet_region() {
        return comet_region;
    }

    public void setComet_region(String comet_region) {
        this.comet_region = comet_region;
    }

    public String getComet_app_id() {
        return comet_app_id;
    }

    public void setComet_app_id(String comet_app_id) {
        this.comet_app_id = comet_app_id;
    }
}
