package com.Marvel.stackquestion.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuestionItemData {

    @SerializedName("items")
    private List<QuestionData> questionData;

    public List<QuestionData> getQuestionData() {
        return questionData;
    }

    public void setQuestionData(List<QuestionData> questionData) {
        this.questionData = questionData;
    }
}
