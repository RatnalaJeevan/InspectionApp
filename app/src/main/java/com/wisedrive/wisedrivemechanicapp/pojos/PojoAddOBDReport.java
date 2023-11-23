package com.wisedrive.wisedrivemechanicapp.pojos;

public class PojoAddOBDReport {
    String vehicleId;
    String inspectedBy;
    String obdUrl;
    String obdPdf;

    public PojoAddOBDReport(String vehicleId, String inspectedBy, String obdUrl, String obdPdf) {
        this.vehicleId = vehicleId;
        this.inspectedBy = inspectedBy;
        this.obdUrl = obdUrl;
        this.obdPdf = obdPdf;
    }
}
