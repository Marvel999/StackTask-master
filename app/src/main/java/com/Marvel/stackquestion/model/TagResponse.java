package com.Marvel.stackquestion.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TagResponse {

    @SerializedName("items")
    @Expose
    private List<TagData> items;

    public List<TagData> getItems() {
        return items;
    }

    public void setItems(List<TagData> items) {
        this.items = items;
    }
}
