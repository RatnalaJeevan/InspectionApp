package com.wisedrive.wisedrivemechanicapp.pojos;

import android.net.Uri;

public class PojoEngineVideo {
    String videourl;
    String videoname;
    Uri selecteduri;

    public PojoEngineVideo(String videoname) {
        this.videoname = videoname;
    }

    public Uri getSelecteduri() {
        return selecteduri;
    }

    public void setSelecteduri(Uri selecteduri) {
        this.selecteduri = selecteduri;
    }

    public String getVideourl() {
        return videourl;
    }

    public void setVideourl(String videourl) {
        this.videourl = videourl;
    }

    public String getVideoname() {
        return videoname;
    }

    public void setVideoname(String videoname) {
        this.videoname = videoname;
    }
}
