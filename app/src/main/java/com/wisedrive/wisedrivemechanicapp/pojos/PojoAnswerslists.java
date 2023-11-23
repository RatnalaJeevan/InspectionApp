package com.wisedrive.wisedrivemechanicapp.pojos;

import android.widget.TextView;

import com.google.gson.annotations.SerializedName;

public class PojoAnswerslists {

    @SerializedName("inspection_answer_id")
    String inspection_answer_id;
    @SerializedName("inspection_answer")
    String inspection_answer;
    @SerializedName("audio")
    String audio;
    @SerializedName("inspection_question_id")
    String inspection_question_id;
    @SerializedName("id")
    String id;

    String isSelected="n";

    public String getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
    }

    public String getInspection_answer_id() {
        return inspection_answer_id;
    }

    public void setInspection_answer_id(String inspection_answer_id) {
        this.inspection_answer_id = inspection_answer_id;
    }

    public String getInspection_answer() {
        return inspection_answer;
    }

    public void setInspection_answer(String inspection_answer) {
        this.inspection_answer = inspection_answer;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getInspection_question_id() {
        return inspection_question_id;
    }

    public void setInspection_question_id(String inspection_question_id) {
        this.inspection_question_id = inspection_question_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
