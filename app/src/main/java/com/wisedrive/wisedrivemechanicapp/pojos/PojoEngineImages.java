package com.wisedrive.wisedrivemechanicapp.pojos;

import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.TextView;

import com.google.gson.annotations.SerializedName;

public class PojoEngineImages {
    @SerializedName("module_id")
    String module_id;
    @SerializedName("part_name")
    String part_name;
    @SerializedName("audio")
    String audio;
    @SerializedName("part_id")
    String part_id;
    @SerializedName("sample_image")
    String sample_image;

    private Uri image = null;
    private String filename = "";
    private String imageurl = "";
    private Bitmap bitmap;

    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getModule_id() {
        return module_id;
    }

    public void setModule_id(String module_id) {
        this.module_id = module_id;
    }

    public String getPart_name() {
        return part_name;
    }

    public void setPart_name(String part_name) {
        this.part_name = part_name;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getPart_id() {
        return part_id;
    }

    public void setPart_id(String part_id) {
        this.part_id = part_id;
    }

    public String getSample_image() {
        return sample_image;
    }

    public void setSample_image(String sample_image) {
        this.sample_image = sample_image;
    }
}
