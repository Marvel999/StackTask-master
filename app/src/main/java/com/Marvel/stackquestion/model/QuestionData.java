package com.Marvel.stackquestion.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionData {

    @SerializedName("owner")
    @Expose
    private OwnerData ownerData;

    @SuppressWarnings("creation_date")
    private long creation_date;

    @SuppressWarnings("link")
    private String link;

    @SuppressWarnings("title")
    private String title;

    @SuppressWarnings("body")
    private String body;


    public OwnerData getOwnerData() {
        return ownerData;
    }

    public void setOwnerData(OwnerData ownerData) {
        this.ownerData = ownerData;
    }

    public long getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(long creation_date) {
        this.creation_date = creation_date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
