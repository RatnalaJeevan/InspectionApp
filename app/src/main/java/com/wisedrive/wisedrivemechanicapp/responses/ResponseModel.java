package com.wisedrive.wisedrivemechanicapp.responses;

import com.google.gson.annotations.SerializedName;
import com.wisedrive.wisedrivemechanicapp.pojos.PojoCarBrands;
import com.wisedrive.wisedrivemechanicapp.pojos.PojoCarModels;
import com.wisedrive.wisedrivemechanicapp.pojos.PojoCredentials;
import com.wisedrive.wisedrivemechanicapp.pojos.PojoDealerLists;
import com.wisedrive.wisedrivemechanicapp.pojos.PojoEngineImages;
import com.wisedrive.wisedrivemechanicapp.pojos.PojoLanguagelist;
import com.wisedrive.wisedrivemechanicapp.pojos.PojoLoginDetails;
import com.wisedrive.wisedrivemechanicapp.pojos.PojoModuleList;
import com.wisedrive.wisedrivemechanicapp.pojos.PojoQuestionsList;
import com.wisedrive.wisedrivemechanicapp.pojos.PojoVehicleImages;
import com.wisedrive.wisedrivemechanicapp.pojos.PojoVehicleStatus;

import java.util.ArrayList;

public class ResponseModel {

    @SerializedName("message")
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    @SerializedName("LoginDetails")
    PojoLoginDetails LoginDetails;

    public PojoLoginDetails getLoginDetails() {
        return LoginDetails;
    }

    public void setLoginDetails(PojoLoginDetails loginDetails) {
        LoginDetails = loginDetails;
    }

    @SerializedName("credentials")
    PojoCredentials credentials;

    public PojoCredentials getCredentials() {
        return credentials;
    }

    public void setCredentials(PojoCredentials credentials) {
        this.credentials = credentials;
    }

    @SerializedName("dealerList")
    ArrayList<PojoDealerLists> dealerList;

    public ArrayList<PojoDealerLists> getDealerList() {
        return dealerList;
    }

    public void setDealerList(ArrayList<PojoDealerLists> dealerList) {
        this.dealerList = dealerList;
    }

    @SerializedName("LanguageList")
    ArrayList<PojoLanguagelist> LanguageList;

    public ArrayList<PojoLanguagelist> getLanguageList() {
        return LanguageList;
    }

    public void setLanguageList(ArrayList<PojoLanguagelist> languageList) {
        LanguageList = languageList;
    }

    @SerializedName("VehicleDetails")
    PojoVehicleStatus VehicleDetails;

    public PojoVehicleStatus getVehicleEligibility() {
        return VehicleDetails;
    }

    public void setVehicleEligibility(PojoVehicleStatus vehicleEligibility) {
        VehicleDetails = VehicleDetails;
    }

    @SerializedName("brandList")
    ArrayList<PojoCarBrands> brandList;

    public ArrayList<PojoCarBrands> getBrandList() {
        return brandList;
    }

    public void setBrandList(ArrayList<PojoCarBrands> brandList) {
        this.brandList = brandList;
    }

    @SerializedName("modelList")
    ArrayList<PojoCarModels> modelList;

    public ArrayList<PojoCarModels> getModelList() {
        return modelList;
    }

    public void setModelList(ArrayList<PojoCarModels> modelList) {
        this.modelList = modelList;
    }

    @SerializedName("ExteriorImageList")
    ArrayList<PojoVehicleImages> ExteriorImageList;

    public ArrayList<PojoVehicleImages> getExteriorImageList() {
        return ExteriorImageList;
    }
    public void setExteriorImageList(ArrayList<PojoVehicleImages> exteriorImageList) {
        ExteriorImageList = exteriorImageList;
    }

    @SerializedName("moduleDetails")
    ArrayList<PojoEngineImages> moduleDetails;

    public ArrayList<PojoEngineImages> getEmgineimagelist() {
        return moduleDetails;
    }

    public void setEmgineimagelist(ArrayList<PojoEngineImages> emgineimagelist) {
        this.moduleDetails = emgineimagelist;
    }

    @SerializedName("moduleList")
    ArrayList<PojoModuleList> moduleList;

    public ArrayList<PojoModuleList> getModuleList() {
        return moduleList;
    }

    public void setModuleList(ArrayList<PojoModuleList> moduleList) {
        this.moduleList = moduleList;
    }

    @SerializedName("questionList")
    ArrayList<PojoQuestionsList> questionList;

    public ArrayList<PojoQuestionsList> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(ArrayList<PojoQuestionsList> questionList) {
        this.questionList = questionList;
    }
}
