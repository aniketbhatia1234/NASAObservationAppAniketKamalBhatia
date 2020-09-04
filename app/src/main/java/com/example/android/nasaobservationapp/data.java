package com.example.android.nasaobservationapp;

import com.google.gson.annotations.SerializedName;

public class data {

    @SerializedName("nasa_id")
    private String nasa_id;

    @SerializedName("title")
    private String title;

    public String getNasa_id() {
        return nasa_id;
    }

    public String getTitle() {
        return title;
    }
}
