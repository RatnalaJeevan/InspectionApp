package com.wisedrive.wisedrivemechanicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wisedrive.wisedrivemechanicapp.commonclasses.Connectivity;
import com.wisedrive.wisedrivemechanicapp.commonclasses.SPHelper;
import com.wisedrive.wisedrivemechanicapp.responses.AppResponse;
import com.wisedrive.wisedrivemechanicapp.services.ApiClient;
import com.wisedrive.wisedrivemechanicapp.services.MechanicApis;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPage extends AppCompatActivity {
    TextView tv_login,tv_sendotp,tv_otp,tv_resend;
    EditText selected_mobileno,otp1,otp2,otp3,otp4;
    String otps;
    Integer textlength1,textlength2,textlength3;
    LinearLayout otp_ll;
    private MechanicApis apiInterface;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        apiInterface = ApiClient.getClient().create(MechanicApis.class);
        progressDialog = new ProgressDialog(LoginPage.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        otp1=(EditText)findViewById(R.id.otp1);
        otp2=(EditText)findViewById(R.id.otp2);
        otp3=(EditText)findViewById(R.id.otp3);
        otp4=(EditText)findViewById(R.id.otp4);
        otp_ll=(LinearLayout)findViewById(R.id.otp_ll);
        selected_mobileno=(EditText)findViewById(R.id.selected_mobileno);
        tv_otp=(TextView)findViewById(R.id.tv_otp);
        tv_login=(TextView)findViewById(R.id.tv_login);
        tv_sendotp=(TextView)findViewById(R.id.tv_sendotp);
        tv_resend=(TextView)findViewById(R.id.tv_resend);
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selected_mobileno.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),
                            "Please Enter Your PhoneNo",
                            Toast.LENGTH_SHORT).show();
                }else if(selected_mobileno.getText().toString().length()<10){
                    Toast.makeText(getApplicationContext(),
                            "Please Enter Valid Phone Number",
                            Toast.LENGTH_SHORT).show();
                }else{
                    verify_otp();
                   /* Intent intent=new Intent(LoginPage.this,HomePage.class);
                    startActivity(intent);*/
                }
            }
        });
        tv_sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //tv_sendotp.setVisibility(View.GONE);
                if(selected_mobileno.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),
                            "Please Enter your Phone Number",
                            Toast.LENGTH_SHORT).show();
                }else if(selected_mobileno.getText().toString().length()<10){
                    Toast.makeText(getApplicationContext(),
                            "Please Enter Valid Phone Number",
                            Toast.LENGTH_SHORT).show();
                }else{
                    send_otp();
                    /*tv_sendotp.setVisibility(View.GONE);
                    otp_ll.setVisibility(View.VISIBLE);
                    tv_otp.setVisibility(View.VISIBLE);
                    tv_resend.setVisibility(View.VISIBLE);*/
                }

            }
        });
        tv_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otp_ll.setVisibility(View.VISIBLE);
                tv_otp.setVisibility(View.VISIBLE);
                tv_resend.setVisibility(View.VISIBLE);
                //tv_sendotp.setVisibility(View.GONE);
                if(selected_mobileno.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),
                            "Please Enter your Phone Number",
                            Toast.LENGTH_SHORT).show();
                }else if(selected_mobileno.getText().toString().length()<10){
                    Toast.makeText(getApplicationContext(),
                            "Please Enter Valid Phone Number",
                            Toast.LENGTH_SHORT).show();
                }else{
                    otp1.setText("");
                    otp2.setText("");
                    otp3.setText("");
                    otp4.setText("");
                    otp1.requestFocus();
                    send_otp();
                }

            }
        });
        selected_mobileno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(selected_mobileno.getText().toString().length()<10){
                    tv_sendotp.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(selected_mobileno.getText().toString().length()<10){
                    tv_sendotp.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(selected_mobileno.getText().toString().length()<10){
                    tv_sendotp.setVisibility(View.VISIBLE);
                }
            }
        });
        otp1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                textlength1 = otp1.getText().length();
                if (textlength1 >= 1) {
                    otp2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
              }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }
        });
        otp2.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                textlength2 = otp2.getText().length();

                if (textlength2 >= 1) {
                    otp3.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
               }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {


            }
        });
        otp3.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                textlength3 = otp3.getText().length();

                if (textlength3 >= 1) {
                    otp4.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                 }
        });
        otp4.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                Integer textlength4 = otp3.getText().length();

                if (textlength4 >= 1) {
                    tv_login.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
             }
        });
    }

    public void send_otp()
    {
        if(!Connectivity.isNetworkConnected(LoginPage.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Plaese Check Your Internet",
                    Toast.LENGTH_LONG).show();
        }else
        {
            tv_sendotp.setVisibility(View.GONE);
            progressDialog.show();
            Call<AppResponse> call =  apiInterface.sendotp(selected_mobileno.getText().toString().trim());
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response)
                {
                    AppResponse appResponse = response.body();
                    assert appResponse != null;
                    String response_code = appResponse.getResponseType();
                    if (response.body()!=null)
                    {
                        if (response_code.equals("200")) {
                            progressDialog.dismiss();
                            Toast.makeText(LoginPage.this,"Otp has sent to your mobile number", Toast.LENGTH_SHORT).show();
                            otp_ll.setVisibility(View.VISIBLE);
                            tv_otp.setVisibility(View.VISIBLE);
                            tv_resend.setVisibility(View.VISIBLE);
                            SPHelper.saveSPdata(LoginPage.this, SPHelper.employeeid, appResponse.getResponse().getLoginDetails().getEmployee_id());
                        } else if (response_code.equals("300")) {
                            progressDialog.dismiss();
                            Toast.makeText(LoginPage.this, appResponse.getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                            selected_mobileno.setText("");
                        }
                    } else{
                        progressDialog.dismiss();
                        Toast.makeText(LoginPage.this, "internal server error" , Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(getApplicationContext(),
                            t.getMessage(),
                            Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            });
        }
    }

    public  void verify_otp(){
        {
            otps=otp1.getText().toString()+otp2.getText().toString()+otp3.getText().toString()+otp4.getText().toString();
            if(!Connectivity.isNetworkConnected(LoginPage.this))
            {
                Toast.makeText(getApplicationContext(),
                        "Please Check Your Internet",
                        Toast.LENGTH_LONG).show();
            }else
            {
                progressDialog.show();
                Call<AppResponse> call =  apiInterface.verifyotp(selected_mobileno.getText().toString().trim(),otps);
                call.enqueue(new Callback<AppResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response)
                    {
                        AppResponse appResponse = response.body();
                        assert appResponse != null;
                        String response_code = appResponse.getResponseType();
                        if (response.body()!=null)
                        {
                            if (response_code.equals("200")) {
                                progressDialog.dismiss();
                                SPHelper.saveSPdata(LoginPage.this, SPHelper.awssecret, appResponse.getResponse().getCredentials().getAws_secret());
                                SPHelper.saveSPdata(LoginPage.this, SPHelper.awskey, appResponse.getResponse().getCredentials().getAws_key());
                                Intent intent=new Intent(LoginPage.this,HomePage.class);
                                startActivity(intent);
                                finish();
                            } else if (response_code.equals("300")) {
                                progressDialog.dismiss();
                                Toast.makeText(LoginPage.this, appResponse.getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                                otp1.setText("");
                                otp2.setText("");
                                otp3.setText("");
                                otp4.setText("");
                                tv_login.setVisibility(View.GONE);
                                tv_sendotp.setVisibility(View.VISIBLE);
                            }
                        } else{
                            progressDialog.dismiss();
                            Toast.makeText(LoginPage.this, "internal server error" , Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                        Toast.makeText(getApplicationContext(),
                                t.getMessage(),
                                Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                });
            }
        }
    }
}