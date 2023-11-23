package com.wisedrive.wisedrivemechanicapp.pojos;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PojoQuestionsList {

    @SerializedName("answerList")
    ArrayList<PojoAnswerslists> answerList;
    @SerializedName("is_multi_selection")
    String is_multi_selection;
    @SerializedName("audio")
    String audio;
    @SerializedName("id")
    String id;
    @SerializedName("inspection_question")
    String inspection_question;
    @SerializedName("inspection_question_id")
    String inspection_question_id;

    public ArrayList<PojoAnswerslists> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(ArrayList<PojoAnswerslists> answerList) {
        this.answerList = answerList;
    }

    public String getIs_multi_selection() {
        return is_multi_selection;
    }

    public void setIs_multi_selection(String is_multi_selection) {
        this.is_multi_selection = is_multi_selection;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInspection_question() {
        return inspection_question;
    }

    public void setInspection_question(String inspection_question) {
        this.inspection_question = inspection_question;
    }

    public String getInspection_question_id() {
        return inspection_question_id;
    }

    public void setInspection_question_id(String inspection_question_id) {
        this.inspection_question_id = inspection_question_id;
    }
}
