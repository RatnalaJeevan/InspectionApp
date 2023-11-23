package com.wisedrive.wisedrivemechanicapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoAnswerDetails {

    String inspection_question_id;
    String inspection_answer_id;

    public PojoAnswerDetails(String inspection_question_id, String inspection_answer_id) {
        this.inspection_question_id = inspection_question_id;
        this.inspection_answer_id = inspection_answer_id;
    }
}
