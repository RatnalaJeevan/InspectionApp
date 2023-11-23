package com.wisedrive.wisedrivemechanicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.wisedrive.wisedrivemechanicapp.adapters.AdapterCarModel;
import com.wisedrive.wisedrivemechanicapp.commonclasses.Connectivity;
import com.wisedrive.wisedrivemechanicapp.commonclasses.SPHelper;
import com.wisedrive.wisedrivemechanicapp.pojos.PojoCarModels;
import com.wisedrive.wisedrivemechanicapp.responses.AppResponse;
import com.wisedrive.wisedrivemechanicapp.services.ApiClient;
import com.wisedrive.wisedrivemechanicapp.services.MechanicApis;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectCarModel extends AppCompatActivity {
    ImageView go_back_home,cancel;
    RecyclerView rv_car_modellist;
    AdapterCarModel adapterCarModelLists;
    ArrayList<PojoCarModels> carModels;
    private MechanicApis apiInterface;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_car_model);
        apiInterface = ApiClient.getClient().create(MechanicApis.class);
        progressDialog = new ProgressDialog(SelectCarModel.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        rv_car_modellist=(RecyclerView)findViewById(R.id.rv_car_modellist);
        get_carmodel_list();
    }

    public  void get_carmodel_list()
    {
        {
            if (!Connectivity.isNetworkConnected(SelectCarModel.this)) {
                Toast.makeText(getApplicationContext(),
                        "Plaese Check Your Internet",
                        Toast.LENGTH_LONG).show();
            } else {
                progressDialog.show();
                Call<AppResponse> call = apiInterface.get_modelList(SPHelper.carbrandid);
                call.enqueue(new Callback<AppResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                        AppResponse appResponse = response.body();
                        assert appResponse != null;
                        String response_code = appResponse.getResponseType();
                        if (response.body() != null) {
                            if (response_code.equals("200"))
                            {
                                progressDialog.dismiss();
                                carModels=new ArrayList<>();
                                carModels=appResponse.getResponse().getModelList();
                                adapterCarModelLists = new AdapterCarModel(carModels, getApplicationContext());
                                GridLayoutManager layoutManager = new GridLayoutManager(SelectCarModel.this,3);
                                rv_car_modellist.setLayoutManager(layoutManager);
                                rv_car_modellist.setAdapter(adapterCarModelLists);
                                SelectCarModel.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapterCarModelLists.notifyDataSetChanged();
                                    }
                                });
                            } else if (response_code.equals("300")) {
                                progressDialog.dismiss();
                            }
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(SelectCarModel.this, "internal server error", Toast.LENGTH_SHORT).show();
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