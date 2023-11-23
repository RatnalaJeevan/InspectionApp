package com.wisedrive.wisedrivemechanicapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoLoginDetails {
    @SerializedName("employee_name")
    String employee_name;
    @SerializedName("employee_id")
    String employee_id;
    @SerializedName("employee_work_location")
    String employee_work_location;
    @SerializedName("error_msg")
    String error_msg;

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getEmployee_work_location() {
        return employee_work_location;
    }

    public void setEmployee_work_location(String employee_work_location) {
        this.employee_work_location = employee_work_location;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }
}
