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

import com.bumptech.glide.Glide;
import com.wisedrive.wisedrivemechanicapp.AddEngImages;
import com.wisedrive.wisedrivemechanicapp.AddVehicleImages;
import com.wisedrive.wisedrivemechanicapp.R;
import com.wisedrive.wisedrivemechanicapp.commonclasses.SPHelper;
import com.wisedrive.wisedrivemechanicapp.pojos.PojoEngineImages;
import com.wisedrive.wisedrivemechanicapp.pojos.PojoVehicleImages;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

public class AdapterEngineImages extends RecyclerView.Adapter<AdapterEngineImages.RecyclerViewHolder> {
    ArrayList<PojoEngineImages> engineImages;
    Context context;
    ImageView car_eng_position;
    MediaPlayer mediaPlayer;

    public AdapterEngineImages(ArrayList<PojoEngineImages> engineImages, Context context) {
        this.engineImages = engineImages;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public AdapterEngineImages.RecyclerViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_engine_images_lists, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterEngineImages.RecyclerViewHolder holder, int position) {
        PojoEngineImages recyclerdata=engineImages.get(position);
        holder.eng_position.setText(engineImages.get(position).getPart_name());
        if (engineImages.get(position).getSample_image() != null && !engineImages.get(position).getSample_image() .isEmpty() && !engineImages.get(position).getSample_image() .equals("null")) {
            Glide.with(context).load(engineImages.get(position).getSample_image()).placeholder(R.drawable.icon_noimage).into(holder.car_eng_position);
        }

        holder.audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(engineImages.get(position).getPart_id().equals("1")){
                     SPHelper.audiourl=recyclerdata.getAudio();
                    playAudio();
                }else if(engineImages.get(position).getPart_id().equals("2")){
                    SPHelper.audiourl=recyclerdata.getAudio();
                    playAudio();
                }else if(engineImages.get(position).getPart_id().equals("3")){
                    SPHelper.audiourl=recyclerdata.getAudio();
                    playAudio();
                }
                else if(engineImages.get(position).getPart_id().equals("4")){
                    SPHelper.audiourl=recyclerdata.getAudio();
                    playAudio();
                }
            }
        });
        holder.rl_eng_images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddEngImages.getInstance().selectedObject = position;
                if (engineImages.get(position).getImage() == null)
                {
                    AddEngImages.getInstance().finalids.add(engineImages.get(position).getPart_id());
                    AddEngImages.getInstance().CallCamera();
                }else{
                    AddEngImages.getInstance().CallCamera();
                }
            }
        });
        if (engineImages.get(position).getImage() == null) {
            //uploaded_image.setImageURI(carImageLists.get(i).getImage());
        } else {
            holder.car_eng_position.setImageURI(engineImages.get(position).getImage());
        }
    }

    @Override
    public int getItemCount() {
        return engineImages.size();
    }

    public class RecyclerViewHolder extends  RecyclerView.ViewHolder{
        TextView eng_position;
        ImageView car_eng_position,audio;
        RelativeLayout rl_eng_images;
        public RecyclerViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            car_eng_position =  itemView.findViewById(R.id.car_eng_position);
            eng_position =  itemView.findViewById(R.id.eng_position);
            audio=  itemView.findViewById(R.id.audio);
            rl_eng_images=itemView.findViewById(R.id.rl_eng_images);
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
   /* @Override
    public int getCount() {
        return engineImages.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridView;
        if (convertView == null) {
            gridView = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_engine_images_lists, null);

        } else {
            gridView = (View) convertView;
        }
        car_eng_position = (ImageView) gridView.findViewById(R.id.car_eng_position);
        TextView eng_position = (TextView) gridView.findViewById(R.id.eng_position);
        eng_position.setText(engineImages.get(position).getPart_name());
        if (engineImages.get(position).getSample_image() != null && !engineImages.get(position).getSample_image() .isEmpty() && !engineImages.get(position).getSample_image() .equals("null")) {
            Glide.with(context).load(engineImages.get(position).getSample_image()).placeholder(R.drawable.icon_noimage).into(car_eng_position);
        }
        if (engineImages.get(position).getImage() == null) {
            //uploaded_image.setImageURI(carImageLists.get(i).getImage());
        } else {
            car_eng_position.setImageURI(engineImages.get(position).getImage());
        }
        return gridView;
    }
    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
*/

}
