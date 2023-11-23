package com.wisedrive.wisedrivemechanicapp;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Pair;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.wisedrive.wisedrivemechanicapp.commonclasses.BitmapUtility;
import com.wisedrive.wisedrivemechanicapp.commonclasses.Connectivity;
import com.wisedrive.wisedrivemechanicapp.commonclasses.RequestPermissionHandler;
import com.wisedrive.wisedrivemechanicapp.commonclasses.SPHelper;
import com.wisedrive.wisedrivemechanicapp.pojos.PojoAddCar;
import com.wisedrive.wisedrivemechanicapp.pojos.PojoAddOBDReport;
import com.wisedrive.wisedrivemechanicapp.responses.AppResponse;
import com.wisedrive.wisedrivemechanicapp.services.ApiClient;
import com.wisedrive.wisedrivemechanicapp.services.MechanicApis;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class OBDReport extends AppCompatActivity {
    public  String KEY = "";
    public  String SECRET = "";
    private MechanicApis apiInterface;
    private ProgressDialog progressDialog;
    RelativeLayout rl_1st_image;
    ImageView iv_obd;
    public int fromWhere=0;
    private RequestPermissionHandler mRequestPermissionHandler;
    String filename,lifetimetaxfilename;
    private Uri imageUri;
    Boolean  isrcfrintimage=false;
    public  boolean isServiceRunning = true;
    String obdurl="";
    File root;
    File pdf;
    String myurl="";
    Uri imageuri = null;
    ImageView go_back;
    private List<String> fileList = new ArrayList<String>();
    private BasicAWSCredentials credentials;
    private AmazonS3Client s3Client;
    EditText entered_obdlink;
    RelativeLayout rl_obdpdf;
    TextView tv_save_next,tv_pdf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obdreport);
        SPHelper.sharedPreferenceInitialization(this);
        KEY=SPHelper.getSPData(OBDReport.this,SPHelper.awskey,"");
        SECRET=SPHelper.getSPData(OBDReport.this,SPHelper.awssecret,"");
        System.out.println("key"+KEY);
        System.out.println("secret"+SECRET);
        mRequestPermissionHandler = new RequestPermissionHandler();
        AWSMobileClient.getInstance().initialize(OBDReport.this).execute();
        credentials = new BasicAWSCredentials(SPHelper.getSPData(OBDReport.this,SPHelper.awskey,""), SPHelper.getSPData(OBDReport.this,SPHelper.awssecret,""));
        s3Client = new AmazonS3Client(credentials);
        apiInterface = ApiClient.getClient().create(MechanicApis.class);
        progressDialog = new ProgressDialog(OBDReport.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        tv_pdf=(TextView)findViewById(R.id.tv_pdf);
        iv_obd=(ImageView)findViewById(R.id.iv_obd);
        FirebaseApp.initializeApp(this);
        rl_obdpdf=(RelativeLayout)findViewById(R.id.rl_obdpdf);
        rl_1st_image=(RelativeLayout)findViewById(R.id.rl_1st_image);

        rl_1st_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mRequestPermissionHandler.requestPermission(OBDReport.this, new String[]
                        {
                                android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
                        }, fromWhere, new RequestPermissionHandler.RequestPermissionListener() {
                    @SuppressLint("IntentReset")
                    @Override
                    public void onSuccess() {
                        Intent galleryIntent = new Intent();
                        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                        galleryIntent.setType("application/pdf");
                        startActivityForResult(galleryIntent, 1);
                    }
                    @Override
                    public void onFailed() {
                        System.out.println("denied");
                    }
                });
            }
        });
        tv_save_next=(TextView)findViewById(R.id.tv_save_next);
        entered_obdlink=(EditText)findViewById(R.id.entered_obdlink);
        root = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath());

        go_back=(ImageView)findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        pdf = new File(root, "PDF");

//        rl_1st_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    getCameraPermissions(100);
//                }
//            }
//        });
        tv_save_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(entered_obdlink.getText().toString().equals("") && obdurl.equals("") && myurl.equalsIgnoreCase("")){
                    Toast.makeText(OBDReport.this, "please upload obd report" , Toast.LENGTH_SHORT).show();

                }else{
                    add_obd_Report();
                }
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getCameraPermissions( int fromWhere)
    {
        mRequestPermissionHandler.requestPermission(OBDReport.this, new String[]
                {
                        android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, fromWhere, new RequestPermissionHandler.RequestPermissionListener() {
            @SuppressLint("IntentReset")
            @Override
            public void onSuccess() {
                if (fromWhere == 100)
                {

                } 
            }
            @Override
            public void onFailed() {
                System.out.println("denied");
            }
        });
    }
    ProgressDialog dialog;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            // Here we are initialising the progress dialog box
            dialog = new ProgressDialog(this);
            dialog.setMessage("Uploading");

            // this will show message uploading
            // while pdf is uploading
            dialog.show();
            imageuri = data.getData();
            final String timestamp = "" + System.currentTimeMillis();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            final String messagePushID = timestamp;
            Toast.makeText(OBDReport.this, imageuri.toString(), Toast.LENGTH_SHORT).show();
            final StorageReference filepath = storageReference.child(SPHelper.vehid + "obd report " + messagePushID + "." + "pdf");
            Toast.makeText(OBDReport.this, filepath.getName(), Toast.LENGTH_SHORT).show();
            filepath.putFile(imageuri).continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return filepath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        dialog.dismiss();
                        Uri uri = task.getResult();
                       // String myurl;
                        myurl = uri.toString();
                        System.out.print(myurl);
                        tv_pdf.setVisibility(View.VISIBLE);
                        tv_pdf.setText(SPHelper.vehid + "obd report " + "." + "pdf");
                        iv_obd.setVisibility(View.GONE);
                        Toast.makeText(OBDReport.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();

                    } else {
                        dialog.dismiss();
                        Toast.makeText(OBDReport.this, "UploadedFailed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }



    public  void add_obd_Report(){
        {

            if(!Connectivity.isNetworkConnected(OBDReport.this))
            {
                Toast.makeText(getApplicationContext(),
                        "Please Check Your Internet",
                        Toast.LENGTH_LONG).show();
            }else
            {
                progressDialog.show();
                PojoAddOBDReport pojoAddOBDReport=new PojoAddOBDReport(SPHelper.vehid,SPHelper.getSPData(OBDReport.this, SPHelper.employeeid, ""),entered_obdlink.getText().toString(),myurl);
               System.out.print( SPHelper.vehid + SPHelper.getSPData(OBDReport.this, SPHelper.employeeid, "") + entered_obdlink.getText().toString() + myurl);
                Call<AppResponse> call =  apiInterface.add_obdreport(pojoAddOBDReport);
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
                                SPHelper.obdreportadded="y";
                                progressDialog.dismiss();
                                Intent intent=new Intent(getApplicationContext(), SubmitVehInspection.class);
                                startActivity(intent);

                            } else if (response_code.equals("300"))
                            {
                                progressDialog.dismiss();
                                Toast.makeText(OBDReport.this, appResponse.getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else{
                            progressDialog.dismiss();
                            Toast.makeText(OBDReport.this, "internal server error" , Toast.LENGTH_SHORT).show();
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