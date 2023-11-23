package com.wisedrive.wisedrivemechanicapp.adapters;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.wisedrivemechanicapp.AddVehicleImages;
import com.wisedrive.wisedrivemechanicapp.R;
import com.wisedrive.wisedrivemechanicapp.commonclasses.SPHelper;
import com.wisedrive.wisedrivemechanicapp.pojos.PojoVehicleImages;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

public class AdapterVehicleImages extends RecyclerView.Adapter<AdapterVehicleImages.RecyclerViewHolder>
{
    MediaPlayer mediaPlayer;
    ArrayList<PojoVehicleImages> vehicleImages;
    Context context;
    public AdapterVehicleImages(ArrayList<PojoVehicleImages> vehicleImages, Context context) {
        this.vehicleImages = vehicleImages;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public AdapterVehicleImages.RecyclerViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_veh_images_lists, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterVehicleImages.RecyclerViewHolder holder, int position) {
        PojoVehicleImages recyclerdata=vehicleImages.get(position);
        holder.car_position_name.setText(vehicleImages.get(position).getName());

        holder.audio.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if(vehicleImages.get(position).getId().equals("1")){
                    SPHelper.audiourl=recyclerdata.getAudio();
                    //SPHelper.audiourl="https://ab-test-container.s3.us-east-2.amazonaws.com/test_audio.mp3";
                    playAudio();
                }else if(vehicleImages.get(position).getId().equals("2")){
                    SPHelper.audiourl=recyclerdata.getAudio();
                    // SPHelper.audiourl="https://ab-test-container.s3.us-east-2.amazonaws.com/test_audio.mp3";
                    playAudio();
                }else if(vehicleImages.get(position).getId().equals("3")){
                    SPHelper.audiourl=recyclerdata.getAudio();
                    playAudio();
                }
                else if(vehicleImages.get(position).getId().equals("4")){
                    SPHelper.audiourl=recyclerdata.getAudio();
                    playAudio();
                }else if(vehicleImages.get(position).getId().equals("6")){
                    SPHelper.audiourl=recyclerdata.getAudio();
                    // SPHelper.audiourl="https://ab-test-container.s3.us-east-2.amazonaws.com/test_audio.mp3";
                    playAudio();
                }else if(vehicleImages.get(position).getId().equals("12")){
                    SPHelper.audiourl=recyclerdata.getAudio();
                    playAudio();
                }
                else if(vehicleImages.get(position).getId().equals("13")){
                    SPHelper.audiourl=recyclerdata.getAudio();
                    playAudio();
                }
                else if(vehicleImages.get(position).getId().equals("14")){
                    SPHelper.audiourl=recyclerdata.getAudio();
                    playAudio();
                }
            }
        });
        holder.rl_veh_images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddVehicleImages.getInstance().selectedObject = position;
                if (vehicleImages.get(position).getImage() == null)
                {
                    AddVehicleImages.getInstance().finalids.add(vehicleImages.get(position).getId());
                    AddVehicleImages.getInstance().CallCamera();
                }else{
                    AddVehicleImages.getInstance().CallCamera();
                }

            }
        });
        if (vehicleImages.get(position).getImage() == null) {
            //uploaded_image.setImageURI(carImageLists.get(i).getImage());
        } else {
            holder.car_image_position.setImageURI(vehicleImages.get(position).getImage());
        }
    }


    @Override
    public int getItemCount() {
        return vehicleImages.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView car_position_name;
        ImageView car_image_position,audio;
        RelativeLayout rl_veh_images;
        public RecyclerViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            car_image_position =  itemView.findViewById(R.id.car_image_position);
           car_position_name =  itemView.findViewById(R.id.car_position_name);
            audio=  itemView.findViewById(R.id.audio);
            rl_veh_images=itemView.findViewById(R.id.rl_veh_images);

        }
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
