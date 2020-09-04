package com.example.android.nasaobservationapp;

import com.google.gson.annotations.SerializedName;

public class items {

    @SerializedName("data")
    private data[] data;

    @SerializedName("href")
    private String href;

    public data getData() {
        return data[0];
    }

    public String getHref() {
        return href;
    }
}
