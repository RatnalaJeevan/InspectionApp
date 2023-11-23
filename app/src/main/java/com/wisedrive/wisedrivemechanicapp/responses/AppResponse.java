package com.wisedrive.wisedrivemechanicapp.responses;

import com.google.gson.annotations.SerializedName;

public class AppResponse {

    @SerializedName("responseType")
    private String responseType;
    @SerializedName("response")
    private ResponseModel response;

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public ResponseModel getResponse() {
        return response;
    }

    public void setResponse(ResponseModel response) {
        this.response = response;
    }
}
