package com.Marvel.stackquestion.model;

import com.google.gson.annotations.SerializedName;

public class OwnerData {

    @SerializedName("profile_image")
    private String profile_image;

    @SerializedName("display_name")
    private String display_name;

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }
}
