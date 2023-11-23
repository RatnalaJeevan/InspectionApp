package com.wisedrive.wisedrivemechanicapp.commonclasses;

import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Common {

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
    public final static String TAG = "Log";
    public final static int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public final static int MY_PERMISSIONS_REQUEST_LOCATION = 100;

    public static void CallToast(Context context, String message, int duration) {
        Toast.makeText(context,message,duration).show();
    }

    public final static String PhoneNumberRegix = "[0-9]{10}";


    public static String getCurrentDateDay() {
        Date current = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String parsed = dateFormat.format(current);
        return parsed;
    }
    public static String getDateFromString(String dateStr) {

        try {

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date got = format.parse(dateStr);
            format = new SimpleDateFormat("dd-MM-yyyy");
            return format.format(got);
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        return "";
    }
}


