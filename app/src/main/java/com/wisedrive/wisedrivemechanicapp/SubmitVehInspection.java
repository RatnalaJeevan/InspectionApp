package com.wisedrive.wisedrivemechanicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wisedrive.wisedrivemechanicapp.adapters.AdapterDealerLists;
import com.wisedrive.wisedrivemechanicapp.adapters.AdapterModuleList;
import com.wisedrive.wisedrivemechanicapp.commonclasses.Connectivity;
import com.wisedrive.wisedrivemechanicapp.commonclasses.SPHelper;
import com.wisedrive.wisedrivemechanicapp.pojos.PojoDealerLists;
import com.wisedrive.wisedrivemechanicapp.pojos.PojoModuleList;
import com.wisedrive.wisedrivemechanicapp.responses.AppResponse;
import com.wisedrive.wisedrivemechanicapp.services.ApiClient;
import com.wisedrive.wisedrivemechanicapp.services.MechanicApis;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmitVehInspection extends AppCompatActivity
{
    TextView tv_entered_expirydate,tv_submit_cardetails,tv_make_model,tv_makeyear,tv_fueltype,text_heading,
            tv_label_transmissiontype,tv_submit_edited_car_details,tv_selected_language;
    RelativeLayout rl_veh_images,rl_eng_trans_images,rl_inspect_videos,rl_qa,rl_obd,rl_trans_images,
            rl_trans_videos,rl_exhaust_videos;
    ImageView iv_cars;
    RecyclerView rv_module_list;
    ArrayList<PojoModuleList> moduleLists;
    AdapterModuleList adapterModuleList;
    private MechanicApis apiInterface;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_veh_inspection);
        apiInterface = ApiClient.getClient().create(MechanicApis.class);
        progressDialog = new ProgressDialog(SubmitVehInspection.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        tv_make_model=(TextView)findViewById(R.id.tv_make_model);
        tv_makeyear=(TextView)findViewById(R.id.tv_makeyear);
        tv_fueltype=(TextView)findViewById(R.id.tv_fueltype);
        tv_label_transmissiontype=(TextView)findViewById(R.id.tv_label_transmissiontype);
        iv_cars=(ImageView)findViewById(R.id.iv_cars);
        tv_selected_language=(TextView)findViewById(R.id.tv_selected_language);
        rv_module_list=(RecyclerView)findViewById(R.id.rv_module_list);
       /* rl_inspect_videos=(RelativeLayout)findViewById(R.id.rl_inspect_videos);
        rl_qa=(RelativeLayout)findViewById(R.id.rl_qa);
        rl_obd=(RelativeLayout)findViewById(R.id.rl_obd);*/
        tv_selected_language.setText(SPHelper.getSPData(SubmitVehInspection.this, SPHelper.languagename, ""));
        tv_selected_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPHelper.camefrom="inspection";
                Intent intent=new Intent(SubmitVehInspection.this,SelectLanguage.class);
                startActivity(intent);
            }
        });
        Glide.with(SubmitVehInspection.this).load(SPHelper.brandlogo).placeholder(R.drawable.icon_noimage).into(iv_cars);
        tv_make_model.setText(SPHelper.model_name);
        tv_fueltype.setText(SPHelper.fueltype);
        tv_label_transmissiontype.setText(SPHelper.transmissiontype);
        tv_makeyear.setText(SPHelper.manufacture_year);
       /* rl_veh_images.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SubmitVehInspection.this,AddVehicleImages.class);
                startActivity(intent);
            }
        });
        rl_eng_trans_images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SubmitVehInspection.this, AddEngImages.class);
                startActivity(intent);
            }
        });
        rl_inspect_videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SubmitVehInspection.this,AddVideos.class);
                startActivity(intent);
            }
        });
        rl_qa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SubmitVehInspection.this,QuestAnswerPage.class);
                startActivity(intent);
            }
        });
        rl_obd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SubmitVehInspection.this,OBDReport.class);
                startActivity(intent);
            }
        });
        rl_trans_images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SubmitVehInspection.this,AddTransmissionImages.class);
                startActivity(intent);
            }
        });

        rl_trans_videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SubmitVehInspection.this,AddTransmissionVideos.class);
                startActivity(intent);
            }
        });

        rl_exhaust_videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SubmitVehInspection.this,AddExhaustVideos.class);
                startActivity(intent);
            }
        });*/

        get_modulelist();
    }
    public void get_modulelist()
    {
        if(!Connectivity.isNetworkConnected(SubmitVehInspection.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Plaese Check Your Internet",
                    Toast.LENGTH_LONG).show();
        }else
        {
            progressDialog.show();
            Call<AppResponse> call =  apiInterface.get_modulelist(SPHelper.getSPData(SubmitVehInspection.this,SPHelper.languageid,""));
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response)
                {
                    AppResponse appResponse = response.body();
                    // assert appResponse != null;
                    String response_code = appResponse.getResponseType();
                    if (response.body()!=null)
                    {
                        progressDialog.dismiss();
                        if (response_code.equals("200"))
                        {
                            progressDialog.dismiss();
                            moduleLists=new ArrayList<>();
                            moduleLists=appResponse.getResponse().getModuleList();

                            adapterModuleList = new AdapterModuleList(moduleLists, SubmitVehInspection.this);
                            LinearLayoutManager layoutManager2 = new LinearLayoutManager(SubmitVehInspection.this, LinearLayoutManager.VERTICAL, false);
                            rv_module_list.setLayoutManager(layoutManager2);
                            rv_module_list.setAdapter(adapterModuleList);
                            SubmitVehInspection.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapterModuleList.notifyDataSetChanged();
                                }
                            });
                        }
                        else if (response_code.equals("300"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(SubmitVehInspection.this, appResponse.getResponse().getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(SubmitVehInspection.this, "internal server error" , Toast.LENGTH_SHORT).show();
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