package com.wisedrive.wisedrivemechanicapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
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
import com.wisedrive.wisedrivemechanicapp.adapters.AdapterEngineImages;
import com.wisedrive.wisedrivemechanicapp.adapters.AdapterTransmissionImages;
import com.wisedrive.wisedrivemechanicapp.commonclasses.BitmapUtility;
import com.wisedrive.wisedrivemechanicapp.commonclasses.Common;
import com.wisedrive.wisedrivemechanicapp.commonclasses.Connectivity;
import com.wisedrive.wisedrivemechanicapp.commonclasses.RequestPermissionHandler;
import com.wisedrive.wisedrivemechanicapp.commonclasses.ResponseExtension;
import com.wisedrive.wisedrivemechanicapp.commonclasses.ResponseListener;
import com.wisedrive.wisedrivemechanicapp.commonclasses.SPHelper;
import com.wisedrive.wisedrivemechanicapp.pojos.PojoEngineImages;
import com.wisedrive.wisedrivemechanicapp.responses.AppResponse;
import com.wisedrive.wisedrivemechanicapp.services.ApiClient;
import com.wisedrive.wisedrivemechanicapp.services.MechanicApis;
import com.wisedrive.wisedrivemechanicapp.services.ServiceURL;
import com.wisedrive.wisedrivemechanicapp.services.WebService;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTransmissionImages extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    TextView tv_save_next;
    public RecyclerView rv_eng_imagelist;
    public AdapterTransmissionImages adapterEngineImages;
    public ArrayList<PojoEngineImages> engineImages;
    ImageView iv_view_more_photos,iv_view_lessphotos,go_back;
    RelativeLayout rl_trans_list;
    private MechanicApis apiInterface;
    private ProgressDialog progressDialog;
    private RequestPermissionHandler mRequestPermissionHandler;
    String filename,upload="";
    Uri imageUri;
    public int selectedObject=0;
    public ArrayList<String> finalids =new ArrayList<>();
    public ArrayList<String> finalImages;
    public  boolean isServiceRunning = false;
    private Runnable runnableImage;
    private Handler handlerImage = new Handler();
    private BasicAWSCredentials credentials;
    private AmazonS3Client s3Client;
    private static AddTransmissionImages instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        go_back=(ImageView)findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        instance=this;
        setContentView(R.layout.activity_add_transmission_images);
        SPHelper.sharedPreferenceInitialization(this);
        SPHelper.saveSPdata(AddTransmissionImages.this, SPHelper.imagestaken, "");
        File dir = new File(BitmapUtility.PictUtil.getSavePath().getPath());
        if (dir.isDirectory())
        {
            String[] children = dir.list();
            if (children != null) {
                for (int i = 0; i < children.length; i++) {
                    new File(dir, children[i]).delete();
                }
            }
        }
        mRequestPermissionHandler = new RequestPermissionHandler();
        AWSMobileClient.getInstance().initialize(AddTransmissionImages.this).execute();
        credentials = new BasicAWSCredentials(SPHelper.getSPData(AddTransmissionImages.this,SPHelper.awskey,""), SPHelper.getSPData(AddTransmissionImages.this,SPHelper.awssecret,""));
        s3Client = new AmazonS3Client(credentials);
        apiInterface = ApiClient.getClient().create(MechanicApis.class);
        progressDialog = new ProgressDialog(AddTransmissionImages.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        rv_eng_imagelist=(RecyclerView) findViewById(R.id.rv_eng_imagelist);
        rl_trans_list=(RelativeLayout)findViewById(R.id.rl_trans_list);
        iv_view_more_photos=(ImageView)findViewById(R.id.iv_view_more_photos);
        iv_view_lessphotos=(ImageView)findViewById(R.id.iv_view_lessphotos);
        tv_save_next=(TextView)findViewById(R.id.tv_save_next);

        iv_view_more_photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                iv_view_more_photos.setVisibility(View.GONE);
                iv_view_lessphotos.setVisibility(View.VISIBLE);
                rl_trans_list.setVisibility(View.VISIBLE);
            }
        });
        iv_view_lessphotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_view_more_photos.setVisibility(View.VISIBLE);
                iv_view_lessphotos.setVisibility(View.GONE);
                rl_trans_list.setVisibility(View.GONE);
            }
        });
        get_trans_imagelist();
        tv_save_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (upload.equals("y"))
                {
                    Upload_after_check();
                } else {
                    add_trans_images();
                }
            }
        });
        /*rv_eng_imagelist.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PojoEngineImages recyclerdata=engineImages.get(position);
                if(engineImages.get(position).getPart_id().equals("1")){
                    // SPHelper.audiourl=recyclerdata.getAudio();
                    SPHelper.audiourl="https://ab-test-container.s3.us-east-2.amazonaws.com/test_audio.mp3";
                    playAudio();
                }else if(engineImages.get(position).getPart_id().equals("2")){
                    //SPHelper.audiourl=recyclerdata.getAudio();
                    SPHelper.audiourl="https://ab-test-container.s3.us-east-2.amazonaws.com/test_audio.mp3";
                    playAudio();
                }else if(engineImages.get(position).getPart_id().equals("3")){
                    //SPHelper.audiourl=recyclerdata.getAudio();
                    //playAudio();
                }
                else if(engineImages.get(position).getPart_id().equals("4")){
                    //SPHelper.audiourl=recyclerdata.getAudio();
                    //playAudio();
                }

                selectedObject = position;
                if (engineImages.get(position).getImage() == null)
                {
                    finalids.add(engineImages.get(position).getPart_id());
                    //  Common.CallToast(NewAddCar.this, "Capturing " + (position + 1) + " image", 3);
                    // CallCameracar();
                    *//*AddTransmissionImages.ImageUploadOptionsPop yourDialog = new AddTransmissionImages.ImageUploadOptionsPop();
                    yourDialog.activity = AddTransmissionImages.this;
                    yourDialog.show(getSupportFragmentManager(), "YOUR_DIALOG_TAG");*//*
                    CallCamera();
                }else{
                   *//* AddTransmissionImages.ImageUploadOptionsPop yourDialog = new AddTransmissionImages.ImageUploadOptionsPop();
                    yourDialog.activity = AddTransmissionImages.this;
                    yourDialog.show(getSupportFragmentManager(), "YOUR_DIALOG_TAG");*//*
                    CallCamera();
                }
            }
        });*/
    }


    public static class ImageUploadOptionsPop extends DialogFragment
    {
        private View dialogView = null;
        private RelativeLayout rl_camera;
        private TextView deleteTxt;
        private TextView cancelTxt,takefromgallery;
        private AddTransmissionImages activity;
        @Override
        public Dialog onCreateDialog(final Bundle savedInstanceState) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            dialogView = inflater.inflate(R.layout.image_upload_options_pop, null);
            rl_camera = (RelativeLayout) dialogView.findViewById(R.id.rl_camera);
            rl_camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.CallCamera();
                    dismiss();
                }
            });

            final Dialog dialog = new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setContentView(dialogView);
            dialog.show();
            return dialog;
        }
    }
    public void CallCamera() {

        mRequestPermissionHandler.requestPermission(this, new String[]{
                Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, 123, new RequestPermissionHandler.RequestPermissionListener() {
            @Override
            public void onSuccess() {
                System.out.println("Succeed");
                openCamera();
            }
            @Override
            public void onFailed() {
                System.out.println("denied");
            }
        });
    }
    void openCamera()
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("-yyyy_MM_dd_HH_mm_ss_SSSSSS'.jpg'");

            String fineName = dateFormat.format(new Date());

            filename = BitmapUtility.PictUtil.getSavePath().getPath() + "/" + "CarService" + fineName;
            imageUri = FileProvider.getUriForFile(AddTransmissionImages.this,
                    BuildConfig.APPLICATION_ID + ".provider", new File(filename));
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(takePictureIntent, selectedObject);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getCameraPermissions( int fromWhere)
    {
        mRequestPermissionHandler.requestPermission(AddTransmissionImages.this, new String[]
                {
                        android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, fromWhere, new RequestPermissionHandler.RequestPermissionListener() {
            @Override
            public void onSuccess() {
                if (fromWhere == 10) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivityForResult(pickPhoto, 200);
                }

            }

            @Override
            public void onFailed() {
                System.out.println("denied");
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
        mRequestPermissionHandler.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode!=1)
        {
            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    upload ="y";
                    String OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs(filename, new Pair<Integer, Integer>(800, 800), "/");
                    imageUri = FileProvider.getUriForFile(AddTransmissionImages.this,
                            BuildConfig.APPLICATION_ID + ".provider", new File(OriginalFileName));
                    engineImages.get(requestCode).setImage(imageUri);
                    engineImages.get(requestCode).setFilename(OriginalFileName);
                    adapterEngineImages.notifyDataSetChanged();

                }
            });
        }

    }

    public ProgressDialog mDialog;
    public void Upload_after_check()
    {
        int totalImagesCount = 0;

        for (PojoEngineImages check : engineImages) {
            if (check.getImage() != null) {
                totalImagesCount++;
            }
        }

        if (totalImagesCount < 1)
        {
            Common.CallToast(this, "Upload atleast 1 image", 3);
            return;
        }
        if (mDialog == null) {
            mDialog = new ProgressDialog(AddTransmissionImages.this);
            mDialog.setMessage("Please wait...");
            mDialog.setCancelable(false);
        }
        mDialog.show();
        final String CheckVerification = "N";
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                final JSONArray uploadImages = new JSONArray();

                for (PojoEngineImages imageUploadDataModel : engineImages) {
                    if (imageUploadDataModel.getImage() != null) {
                        String OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs(imageUploadDataModel.getFilename(), new Pair<Integer, Integer>(1500, 1500), "/");
                        uploadImages.put(OriginalFileName);
                    }
                }


                mDialog.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SPHelper.saveSPdata(AddTransmissionImages.this, SPHelper.imagestaken, uploadImages.toString());
                        checkupload();
                    }
                });
            }
        });
    }
    public void checkupload()
    {
        //Toast.makeText(AddCarPage.this, "check upload method started", Toast.LENGTH_SHORT).show();

        if (!Connectivity.isNetworkConnected(AddTransmissionImages.this)) {
            Toast.makeText(AddTransmissionImages.this, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
            return;
        }
        if (SPHelper.getSPData(AddTransmissionImages.this, SPHelper.imagestaken, "").trim().length() > 0) {
            try {
                JSONArray images = new JSONArray(SPHelper.getSPData(AddTransmissionImages.this, SPHelper.imagestaken, ""));
                if (images.length() >1||images.length()==1) {
                    finalImages = new ArrayList<>();
                    //Toast.makeText(AddCarPage.this, "going to check upload", Toast.LENGTH_SHORT).show();

                    countDownStart();
                    return;
                }
            } catch (JSONException JE) {
                JE.printStackTrace();
            }
        } else {
            Toast.makeText(AddTransmissionImages.this, "Please capture all 4 images", Toast.LENGTH_SHORT).show();

        }
    }
    private void countDownStart()
    {
        //Toast.makeText(AddCarPage.this, "count down method started", Toast.LENGTH_SHORT).show();

        progressDialog.show();
        runnableImage = new Runnable() {
            @Override
            public void run() {
                try {
                    if (!isServiceRunning)
                    {
                        if (!Connectivity.isNetworkConnected(AddTransmissionImages.this)) {
                            handlerImage.removeCallbacks(this);
                            progressDialog.dismiss();
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(AddTransmissionImages.this);
                            builder1.setMessage("Please retry to Submit your Details");
                            builder1.setCancelable(true);
                            builder1.setPositiveButton("RETRY", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    countDownStart();
                                }
                            });
                            AlertDialog alert11 = builder1.create();
                            alert11.show();
                            return;
                        }
                        JSONArray images = new JSONArray(SPHelper.getSPData(AddTransmissionImages.this, SPHelper.imagestaken, ""));
                        if ( finalImages.size() == images.length())
                        {
                            for(int i= 0;i<finalImages.size();i++)
                            {
                                engineImages.get(i).setImageurl(finalImages.get(i));
                            }
                            add_trans_images();
                        } else {
                            //Toast.makeText(AddCarPage.this, "uploading image", Toast.LENGTH_SHORT).show();
                            upload(images.getString(finalImages.size()));
                        }
                    }
                    handlerImage.postDelayed(this, 2000);
                } catch (Exception e) {
                    e.printStackTrace();
                    handlerImage.postDelayed(this, 2000);
                }
            }
        };
        handlerImage.postDelayed(runnableImage, 500);
    }
    public void upload(final String filename)
    {
        // Toast.makeText(getApplicationContext(), "Image Upload started", Toast.LENGTH_SHORT).show();

        isServiceRunning = true;
        // StorageReference storageRef = storage.getReference();
        File imageFile = new File(filename);
        Uri uri = Uri.fromFile(imageFile);
        try {
            final TransferUtility transferUtility =
                    TransferUtility.builder()
                            .context(getApplicationContext())
                            .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                            .s3Client(s3Client)
                            .build();
            final String key = "Test_Dealer_Trans_Image/" +SPHelper.getSPData(AddTransmissionImages.this, SPHelper.dealerid, "")+ uri.getLastPathSegment();
            final TransferObserver uploadObserver =
                    transferUtility.upload(key, new File(filename));
            uploadObserver.setTransferListener(new TransferListener() {
                @Override
                public void onStateChanged(int id, TransferState state) {
                    if (TransferState.COMPLETED == state) {
                        //Toast.makeText(getApplicationContext(), "Upload Completed!", Toast.LENGTH_SHORT).show();
                        String finalurl = s3Client.getResourceUrl("ab-prod-container", key);
                        System.out.print(finalurl);
                        finalImages.add(finalurl);
                        isServiceRunning = false;
                    } else if (TransferState.FAILED == state) {
                        isServiceRunning = false;
                    }
                }

                @Override
                public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                    float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                    int percentDone = (int) percentDonef;

                }

                @Override
                public void onError(int id, Exception ex) {
                    ex.printStackTrace();
                    isServiceRunning = false;
                }

            });

        } catch (Exception je) {

            je.printStackTrace();
            isServiceRunning = false;
        }
    }

    public void add_trans_images()
    {
        JSONObject params = new JSONObject();
        try {
            params.put("dealerId", SPHelper.getSPData(AddTransmissionImages.this,SPHelper.dealerid,""));
            params.put("vehicleId",SPHelper.vehid);
            params.put("moduleId", SPHelper.moduleid);
            params.put("addedBy", SPHelper.getSPData(AddTransmissionImages.this,SPHelper.employeeid,""));
            params.put("isImg", "Y");
            JSONArray paramsarr = new JSONArray();
            for (int i = 0; i <finalids.size(); i++) {
                JSONObject obbb = new JSONObject();

                obbb.put("part_id", finalids.get(i));
                obbb.put("inspection_url", finalImages.get(i));
                paramsarr.put(obbb);
            }
            params.put("inspectionimagesvideosArr", paramsarr);
            System.out.println("post_data_with images"+params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new WebService().LoadwithUrl(AddTransmissionImages.this, ServiceURL.BASEURL, WebService.HttpMethod.post, null, WebService.RequestType.shared.REST(ServiceURL.add_engine_images, WebService.RESTType.JSONBody, params), true, 0, new ResponseListener() {
            @Override
            public void ResponseSuccess(ResponseExtension response) {
                if (response.getResponseType().equalsIgnoreCase("200")) {
                    System.out.println(response);
                    JSONObject tktobj = response.getResponseObject();
                    try {
                        progressDialog.dismiss();
                        upload ="";
                        isServiceRunning=false;

                        Toast.makeText(AddTransmissionImages.this, "Images added successfully", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(AddTransmissionImages.this,SubmitVehInspection.class);
                        startActivity(intent);
                        finish();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    isServiceRunning=false;
                    progressDialog.dismiss();
                    Toast.makeText(AddTransmissionImages.this, response.getResponseMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void ResponseFailure(int responseCode, String errorMsg) {
                isServiceRunning=false;
                progressDialog.dismiss();
            }
        });
    }
    public  void get_trans_imagelist()
    {
        {
            if (!Connectivity.isNetworkConnected(AddTransmissionImages.this)) {
                Toast.makeText(getApplicationContext(),
                        "Plaese Check Your Internet",
                        Toast.LENGTH_LONG).show();
            } else {
                progressDialog.show();
                Call<AppResponse> call = apiInterface.get_partlist(SPHelper.moduleid,"4");
                call.enqueue(new Callback<AppResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                        AppResponse appResponse = response.body();
                        assert appResponse != null;
                        String response_code = appResponse.getResponseType();
                        if (response.body() != null)
                        {
                            if (response_code.equals("200"))
                            {
                                progressDialog.dismiss();
                                engineImages=new ArrayList<>();
                                engineImages=appResponse.getResponse().getEmgineimagelist();
                                adapterEngineImages = new AdapterTransmissionImages(engineImages,AddTransmissionImages.this);
                                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(AddTransmissionImages.this,RecyclerView.VERTICAL,false);
                                rv_eng_imagelist.setLayoutManager(linearLayoutManager);
                                rv_eng_imagelist.setAdapter(adapterEngineImages);
                                AddTransmissionImages.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapterEngineImages.notifyDataSetChanged();
                                    }
                                });
                            } else if (response_code.equals("300")) {
                                progressDialog.dismiss();
                            }
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(AddTransmissionImages.this, "internal server error", Toast.LENGTH_SHORT).show();
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

    public static AddTransmissionImages getInstance() {
        return instance;
    }
    private void playAudio() {

        // String audioUrl = "https://ab-test-container.s3.us-east-2.amazonaws.com/test_audio.mp3";

        // initializing media player
        mediaPlayer = new MediaPlayer();

        // below line is use to set the audio
        // stream type for our media player.
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        // below line is use to set our
        // url to our media player.
        try {
            mediaPlayer.setDataSource(SPHelper.audiourl);
            // below line is use to prepare
            // and start our media player.
            mediaPlayer.prepare();
            mediaPlayer.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
        // below line is use to display a toast message.
        // Toast.makeText(this, "Audio started playing..", Toast.LENGTH_SHORT).show();
    }
}