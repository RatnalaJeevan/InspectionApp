package com.wisedrive.wisedrivemechanicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.wisedrive.wisedrivemechanicapp.adapters.AdapterCarBrand;
import com.wisedrive.wisedrivemechanicapp.commonclasses.Connectivity;
import com.wisedrive.wisedrivemechanicapp.pojos.PojoCarBrands;
import com.wisedrive.wisedrivemechanicapp.pojos.PojoCarModels;
import com.wisedrive.wisedrivemechanicapp.responses.AppResponse;
import com.wisedrive.wisedrivemechanicapp.services.ApiClient;
import com.wisedrive.wisedrivemechanicapp.services.MechanicApis;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectCarBrand extends AppCompatActivity {
    ImageView go_back_home,cancel;
    RecyclerView rv_car_brandlist;
    AdapterCarBrand adapterCarBrand;
    ArrayList<PojoCarBrands> carBrands;
    private MechanicApis apiInterface;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_car_brand);
        apiInterface = ApiClient.getClient().create(MechanicApis.class);
        progressDialog = new ProgressDialog(SelectCarBrand.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        rv_car_brandlist=(RecyclerView)findViewById(R.id.rv_car_brandlist);
        get_carbrands_list();

    }

    public  void get_carbrands_list() {
        {
            if (!Connectivity.isNetworkConnected(SelectCarBrand.this)) {
                Toast.makeText(getApplicationContext(),
                        "Plaese Check Your Internet",
                        Toast.LENGTH_LONG).show();
            } else {
                progressDialog.show();
                Call<AppResponse> call = apiInterface.get_brandlist();
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
                                carBrands=new ArrayList<>();
                                carBrands=appResponse.getResponse().getBrandList();
                                adapterCarBrand = new AdapterCarBrand(carBrands, getApplicationContext());
                                GridLayoutManager layoutManager = new GridLayoutManager(SelectCarBrand.this,3);
                                rv_car_brandlist.setLayoutManager(layoutManager);
                                rv_car_brandlist.setAdapter(adapterCarBrand);
                                SelectCarBrand.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapterCarBrand.notifyDataSetChanged();
                                    }
                                });
                            } else if (response_code.equals("300")) {
                                progressDialog.dismiss();
                            }
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(SelectCarBrand.this, "internal server error", Toast.LENGTH_SHORT).show();
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