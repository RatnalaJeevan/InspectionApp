package com.wisedrive.wisedrivemechanicapp.commonclasses;

import android.content.Context;
import android.content.SharedPreferences;
public class SPHelper {
    public static SharedPreferences prefs;

    public static String employeeid="employeeid";
    public static String dealername="dealername";
    public static String dealerno="dealerno";
    public static String carbrandid="";
    public static String carmodelid="";
    public static String manufacture_year="";
    public static String transmissiontype="";
    public static String fueltype="";
    public static String obdreportadded="";
    public static String carimagepositionid="";
    public static String imagestaken="imagestaken";
    public static String model_name="";
    public static String brandlogo="";
    public static String vehid="";
    public static String selected_brandid="";
    public static String kmsdriven="";
    public static String dealerselected="";
    public static String vehno="";
    public static String veh_color="";
    public static String no_owners="";
    public static String dealerlogo="dealerlogo";
    public static String selling_price="";
    public static String helplineno="helplineno";
    public static String awssecret="awssecret";
    public static String awskey="awskey";
    public static String comet_authkey="comet_authkey";
    public static String comet_region="comet_region";
    public static String comet_appid="comet_appid";
    public static String dealerid="dealerid";
    public static String languageid="languageid";
    public static String languagename="languagename";
    public static String moduleid="";
    public static String audiourl="";
    public static Object camefrom="";
    public static String ismulti="";

    private  static String spName="DealerApp1";

    public static void sharedPreferenceInitialization(Context ctx) {
        prefs = ctx.getSharedPreferences(spName,Context.MODE_PRIVATE);
    }

    public static void saveSPdata(Context ctx, String key, String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }


    public static String getSPData(Context ctx, String key, String defaultValue) {
        prefs = ctx.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return prefs.getString(key, defaultValue);
    }
}
