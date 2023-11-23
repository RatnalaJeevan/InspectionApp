package com.wisedrive.wisedrivemechanicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wisedrive.wisedrivemechanicapp.commonclasses.Connectivity;
import com.wisedrive.wisedrivemechanicapp.commonclasses.SPHelper;
import com.wisedrive.wisedrivemechanicapp.pojos.PojoAddCar;
import com.wisedrive.wisedrivemechanicapp.responses.AppResponse;
import com.wisedrive.wisedrivemechanicapp.services.ApiClient;
import com.wisedrive.wisedrivemechanicapp.services.MechanicApis;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectCarFuelMnf extends AppCompatActivity
{
    TextView tv_add_cardetails;
    RelativeLayout rl_petrol,rl_diesel,rl_auto,rl_manual;
    private MechanicApis apiInterface;
    private ProgressDialog progressDialog;
    EditText selected_manufacture_year,selected_kms;
    public  int cy=0,selected_year=0,fromyear=0,toyear=0;
    String currentyear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_car_fuel_mnf);
        Calendar cal = Calendar.getInstance();
        cy=cal.get(Calendar.YEAR);
        currentyear=String.valueOf(cy);
        tv_add_cardetails=(TextView)findViewById(R.id.tv_next);
        rl_diesel=(RelativeLayout)findViewById(R.id.rl_diesel);
        rl_petrol=(RelativeLayout)findViewById(R.id.rl_petrol);
        rl_auto=(RelativeLayout)findViewById(R.id.rl_auto);
        selected_kms=(EditText)findViewById(R.id.selected_kms);
        selected_manufacture_year=(EditText)findViewById(R.id.selected_manufacture_year);
        rl_manual=(RelativeLayout)findViewById(R.id.rl_manual);
        apiInterface = ApiClient.getClient().create(MechanicApis.class);
        progressDialog = new ProgressDialog(SelectCarFuelMnf.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        tv_add_cardetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if(selected_manufacture_year.getText().toString().equals("")){

                }else{
                    selected_year=Integer.parseInt(selected_manufacture_year.getText().toString().trim());
                }
                if(SPHelper.fueltype.equals(""))
                {
                    Toast.makeText(getApplicationContext(),
                            "Please Enter Fuel type",
                            Toast.LENGTH_SHORT).show();
                }
                else if(SPHelper.transmissiontype.equals("")){
                    Toast.makeText(getApplicationContext(),
                            "Please Enter Transmission type",
                            Toast.LENGTH_SHORT).show();
                }else if(selected_manufacture_year.getText().toString().trim().equals(""))
                {
                    Toast.makeText(getApplicationContext(),
                            "Please Enter Manufacturing year",
                            Toast.LENGTH_SHORT).show();
                }
                else if(selected_kms.getText().toString().trim().equals(""))
                {
                    Toast.makeText(getApplicationContext(),
                            "Please Enter kms driven",
                            Toast.LENGTH_SHORT).show();
                }
                else if(selected_year>cy||selected_year<1995)
                {
                    Toast.makeText(getApplicationContext(),
                            "Please Enter valid Manufacturing year",
                            Toast.LENGTH_SHORT).show();}
                else{
                    post_addcar();
                }

            }
        });
    }

    public void onpetrolselect(View view)
    {
        SPHelper.fueltype="Petrol";
        rl_petrol.setBackground(getDrawable(R.drawable.cardview_dealership));
        rl_petrol.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.lightorange));
        // tv_dealership.setTextColor(Color.parseColor("#FFFFFF"));
        // rl_diesel.setBackground(getDrawable(R.drawable.cardview_marketplace));
        rl_diesel.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.white));
    }

    public void ondieselselect(View view) {
        SPHelper.fueltype="Diesel";
        rl_diesel.setBackground(getDrawable(R.drawable.cardview_dealership));
        rl_diesel.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.lightorange));
        // tv_dealership.setTextColor(Color.parseColor("#FFFFFF"));
        rl_petrol.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.white));
    }

    public void onmanualselect(View view) {
        SPHelper.transmissiontype="Manual";
        rl_manual.setBackground(getDrawable(R.drawable.cardview_dealership));
        rl_manual.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.lightorange));
        // tv_dealership.setTextColor(Color.parseColor("#FFFFFF"));
        // rl_diesel.setBackground(getDrawable(R.drawable.cardview_marketplace));
        rl_auto.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.white));
    }

    public void onautoselect(View view) {
        SPHelper.transmissiontype="Automatic";
        rl_auto.setBackground(getDrawable(R.drawable.cardview_dealership));
        rl_auto.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.lightorange));
        // tv_dealership.setTextColor(Color.parseColor("#FFFFFF"));
        // rl_diesel.setBackground(getDrawable(R.drawable.cardview_marketplace));
        rl_manual.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.white));
    }

    public  void post_addcar(){
        {
            SPHelper.kmsdriven=selected_kms.getText().toString().trim();
            SPHelper.manufacture_year=selected_manufacture_year.getText().toString().trim();
            if(!Connectivity.isNetworkConnected(SelectCarFuelMnf.this))
            {
                Toast.makeText(getApplicationContext(),
                        "Please Check Your Internet",
                        Toast.LENGTH_LONG).show();
            }else
            {
                progressDialog.show();
                PojoAddCar pojoAddCar=new PojoAddCar(SPHelper.getSPData(SelectCarFuelMnf.this, SPHelper.dealerid, "")
                        ,SPHelper.carbrandid,SPHelper.fueltype,SPHelper.transmissiontype,selected_manufacture_year.getText().toString().trim(),
                        SPHelper.vehno,"",selected_kms.getText().toString().trim(),"","","","",
                        "","","",SPHelper.getSPData(SelectCarFuelMnf.this, SPHelper.employeeid, ""));
                Call<AppResponse> call =  apiInterface.add_car(pojoAddCar);
                call.enqueue(new Callback<AppResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response)
                    {
                        AppResponse appResponse = response.body();
                        assert appResponse != null;
                        String response_code = appResponse.getResponseType();
                        if (response.body()!=null)
                        {
                            if (response_code.equals("200"))
                            {
                                progressDialog.dismiss();
                                Intent intent=new Intent(getApplicationContext(), SubmitVehInspection.class);
                                startActivity(intent);

                            } else if (response_code.equals("300"))
                            {
                                progressDialog.dismiss();
                                Toast.makeText(SelectCarFuelMnf.this, appResponse.getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else{
                            progressDialog.dismiss();
                            Toast.makeText(SelectCarFuelMnf.this, "internal server error" , Toast.LENGTH_SHORT).show();
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