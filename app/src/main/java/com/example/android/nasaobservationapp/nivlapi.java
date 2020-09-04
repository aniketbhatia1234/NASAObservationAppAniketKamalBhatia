package com.example.android.nasaobservationapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface nivlapi {

    @GET("search")
    Call<NIVLObj> nivlsearch(@Query("q") String query);

    @GET("asset/{nasa_id}")
    Call<NIVLObj> nivlimage(@Path("nasa_id") String nasa_id);
}
