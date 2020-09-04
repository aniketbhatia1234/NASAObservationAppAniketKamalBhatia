package com.example.android.nasaobservationapp;

import com.google.gson.annotations.SerializedName;

public class collection {

    @SerializedName("items")
    private items[] item;

    public items getItems(int i) {
        return item[i];
    }

    public items[] getItemarray() {
        return item;
    }
}
