package com.wisedrive.wisedrivemechanicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.wisedrive.wisedrivemechanicapp.commonclasses.SPHelper;

public class SplashScreen extends AppCompatActivity {
    private static final int SPLASH_DISPLAY_TIME = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        SPHelper.sharedPreferenceInitialization(SplashScreen.this);
        new Handler().postDelayed(new Runnable() {
            public void run()
            {
                if(SPHelper.getSPData(SplashScreen.this, SPHelper.employeeid, "").equals(""))
                {
                    Intent intent=new Intent(SplashScreen.this,LoginPage.class);
                    startActivity(intent);
                   /* intent.setClass(SplashScreen.this,
                            LoginPage.class);
                    SplashScreen.this.startActivity(intent);*/
                    SplashScreen.this.finish();
                }else {
                    Intent intent1=new Intent(SplashScreen.this,
                            HomePage.class);
                    startActivity(intent1);
                    SplashScreen.this.finish();
                }
            }
        }, SPLASH_DISPLAY_TIME);
    }
}