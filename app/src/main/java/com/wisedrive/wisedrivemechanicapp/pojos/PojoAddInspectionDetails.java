package com.wisedrive.wisedrivemechanicapp.pojos;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;

import java.util.ArrayList;

public class PojoAddInspectionDetails {
    @SerializedName("dealerId")
    String dealerId;
    @SerializedName("vehicleId")
    String vehicleId;
    @SerializedName("addedBy")
    String addedBy;
    @SerializedName("inspectionArr")
    JSONArray inspectionArr;

    public PojoAddInspectionDetails(String dealerId, String vehicleId, String addedBy, JSONArray inspectionArr) {
        this.dealerId = dealerId;
        this.vehicleId = vehicleId;
        this.addedBy = addedBy;
        this.inspectionArr = inspectionArr;
    }
}
