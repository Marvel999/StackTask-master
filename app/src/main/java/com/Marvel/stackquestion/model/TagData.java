package com.Marvel.stackquestion.model;

import com.google.gson.annotations.SerializedName;

public class TagData {

    @SerializedName("name")
    private String name;

    @SerializedName("count")
    private int count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
