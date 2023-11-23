package com.wisedrive.wisedrivemechanicapp.services;

import com.wisedrive.wisedrivemechanicapp.pojos.PojoAddCar;
import com.wisedrive.wisedrivemechanicapp.pojos.PojoAddInspectionDetails;
import com.wisedrive.wisedrivemechanicapp.pojos.PojoAddOBDReport;
import com.wisedrive.wisedrivemechanicapp.responses.AppResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MechanicApis
{

    @GET("/send/otp")
    Call<AppResponse> sendotp(@Query("mobile_no") String mobile_no);

    @GET("/verify/otp")
    Call<AppResponse> verifyotp(@Query("mobile_no") String mobile_no,@Query("otp") String otp);

    @GET("/Home/getDealerList")
    Call<AppResponse> get_dealerlist(@Query("mecId") String mecId,@Query("search") String search);

    @GET("/Home/getLanguageList")
    Call<AppResponse> get_languagelist();

    @GET("/Home/checkVehicleEligibility")
    Call<AppResponse> chech_eligibility(@Query("dealerId") String dealerId,@Query("vehNo") String vehNo);

    @GET("/mechanicVehicle/getBrandList")
    Call<AppResponse> get_brandlist();

    @GET("/mechanicVehicle/getModelList")
    Call<AppResponse> get_modelList(@Query("brandId") String brandId);

    @POST("/mechanicVehicle/addCar")
    Call<AppResponse> add_car(@Body PojoAddCar pojoAddCar);

    @GET("/mechanicVehicle/getimagelist")
    Call<AppResponse> get_imageList(@Query("languageId") String languageId);

    @GET("/inspection/getPartDetails")
    Call<AppResponse> get_partlist(@Query("moduleId") String moduleId,@Query("langId") String langId);

    @POST("/mechanicVehicle/addexteriorphotos")
    Call<AppResponse> add_exterior_photos(@Body PojoAddCar pojoAddCar);

    @GET("/inspection/getModuleList")
    Call<AppResponse> get_modulelist(@Query("langId") String langId);

    @POST("/inspection/addobddata")
    Call<AppResponse> add_obdreport(@Body PojoAddOBDReport pojoAddOBDReport);

    @GET("/inspection/questionlist")
    Call<AppResponse> get_questions_list(@Query("languageId") String languageId,@Query("moduleId") String moduleId);

    @POST("/inspection/addinspectiondetails")
    Call<AppResponse> add_inspection_details(@Body PojoAddInspectionDetails pojoAddInspectionDetails);



}
