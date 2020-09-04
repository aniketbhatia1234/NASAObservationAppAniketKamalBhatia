package com.example.android.nasaobservationapp;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;

public class NIVLObj {

    @SerializedName("collection")
    private collection collections;

    public collection getCollections() {
        return collections;
    }
}
