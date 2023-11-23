package com.wisedrive.wisedrivemechanicapp.pojos;

public class PojoAddCar {
    String dealerId;
    String modelId;
    String fuelType;
    String transmissionType;
    String year;
    String vehicleNo;
    String sellingPrice;
    String km;
    String noOfOwner;
    String color;
    String isPublic;
    String status;
    String bankName;
    String insuranceType;
    String expDate;
    String addedBy;

    public PojoAddCar(String dealerId, String modelId, String fuelType, String transmissionType, String year,
                      String vehicleNo, String sellingPrice, String km, String noOfOwner, String color,
                      String isPublic, String status, String bankName, String insuranceType, String expDate, String addedBy) {
        this.dealerId = dealerId;
        this.modelId = modelId;
        this.fuelType = fuelType;
        this.transmissionType = transmissionType;
        this.year = year;
        this.vehicleNo = vehicleNo;
        this.sellingPrice = sellingPrice;
        this.km = km;
        this.noOfOwner = noOfOwner;
        this.color = color;
        this.isPublic = isPublic;
        this.status = status;
        this.bankName = bankName;
        this.insuranceType = insuranceType;
        this.expDate = expDate;
        this.addedBy = addedBy;
    }
}
